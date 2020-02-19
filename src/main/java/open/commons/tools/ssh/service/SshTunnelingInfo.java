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
 * Date  : 2020. 2. 15. 오전 12:14:57
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * SSH Tunneling 설정 정보
 * 
 * @since 2020. 2. 15.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Validated
public class SshTunnelingInfo {

    /** SSH Server 사용자 */
    private final String username;

    /** SSH Server Host. */
    private final String host;

    /**
     * SSH Server 포트
     */
    private final int port;

    /**
     * Remote Port Forwarding 정보
     */
    private Set<RemotePortForwarding> remotePortForwardings = new HashSet<>();

    /**
     * @param username
     *            SSH Server 사용자
     * @param host
     *            SSH Server Host
     * @param port
     *            SSH Server 포트.
     * @since 2020. 2. 15.
     */
    public SshTunnelingInfo(@NotNull @NotEmpty String username, @NotNull @NotEmpty String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
    }

    public void addRemotePortForwarding(RemotePortForwarding remote) {
        if (remote == null) {
            return;
        }

        if (this.remotePortForwardings == null) {
            this.remotePortForwardings = new HashSet<>();
        }

        this.remotePortForwardings.add(remote);
    }

    public void addRemotePortForwardings(Collection<RemotePortForwarding> remotes) {
        if (remotes == null) {
            return;
        }

        if (this.remotePortForwardings == null) {
            this.remotePortForwardings = new HashSet<>();
        }

        this.remotePortForwardings.addAll(remotes);
    }

    public void addRemotePortForwardings(RemotePortForwarding... remotes) {
        if (remotes == null) {
            return;
        }

        this.addRemotePortForwardings(Arrays.asList(remotes));
    }

    /**
     *
     * @return the host
     *
     * @since 2020. 2. 15.
     */
    public String getHost() {
        return host;
    }

    public String getId() {
        return String.join(":", this.username, this.host, String.valueOf(this.port));
    }

    /**
     *
     * @return the port
     *
     * @since 2020. 2. 15.
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return the remotePortForwardings
     *
     * @since 2020. 2. 15.
     */
    public Set<RemotePortForwarding> getRemotePortForwardings() {
        return remotePortForwardings;
    }

    public String getTunnelingInfo() {

        StringBuffer buf = new StringBuffer(getId());

        int idx = 0;
        for (RemotePortForwarding remote : remotePortForwardings) {
            buf.append("\n");
            buf.append("  [");
            buf.append(idx++);
            buf.append("] ");
            buf.append(remote.getRemotPortForwarding());
        }

        return buf.toString();
    }

    /**
     *
     * @return the username
     *
     * @since 2020. 2. 15.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param remotePortForwardings
     *            the remotePortForwardings to set
     *
     * @since 2020. 2. 15.
     */
    public void setRemotePortForwardings(Set<RemotePortForwarding> remotePortForwarding) {
        this.remotePortForwardings = remotePortForwarding;
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
        builder.append("SshTunnelingInfo [username=");
        builder.append(username);
        builder.append(", host=");
        builder.append(host);
        builder.append(", port=");
        builder.append(port);
        builder.append(", remotePortForwardings=");
        builder.append(remotePortForwardings);
        builder.append("]");
        return builder.toString();
    }

}
