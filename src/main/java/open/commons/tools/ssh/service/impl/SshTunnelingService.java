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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Service;

import open.commons.Result;
import open.commons.spring.web.mvc.service.AbstractComponent;
import open.commons.tools.ssh.controller.dto.ExecutionInfo;
import open.commons.tools.ssh.controller.dto.TunnelingInfo;
import open.commons.tools.ssh.service.ISshTunnelingService;
import open.commons.utils.IOUtils;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Service(SshTunnelingService.BEAN_QUALIFIER)
public class SshTunnelingService extends AbstractComponent implements ISshTunnelingService {
    public static final String BEAN_QUALIFIER = "open.commons.tools.ssh.service.impl.SshTunnelingService";

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
     * 2020. 2. 13.		박준홍			최초 작성
     * </pre>
     *
     * @param tunneling
     * @param execution
     * @return
     *
     * @since 2020. 2. 13.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see open.commons.tools.ssh.service.ISshTunnelingService#connect(open.commons.tools.ssh.controller.dto.TunnelingInfo,
     *      String, int, open.commons.tools.ssh.controller.dto.ExecutionInfo)
     */
    @Override
    public Result<Boolean> connect(TunnelingInfo tunneling, String serviceHost, int servicePort, ExecutionInfo execution) {
        String sshServerHost = tunneling.getSshServerHost();
        int sshServerPort = tunneling.getSshServerPort();
        int tunnelingPort = tunneling.getTunnelingPort();
        String userId = tunneling.getUserId();
        String userPwd = tunneling.getUserPwd();

        // ssh -R ${tunneling-port}:${service-host}:${service-port} ${user-id}@${ssh-server-host} -p ${ssh-server-port}
        String[] cmdarray = { "ssh", //
                "-R", //
                String.join(":", String.valueOf(tunnelingPort), serviceHost, String.valueOf(servicePort)), //
                String.join("@", userId, sshServerHost), //
                "-p", //
                String.valueOf(sshServerPort) };

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(cmdarray);

            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream(), "UTF-8"));

            String readline = null;
            String questionPwd = new StringBuffer(userId)//
                    .append('@') //
                    .append(sshServerHost) //
                    .append("'s password:") //
                    .toString();

            byte[] read = null;
            while ((read = IOUtils.readFully(proc.getInputStream(), false)) != null) {
                readline = new String(read);

                logger.debug(readline);

                // Ask to continue connecting...
                if (readline.contains("(yes/no)?")) {
                    writer.write("yes");
                    writer.flush();
                } else
                // Ask a password to log in.
                if (readline.trim().startsWith(questionPwd)) {
                    writer.write(userPwd);
                    writer.flush();
                }
            }

            // while ((readline = reader.readLine()) != null) {
            //
            // logger.debug(readline);
            //
            // // Ask to continue connecting...
            // if (readline.contains("(yes/no)?")) {
            // writer.write("yes");
            // writer.flush();
            // } else
            // // Ask a password to log in.
            // if (readline.trim().startsWith(questionPwd)) {
            // writer.write(userPwd);
            // writer.flush();
            // }
            // }

            return null;
        } catch (IOException e) {
            String errorMsg = String.format("SSH 연결 시도 중 에러가 발생하였습니다. 원인: %s", e.getMessage());
            logger.warn(errorMsg, e);

            return new Result<Boolean>().setMessage(errorMsg);
        } finally {
            proc.destroy();
        }
    }

}