/*
 * Copyright 2020 Park Jun-Hong_(parkjunhong77/google/com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *
 * This file is generated under this project, "ssh-tunneling".
 *
 * Date  : 2020. 2. 13. 오후 1:53:48
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import open.commons.Result;
import open.commons.spring.web.BasePackageMarker;
import open.commons.spring.web.listener.SpringApplicationListener;
import open.commons.tools.ssh.config.ResourceConfig;
import open.commons.tools.ssh.controller.dto.AutoConnectionTunnelingInfo;
import open.commons.tools.ssh.controller.dto.TunnelingInfo;
import open.commons.tools.ssh.service.ISshTunnelingService;
import open.commons.tools.ssh.service.impl.SshTunnelingService;
import open.commons.tools.ssh.utils.Utils;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@ComponentScan(basePackageClasses = { ApplicationMain.class, BasePackageMarker.class })
@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableWebMvc
@SuppressWarnings("unused")
public class ApplicationMain {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

    /**
     * 
     * @since 2020. 2. 13.
     */
    public ApplicationMain() {
    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ApplicationMain.class);
        app.addListeners(new SpringApplicationListener() {
            /**
             * <br>
             * 
             * <pre>
             * [개정이력]
             *      날짜    	| 작성자	|	내용
             * ------------------------------------------
             * 2020. 5. 18.		박준홍			최초 작성
             * </pre>
             *
             * @param event
             *
             * @since 2020. 5. 18.
             * @author Park_Jun_Hong_(fafanmama_at_naver_com)
             *
             * @see open.commons.spring.web.listener.SpringApplicationListener#onApplicationReadyEvent(org.springframework.boot.context.event.ApplicationReadyEvent)
             */
            @Override
            public void onApplicationReadyEvent(ApplicationReadyEvent event) {
                logger.info(" * * * * * Ready to serve");

                // Auto connected...
                runAutoConnection(event);
            }

            private void runAutoConnection(ApplicationReadyEvent event) {
                ConfigurableApplicationContext context = event.getApplicationContext();
                final ISshTunnelingService sshSvc = (ISshTunnelingService) context.getBean(SshTunnelingService.BEAN_QUALIFIER);
                List<AutoConnectionTunnelingInfo> autoConns = (List<AutoConnectionTunnelingInfo>) context.getBean(ResourceConfig.BEAN_QUALIFIER_AUTO_CONNECTION_TUNNELINGS);
                Utils.runIf(sshSvc, svc -> svc != null //
                , autoConns, conns -> conns != null && !conns.isEmpty() //
                , (svc, conns) -> {

                    Result<String> res = null;
                    for (AutoConnectionTunnelingInfo con : conns) {
                        TunnelingInfo tunl = con.getTunneling();
                        logger.info(" * * * * * [AUTO CONNECTING] Tunnelings: {}", tunl);
                        res = svc.connect(con.getTunneling(), con.getServiceHost(), con.getServicePort(), null);

                        if (!res.getResult()) {
                            // 실패한 경우 1번 더 시도한다.
                            res = svc.connect(con.getTunneling(), con.getServiceHost(), con.getServicePort(), null);
                            if (!res.getResult()) {
                                continue;
                            }
                        }

                        tunl = con.getTunneling();
                        logger.info(" * * * * * [AUTO CONNECTED] Connections: {}@{}:{} -> {}:{}", tunl.getUsername(), tunl.getSshServerHost(), tunl.getRemotePort(),
                                con.getServiceHost(), con.getServicePort());
                    }
                });
            }
        });

        app.run(args);
    }
}
