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
 * Date  : 2020. 2. 17. 오후 5:53:45
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import open.commons.concurrent.Mutex;
import open.commons.lang.DefaultRunnable;
import open.commons.tools.ssh.service.SessionUtils;

import com.jcraft.jsch.Session;

/**
 * 
 * @since 2020. 2. 17.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class SessionHeartBeatChecker extends DefaultRunnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ConcurrentSkipListMap<String, Session> sessions = new ConcurrentSkipListMap<>();
    private Mutex mutexSessions = new Mutex("mutex of 'sessions'");

    /** {@link Session}이 종료되는 경우 처리하는 함수 */
    private Set<Consumer<String>> sessionDisconnectHandles = new HashSet<>();

    /**
     * 
     * @since 2020. 2. 17.
     */
    public SessionHeartBeatChecker() {
    }

    private void fireSessionDisconnection(final String sessionId) {
        sessions.remove(sessionId);
        sessionDisconnectHandles.forEach(h -> h.accept(sessionId));
    }

    private boolean get() {
        try {
            mutexSessions.wait(Math.max(10, (long) (Math.random() * 30000.0)));
        } catch (InterruptedException ignored) {
        }

        boolean get = false;
        synchronized (mutexSessions) {
            while (isRunning() && !(get = this.sessions.size() > 0)) {
                try {
                    mutexSessions.wait();
                } catch (InterruptedException ignored) {
                }
            }

        }

        return get;
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
     * @return
     *
     * @since 2020. 2. 17.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see open.commons.lang.DefaultRunnable#getThreadName()
     */
    @Override
    protected String getThreadName() {
        return "ssh-tunneling-session-hb-checker";
    }

    public Session register(Session session) {

        if (session == null) {
            return null;
        }

        synchronized (mutexSessions) {
            session = sessions.put(SessionUtils.GET_SESSION_KEY.apply(session), session);

            mutexSessions.notifyAll();
        }

        return session;
    }

    public void registerDisconnectionHandle(Consumer<String> handle) {

        if (handle == null) {
            return;
        }

        sessionDisconnectHandles.add(handle);
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
     *
     * @since 2020. 2. 17.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see open.commons.lang.DefaultRunnable#runInternal()
     */
    @Override
    protected void runInternal() {

        synchronized (mutexSessions) {
            String id = null;
            Session session = null;

            while (get()) {
                for (Entry<String, Session> entry : sessions.entrySet()) {
                    id = entry.getKey();
                    session = entry.getValue();
                    try {
                        if (session.isConnected()) {
                            session.sendKeepAliveMsg();
                        } else {
                            fireSessionDisconnection(id);
                        }

                        logger.trace("[HEART-BEAT] id={}, connected={}, remote={}", id, session.isConnected(), Arrays.toString(session.getPortForwardingR()));
                    } catch (Exception e) {
                        fireSessionDisconnection(id);

                        logger.warn("[INTERUPPTED] id={}, session={}", id, session);
                    }
                }
            }
        }
    }

    public Session unregister(String sessionId) {

        if (sessionId == null) {
            return null;
        }

        synchronized (mutexSessions) {
            return sessions.remove(sessionId);
        }
    }

    public void unregisterDisconnectionHandle(Consumer<String> handle) {

        if (handle == null) {
            return;
        }

        sessionDisconnectHandles.remove(handle);
    }
}
