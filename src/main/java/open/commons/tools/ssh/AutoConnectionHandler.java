/*
 *
 * This file is generated under this project, "SSH-Tunneling-Service".
 *
 * Date  : 2021. 7. 14. 오후 4:53:48
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import open.commons.Result;
import open.commons.spring.web.config.ResourceConfiguration;
import open.commons.tools.ssh.config.ResourceConfig;
import open.commons.tools.ssh.controller.dto.AutoConnectionTunnelingInfo;
import open.commons.tools.ssh.controller.dto.TunnelingInfo;
import open.commons.tools.ssh.service.ISshTunnelingService;
import open.commons.tools.ssh.service.impl.SshTunnelingService;
import open.commons.tools.ssh.utils.Utils;
import open.commons.utils.ThreadUtils;

/**
 * 
 * @since 2021. 7. 14.
 * @version
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Component
public class AutoConnectionHandler implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ThreadPoolTaskExecutor executor;
    private final ISshTunnelingService sshSvc;
    private final List<AutoConnectionTunnelingInfo> autoConConfig;

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 7. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param sshSvc
     * @param autoConConfig
     * @since 2021. 7. 14.
     */
    @Autowired
    public AutoConnectionHandler( //
            @Qualifier(ResourceConfiguration.BEAN_QUALIFIER_THREAD_POOL) ThreadPoolTaskExecutor executor, //
            @Qualifier(SshTunnelingService.BEAN_QUALIFIER) ISshTunnelingService sshSvc, //
            @Qualifier(ResourceConfig.BEAN_QUALIFIER_AUTO_CONNECTION_TUNNELINGS) List<AutoConnectionTunnelingInfo> autoConConfig //
    ) {
        this.executor = executor;
        this.sshSvc = sshSvc;
        this.autoConConfig = autoConConfig;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void execute() {
        executor.submit(this);
    }

    /**
     * @since 2021. 7. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        Utils.runIf(sshSvc, svc -> svc != null //
                , autoConConfig, conns -> conns != null && !conns.isEmpty() //
                , (svc, configs) -> {
                    AtomicInteger retry = new AtomicInteger();
                    while (configs.size() > 0) {
                        configs = configs.stream().filter(config -> {
                            Result<String> res = null;
                            try {
                                TunnelingInfo tunl = config.getTunneling();
                                logger.info(" * * * * * [AUTO CONNECTING] Tunnelings: {}", tunl);
                                res = svc.connect(config.getTunneling(), config.getServiceHost(), config.getServicePort(), null);

                                if (res.getResult()) {
                                    logger.info(" * * * * * [AUTO CONNECTED] Connections: {}@{}:{} -> {}:{}", tunl.getUsername(), tunl.getSshServerHost(), tunl.getRemotePort(),
                                            config.getServiceHost(), config.getServicePort());
                                } else {
                                    logger.error(" * * * * * [FAILED A.C.] Connections: {}@{}:{} -> {}:{}", tunl.getUsername(), tunl.getSshServerHost(), tunl.getRemotePort(),
                                            config.getServiceHost(), config.getServicePort());
                                }
                            } finally {
                            }
                            return !res.getResult();
                        }).collect(Collectors.toList());

                        if (configs.size() > 0) {
                            ThreadUtils.sleep(1000);

                            logger.warn("RETRY({}) to connect with {}", retry.incrementAndGet(), configs);
                        }
                    }
                });

    }
}
