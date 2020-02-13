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
 * Date  : 2020. 2. 13. 오후 7:07:46
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service;

import open.commons.Result;
import open.commons.tools.ssh.controller.dto.ExecutionInfo;
import open.commons.tools.ssh.controller.dto.TunnelingInfo;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public interface ISshTunnelingService {

    /**
     * SSH Tunneling 연결을 생성한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 13.		박준홍			최초 작성
     * </pre>
     *
     * @param tunneling
     *            SSH Tunneling 연결 정보
     * @param serviceHost
     *            SSH 서버 사용자 ID
     * @param servicePort
     *            SSH 서버 사용자 비밀번호
     * @param execution
     *            SSH Tunneling 실행 정보
     * @return
     *
     * @since 2020. 2. 13.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public Result<Boolean> connect(TunnelingInfo tunneling, String serviceHost, int servicePort, ExecutionInfo execution);

}
