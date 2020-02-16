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
 * Date  : 2020. 2. 13. 오후 6:04:33
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.controller.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

/**
 * SSH Tunneling 연결 요청 정보
 * 
  * <a href="http://tools.ietf.org/html/rfc7159">APPLICATION_JSON</a> source: <br>
  * <pre>
  * 
  * [CASE - 0]
  * 
  * {
  *   "tunneling": {
  *     "userId": "user-id",
  *     "userPwd": "user-passwd",
  *     "sshServerHost": "192.168.10.1",
  *     "sshServerPort": 22,
  *     "tunnelingPort": 8282
  *   },
  *   "execution": {
  *     "type": "now|schedule",
  *     "begin": 123456789,
  *     "end": {
  *       "type": "duration|until",
  *       "duration": "duration: \\d+[s|m|h|d|y]",
  *       "until": 123456789
  *     }
  *   }
  * }
  * </pre>
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Validated
public class ConnectionDTO {

    @NotNull
    @Valid
    private TunnelingInfo tunneling;

    @Nullable
    @Valid
    private ExecutionInfo execution;

    /**
     * 
     * @since 2020. 2. 13.
     */
    public ConnectionDTO() {
    }

    /**
     *
     * @return the execution
     *
     * @since 2020. 2. 13.
     */
    public ExecutionInfo getExecution() {
        return execution;
    }

    /**
     *
     * @return the tunneling
     *
     * @since 2020. 2. 13.
     */
    public TunnelingInfo getTunneling() {
        return tunneling;
    }

    /**
     * @param execution
     *            the execution to set
     *
     * @since 2020. 2. 13.
     */
    public void setExecution(ExecutionInfo execution) {
        this.execution = execution;
    }

    /**
     * @param tunneling
     *            the tunneling to set
     *
     * @since 2020. 2. 13.
     */
    public void setTunneling(TunnelingInfo tunneling) {
        this.tunneling = tunneling;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 13.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 2. 13.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConnectionDTO [tunneling=");
        builder.append(tunneling);
        builder.append(", execution=");
        builder.append(execution);
        builder.append("]");
        return builder.toString();
    }

}
