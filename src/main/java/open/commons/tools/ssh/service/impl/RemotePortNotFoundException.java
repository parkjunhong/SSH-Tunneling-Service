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
 * Date  : 2020. 2. 16. 오전 9:59:28
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service.impl;

import open.commons.tools.ssh.lang.HttpResponseWrappedException;

/**
 * Remote Port가 존재하지 않을 때 발생하는 예외상황.
 * 
 * @since 2020. 2. 16.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class RemotePortNotFoundException extends HttpResponseWrappedException {

    /**
     *
     * @since 2020. 2. 16.
     */
    private static final long serialVersionUID = -1003773889126187631L;

    /**
     * 
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException() {
    }

    /**
     * @param message
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException(String message) {
        super(message);
    }

    /**
     * @param format
     * @param args
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException(String format, Object... args) {
        super(format, args);
    }

    /**
     * @param message
     * @param cause
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param cause
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     * @param format
     * @param args
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String format, Object... args) {
        super(cause, enableSuppression, writableStackTrace, format, args);
    }

    /**
     * @param cause
     * @param format
     * @param args
     * @since 2020. 2. 16.
     */
    public RemotePortNotFoundException(Throwable cause, String format, Object... args) {
        super(cause, format, args);
    }

}
