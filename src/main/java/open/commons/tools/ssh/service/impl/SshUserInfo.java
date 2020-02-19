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
 * Date  : 2020. 2. 14. 오후 1:14:47
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service.impl;

import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;

import com.jcraft.jsch.UserInfo;

/**
 * 
 * @since 2020. 2. 14.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Validated
public class SshUserInfo implements UserInfo {

    private final Logger logger;

    private final String password;
    private final String passPhrase;

    /**
     * 
     * @param password
     *            TODO
     * @param passPhrase
     *            TODO
     * @param logger
     *            TODO
     * @since 2020. 2. 14.
     */
    public SshUserInfo(String password, String passPhrase, Logger logger) {
        this.password = password;
        this.passPhrase = passPhrase;
        this.logger = logger;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see com.jcraft.jsch.UserInfo#getPassphrase()
     */
    @Override
    public String getPassphrase() {
        return this.passPhrase;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see com.jcraft.jsch.UserInfo#getPassword()
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param message
     * @return
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see com.jcraft.jsch.UserInfo#promptPassphrase(java.lang.String)
     */
    @Override
    public boolean promptPassphrase(String message) {
        logger.info(" > promptPassPhrase: {}", message);
        return true;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param message
     * @return
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see com.jcraft.jsch.UserInfo#promptPassword(java.lang.String)
     */
    @Override
    public boolean promptPassword(String message) {
        logger.info(" > promptPassword: {}", message);
        return true;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param message
     * @return
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see com.jcraft.jsch.UserInfo#promptYesNo(java.lang.String)
     */
    @Override
    public boolean promptYesNo(String message) {
        logger.info(" > promptYesNo: {}", message);
        return true;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @param message
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see com.jcraft.jsch.UserInfo#showMessage(java.lang.String)
     */
    @Override
    public void showMessage(String message) {
        logger.debug(" > showMessage: {}", message);
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 14.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 2. 14.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SshUserInfo [passPhrase=");
        builder.append(passPhrase);
        builder.append("]");
        return builder.toString();
    }

}
