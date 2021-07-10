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
 * Date  : 2020. 2. 13. 오후 5:55:18
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import open.commons.tools.ssh.Const;

/**
 * SSH Tunneling 연결 정보
 * 
 * <a href="http://tools.ietf.org/html/rfc7159">APPLICATION_JSON</a> source: <br>
 * 
 * <pre>
 * 
 * [CASE - 0]
 * 
 * {
 *   "username": "user-id",
 *   "password": "user-passwd",
 *   "sshServerHost": "192.168.10.1",
 *   "sshServerPort": 22,
 *   "remotePort": 8282
 * }
 * </pre>
 * 
 * @since 2020. 2. 13.
 * @version 1.0.0
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Validated
public class TunnelingInfo {
    /** SSH Server System Account Username */
    @NotEmpty
    @Pattern(regexp = Const.REGEX_LINUX_USERNAME)
    private String username;

    /** SSH Server System Account Password */
    @NotEmpty
    private String password;

    /** SSH Server Host */
    @NotEmpty
    @Pattern(regexp = Const.REGEX_IPV4_DOMAIN)
    private String sshServerHost;

    /** SSH Server Port */
    @Min(1)
    @Max(65535)
    @Nullable
    private int sshServerPort = 22;

    /** */
    @Min(1)
    @Max(65535)
    private int remotePort;

    /** 연결에 대한 설명 */
    @Nullable
    private String description;

    /**
     * 
     * @since 2020. 2. 13.
     */
    public TunnelingInfo() {
    }

    /**
     *
     * @return the description
     *
     * @since 2020. 6. 30.
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the password
     *
     * @since 2020. 2. 13.
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return the remotePort
     *
     * @since 2020. 2. 13.
     */
    public int getRemotePort() {
        return remotePort;
    }

    /**
     *
     * @return the sshServerHost
     *
     * @since 2020. 2. 13.
     */
    public String getSshServerHost() {
        return sshServerHost;
    }

    /**
     *
     * @return the sshServerPort
     *
     * @since 2020. 2. 13.
     */
    public int getSshServerPort() {
        return sshServerPort;
    }

    /**
     *
     * @return the username
     *
     * @since 2020. 2. 13.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param description
     *            the description to set
     *
     * @since 2020. 6. 30.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param password
     *            the password to set
     *
     * @since 2020. 2. 13.
     */
    public void setPassword(@NotEmpty String userPwd) {
        this.password = userPwd;
    }

    /**
     * @param remotePort
     *            the remotePort to set
     *
     * @since 2020. 2. 13.
     */
    public void setRemotePort(@Min(1) @Max(65535) int tunnelingPort) {
        this.remotePort = tunnelingPort;
    }

    /**
     * @param sshServerHost
     *            the sshServerHost to set
     *
     * @since 2020. 2. 13.
     */
    public void setSshServerHost(@NotEmpty String sshServerHost) {
        this.sshServerHost = sshServerHost;
    }

    /**
     * @param sshServerPort
     *            the sshServerPort to set
     *
     * @since 2020. 2. 13.
     */
    public void setSshServerPort(@Min(1) @Max(65535) int sshServerPort) {
        this.sshServerPort = sshServerPort;
    }

    /**
     * @param username
     *            the username to set
     *
     * @since 2020. 2. 13.
     */
    public void setUsername(@NotEmpty String userId) {
        this.username = userId;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 6. 30.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 6. 30.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TunnelingInfo [username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", sshServerHost=");
        builder.append(sshServerHost);
        builder.append(", sshServerPort=");
        builder.append(sshServerPort);
        builder.append(", remotePort=");
        builder.append(remotePort);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }
}
