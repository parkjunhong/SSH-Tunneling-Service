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
 * This file is generated under this project, "SSH-Tunneling-Service".
 *
 * Date  : 2020. 2. 13. 오후 7:13:05
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import open.commons.Result;
import open.commons.concurrent.Mutex;
import open.commons.io.Closeables;
import open.commons.spring.web.mvc.service.AbstractComponent;
import open.commons.tools.ssh.controller.dto.ExecutionInfo;
import open.commons.tools.ssh.controller.dto.TunnelingInfo;
import open.commons.tools.ssh.service.ISshTunnelingService;
import open.commons.tools.ssh.service.RemotePortForwarding;
import open.commons.tools.ssh.service.SessionUtils;
import open.commons.tools.ssh.service.SshTunnelingInfo;
import open.commons.utils.ArrayUtils;
import open.commons.utils.IOUtils;
import open.commons.utils.StringUtils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Service(SshTunnelingService.BEAN_QUALIFIER)
@Scope()
public class SshTunnelingService extends AbstractComponent implements ISshTunnelingService, InitializingBean, DisposableBean {
    public static final String BEAN_QUALIFIER = "open.commons.tools.ssh.service.impl.SshTunnelingService";

    /**
     * <ul>
     * <li>key: {@link Session} ID.<br>
     * <li>value: a {@link Session}.
     * </ul>
     * 
     * @see SessionUtils#GET_SESSION_KEY
     */
    private static final ConcurrentSkipListMap<String, Session> sessions = new ConcurrentSkipListMap<>();
    private static final Mutex mutexSessions = new Mutex("mutex of 'sessions'");
    /**
     * <ul>
     * <li>key: {@link Session} ID.<br>
     * <li>value: remote ports bound by a {@link Session}.
     * </ul>
     * 
     * @see SessionUtils#GET_SESSION_KEY
     */
    private static final ConcurrentSkipListMap<String, Set<Integer>> sessionBoundRemotePorts = new ConcurrentSkipListMap<>();

    private final Closeables closeables = Closeables.list();
    private SessionHeartBeatChecker hbChecker;

    /**
     * 
     * @since 2020. 2. 13.
     */
    public SshTunnelingService() {
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 17.		박준홍			최초 작성
     * </pre>
     *
     * @throws Exception
     *
     * @since 2020. 2. 17.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.threadpool.submit(hbChecker = new SessionHeartBeatChecker());

        closeables.addAll(new Closeable() {

            @Override
            public void close() throws IOException {
                IOUtils.close(hbChecker);
            }
        }, new Closeable() {

            @Override
            public void close() throws IOException {
                SshTunnelingService.this.threadpool.shutdown();
            }
        });

        this.hbChecker.registerDisconnectionHandle(sessionId -> {
            synchronized (mutexSessions) {
                sessions.remove(sessionId);
                sessionBoundRemotePorts.remove(sessionId);
            }
        });
    }

    /**
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see open.commons.tools.ssh.service.ISshTunnelingService#connect(open.commons.tools.ssh.controller.dto.TunnelingInfo, java.lang.String, int, open.commons.tools.ssh.controller.dto.ExecutionInfo)
     */
    @Override
    public Result<String> connect(@NotNull TunnelingInfo tunneling, @NotNull String serviceHost, @Min(1) @Max(65535) int servicePort, ExecutionInfo execution) {

        String sshServerHost = tunneling.getSshServerHost();
        int sshServerPort = tunneling.getSshServerPort();
        int remotePort = tunneling.getRemotePort();
        String username = tunneling.getUsername();
        String userPwd = tunneling.getPassword();

        Session session = null;
        UserInfo userInfo = null;
        try {
            synchronized (mutexSessions) {
                // #1. Sessiong 생성
                session = getSession(username, sshServerHost, sshServerPort);

                // #1-1. 신규일 경우 연결생성
                if (!session.isConnected()) {
                    userInfo = new SshUserInfo(userPwd, "Are you sure you want to continue connecting", logger);
                    session.setUserInfo(userInfo);

                    try {
                        session.connect();
                    } catch (JSchException e) {
                        if (!session.isConnected()) {
                            disconnectSession(session);
                        }

                        throw e;
                    }
                }else {
                    userInfo = session.getUserInfo();
                }

                // #2. Remote Port Forwarding 추가
                return createRemotePortForwarding(session, remotePort, serviceHost, servicePort);
            }
        } catch (JSchException e) {
            // com.jcraft.jsch.JSchException: SSH_MSG_DISCONNECT: 2 Too many authentication failures

            String exMsg = e.getMessage();
            String errorMsg = String.format("SSH 연결 시도 중 에러가 발생하였습니다. session: %s, userinfo: %s, 원인: %s", session, userInfo, exMsg);
            if (exMsg.contains("Too many authentication failures")) {
            } else {
                logger.warn(errorMsg, e);
            }

            return new Result<String>().setMessage(errorMsg);
        }
    }

    /**
     * Remote Port Forwarding 을 추가한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param session
     *            연결 객체
     * @param remotePort
     *            SSH Tunelling를 제공해주는 포트
     * @param serviceHost
     *            SSH Tunneling 으로 연결되는 Host
     * @param servicePort
     *            SSH Tunneling 으로 연결되는 포트
     * @return
     * @throws JSchException
     *
     * @since 2020. 2. 14.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    private Result<String> createRemotePortForwarding(Session session, int remotePort, String serviceHost, int servicePort) throws JSchException {

        synchronized (mutexSessions) {
            boolean result = false;
            String message = null;
            // 기존 Remote Port Forwarding 목록 조회. 이미 포함되어 있다면 신규 생성하지 않음.
            // ${remote-port}:${service-host}:${service-port}
            String[] rpfs = session.getPortForwardingR();
            String rpfKey = SessionUtils.REMOTE_PORT_FORWARDING_KEYGEN.apply(remotePort, serviceHost, servicePort);
            if (ArrayUtils.contains(rpfs, rpfKey)) {

                logger.debug("{} already exists.", rpfKey);

                result = true;
                message = "Already exists";
            } else {
                // SSH Server에서 요청한 포트를 다른 서비스에 할당했는지 확인.
                Set<Integer> sbrps = sessionBoundRemotePorts.getOrDefault(SessionUtils.GET_SESSION_KEY.apply(session), new HashSet<>());
                if (sbrps.contains(remotePort)) {
                    result = false;
                    message = "Already bound to other service.";
                } else {
                    // ssh -R ${remote-port}:${service-host}:${service-port} ${username}@${ssh-server-host} -p
                    // ${ssh-server-port}

                    // Remote Port Forwarding 추가
                    try {
                        session.setPortForwardingR(remotePort, serviceHost, servicePort);

                        this.hbChecker.register(session);

                    } catch (JSchException e) {
                        logger.warn("Remote Port Forwarding 정보를 생성하는 도중 에러가 발생하였습니다. 원인: {}", e.getMessage());

                        Set<Integer> rPorts = sessionBoundRemotePorts.get(SessionUtils.GET_SESSION_KEY.apply(session));
                        if (rPorts == null || rPorts.size() < 1) {
                            disconnectSession(session);
                        }

                        throw e;
                    }

                    String sessionId = SessionUtils.GET_SESSION_KEY.apply(session);
                    Set<Integer> rPorts = sessionBoundRemotePorts.get(sessionId);
                    if (rPorts == null) {
                        rPorts = new HashSet<>();
                        sessionBoundRemotePorts.put(sessionId, rPorts);
                    }
                    rPorts.add(remotePort);

                    result = true;
                    message = "Created";
                }
            }

            return new Result<String>(String.join("/", String.valueOf(remotePort), SessionUtils.GET_SESSION_KEY.apply(session)), result).setMessage(message);
        }
    }

    /**
     * {@link Session}에서 SSH Tunneling 포트를 제거하고, 더 이상 포트가 없는 경우 {@link Session}을 해제한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param session
     *            Session 객체
     * @param remotePort
     *            SSH Tunneling 포트
     * @throws JSchException
     *
     * @since 2020. 2. 14.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    private void deleteRemotePortForawrding(Session session, int remotePort) throws JSchException {

        session.delPortForwardingR(remotePort);

        logger.info("Disconnected a port({}) in session({}).", remotePort, session);

        String sessionId = SessionUtils.GET_SESSION_KEY.apply(session);
        Set<Integer> rPorts = sessionBoundRemotePorts.get(sessionId);

        logger.info("Removed a port({}) in ({}).", remotePort, StringUtils.concatenate(", ", rPorts));

        rPorts.remove(remotePort);

        // 해당 Session에 더 이상 Remote Port Forwarding이 존재하지 않는 경우
        if (rPorts.size() < 1) {
            disconnectSession(session);
        }
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 17.		박준홍			최초 작성
     * </pre>
     *
     * @throws Exception
     *
     * @since 2020. 2. 17.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        this.closeables.close();
    }

    /**
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see open.commons.tools.ssh.service.ISshTunnelingService#disconnect(java.lang.String, int)
     */
    @Override
    public Result<String> disconnect(@NotNull String sessionId, @Min(1) @Max(65535) int remotePort) {
        Result<String> result = null;
        synchronized (mutexSessions) {
            try {
                // sessionId에 Remote Port Forwaring이 등록되어 있는지 확인.
                if (sessionBoundRemotePorts.containsKey(sessionId)) {
                    Set<Integer> rPorts = sessionBoundRemotePorts.get(sessionId);
                    if (rPorts == null) {
                        throw new RemotePortNotFoundException("There is no remote ports of session. session-id: %s", sessionId);
                    } else if (rPorts.contains(remotePort)) {
                        deleteRemotePortForawrding(sessions.get(sessionId), remotePort);
                        result = new Result<String>(String.join("/", String.valueOf(remotePort), sessionId), true).setMessage("Deleted");
                    } else {
                        throw new RemotePortNotFoundException("There is no remote port of session. session-id: %s, port: %s", sessionId, remotePort);
                    }
                } else
                // Session 목록에는 존재하는경우 Session 에서 Remote Port Forwarding 정보를 불러와 매핑 정보를 추가
                if (sessions.containsKey(sessionId)) {
                    Session session = sessions.get(sessionId);

                    // "rport:host:hostport"
                    String[] rPortFwd = session.getPortForwardingR();
                    // 기존 Session 에 Remote Port가 존재하지 않는 경우 해당 Session 삭제.
                    if (rPortFwd == null || rPortFwd.length < 1) {
                        disconnectSession(session);
                        throw new RemotePortNotFoundException("There is no remote ports of session. session-id: %s", sessionId);
                    } else {
                        rebuildRemotePortForwarding(session);
                        return disconnect(sessionId, remotePort);
                    }
                } else {
                    throw new SshSessionNotFoundException("There is no sessionId. session-id: %s", sessionId);
                }
            } catch (JSchException e) {
                String errorMsg = String.format("SSH 연결 제거 중 에러가 발생하였습니다. session: %s, remote-port: %s, 원인: %s", sessionId, remotePort, e.getMessage());
                logger.warn(errorMsg, e);
                return new Result<String>().setMessage(errorMsg);
            }
        }
        return result;
    }

    private void disconnectSession(Session session) {
        synchronized (mutexSessions) {
            session.disconnect();

            logger.info("Disconnected a session to {}:{}:{}.", session, session.getUserName(), session.getHost(), session.getPort());

            String sessionId = SessionUtils.GET_SESSION_KEY.apply(session);
            sessions.remove(sessionId);
            sessionBoundRemotePorts.remove(sessionId);

            this.hbChecker.unregister(sessionId);
        }
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param username
     *            사용자 명
     * @param host
     *            SSH Server host.
     * @param port
     *            SSH Server 포트.
     * @return
     * @throws JSchException
     *
     * @since 2020. 2. 14.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    private Session getSession(String username, String host, int port) throws JSchException {

        Session session = null;
        synchronized (mutexSessions) {
            String sessionId = SessionUtils.GENERATE_SESSION_KEY.apply(username, host, port);
            session = sessions.get(sessionId);

            if (session == null) {
                JSch sch = new JSch();
                session = sch.getSession(username, host, port);

                sessions.put(sessionId, session);

                logger.info("New session is created. id: {}", sessionId);
            } else {
                logger.debug("Already exists. id: {}", sessionId);
            }
        }

        return session;
    }
    
    /**
     *
     * @return
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see open.commons.tools.ssh.service.ISshTunnelingService#listRemotePortForwardingAll()
     */
    @Override
    public Result<List<SshTunnelingInfo>> listRemotePortForwardingAll() {

        List<SshTunnelingInfo> info = new ArrayList<>();

        Session session = null;

        SshTunnelingInfo sti = null;
        RemotePortForwarding remote = null;
        String[] rpfArr = null;
        try {
            synchronized (mutexSessions) {
                for (Entry<String, Session> entry : sessions.entrySet()) {
                    session = entry.getValue();

                    sti = new SshTunnelingInfo(session.getUserName(), session.getHost(), session.getPort());

                    rpfArr = session.getPortForwardingR();
                    for (String remoteStr : rpfArr) {
                        remote = new RemotePortForwarding(remoteStr);
                        sti.addRemotePortForwarding(remote);
                    }

                    info.add(sti);
                }
            }
            return new Result<>(info, true);
        } catch (JSchException e) {
            String errorMsg = String.format("SSH 연결 목록 조회 중 에러가 발생하였습니다. 원인: %s", e.getMessage());
            logger.warn(errorMsg, e);
            return new Result<List<SshTunnelingInfo>>().setMessage(errorMsg);
        }
    }

    /**
     * 기존 Session 에 관리되지 못한 Remote Port가 존재하지 않는 경우 관리목록에 추가.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 18.		박준홍			최초 작성
     * </pre>
     *
     * @param session
     * @throws JSchException
     *
     * @since 2020. 2. 18.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    private void rebuildRemotePortForwarding(Session session) throws JSchException {
        Set<Integer> rPorts = new HashSet<>();
        for (String rPort : session.getPortForwardingR()) {
            rPorts.add(Integer.parseInt(rPort.split(":")[0]));
        }
        sessionBoundRemotePorts.put(SessionUtils.GET_SESSION_KEY.apply(session), rPorts);
    }
}