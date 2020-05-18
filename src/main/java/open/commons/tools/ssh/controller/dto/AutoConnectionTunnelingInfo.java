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
 * Date  : 2020. 5. 18. 오후 5:13:39
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * 
 * @since 2020. 5. 18.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Validated
public class AutoConnectionTunnelingInfo {

    @NotEmpty
    private String serviceHost;

    @Min(1)
    @Max(65535)
    private int servicePort;

    @NotNull
    private TunnelingInfo tunneling;

    /**
     * @since 2020. 5. 18.
     */
    public AutoConnectionTunnelingInfo() {
    }

    /**
     * @param serviceHost
     * @param servicePort
     * @param tunneling
     * @since 2020. 5. 18.
     */
    public AutoConnectionTunnelingInfo(@NotEmpty String serviceHost, @Min(1) @Max(65535) int servicePort, @NotNull TunnelingInfo tunneling) {
        this.serviceHost = serviceHost;
        this.servicePort = servicePort;
        this.tunneling = tunneling;
    }

    /**
     *
     * @return the serviceHost
     *
     * @since 2020. 5. 18.
     */
    public String getServiceHost() {
        return serviceHost;
    }

    /**
     *
     * @return the servicePort
     *
     * @since 2020. 5. 18.
     */
    public int getServicePort() {
        return servicePort;
    }

    /**
     *
     * @return the tunneling
     *
     * @since 2020. 5. 18.
     */
    public TunnelingInfo getTunneling() {
        return tunneling;
    }

    /**
     * @param serviceHost
     *            the serviceHost to set
     *
     * @since 2020. 5. 18.
     */
    public void setServiceHost(@NotEmpty String serviceHost) {
        this.serviceHost = serviceHost;
    }

    /**
     * @param servicePort
     *            the servicePort to set
     *
     * @since 2020. 5. 18.
     */
    public void setServicePort(@Min(1) @Max(65535) int servicePort) {
        this.servicePort = servicePort;
    }

    /**
     * @param tunneling
     *            the tunneling to set
     *
     * @since 2020. 5. 18.
     */
    public void setTunneling(@NotNull TunnelingInfo tunneling) {
        this.tunneling = tunneling;
    }

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
     * @return
     *
     * @since 2020. 5. 18.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutoConnectionTunnelingInfo [serviceHost=");
        builder.append(serviceHost);
        builder.append(", servicePort=");
        builder.append(servicePort);
        builder.append(", tunneling=");
        builder.append(tunneling);
        builder.append("]");
        return builder.toString();
    }
}
