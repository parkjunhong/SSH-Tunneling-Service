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
 * Date  : 2020. 2. 15. 오전 12:16:00
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service;

/**
 * 
 * @since 2020. 2. 15.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class RemotePortForwarding implements Comparable<RemotePortForwarding> {

    /**
     * SSH Tunneling 포트. <br>
     * SSH Server에서 개방되는 포트이다.
     */
    private final int remotePort;

    /**
     * SSH Tunneling 포트로 연결되는 서버 Host.
     */
    private final String serviceHost;

    /**
     * SSH Tunneling 포트로 연결되는 서버 포트..
     */
    private final int servicePort;

    /**
     * 
     * @param remotePortFwdStr
     *            Remote Port Forwarding 정보로 구성된 문자열. "rport:host:hostport"
     * @since 2020. 2. 15.
     */
    public RemotePortForwarding(String remotePortFwdStr) {
        String[] strs = remotePortFwdStr.split(":");

        this.remotePort = Integer.parseInt(strs[0]);
        this.serviceHost = strs[1];
        this.servicePort = Integer.parseInt(strs[2]);
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 5. 19.		박준홍			최초 작성
     * </pre>
     *
     * @param o
     * @return
     *
     * @since 2020. 5. 19.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(RemotePortForwarding o) {
        int c = this.remotePort - o.remotePort;
        if (c != 0) {
            return c;
        }

        c = this.serviceHost.compareTo(o.serviceHost);
        if (c != 0) {
            return c;
        }

        return this.servicePort - o.servicePort;
    }

    /**
     *
     * @return the remotePort
     *
     * @since 2020. 2. 15.
     */
    public int getRemotePort() {
        return remotePort;
    }

    public String getRemotPortForwarding() {
        return String.join(":", String.valueOf(this.remotePort), this.serviceHost, String.valueOf(this.servicePort));
    }

    /**
     *
     * @return the serviceHost
     *
     * @since 2020. 2. 15.
     */
    public String getServiceHost() {
        return serviceHost;
    }

    /**
     *
     * @return the servicePort
     *
     * @since 2020. 2. 15.
     */
    public int getServicePort() {
        return servicePort;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 15.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 2. 15.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RemotePortForwarding [remotePort=");
        builder.append(remotePort);
        builder.append(", serviceHost=");
        builder.append(serviceHost);
        builder.append(", servicePort=");
        builder.append(servicePort);
        builder.append("]");
        return builder.toString();
    }

}
