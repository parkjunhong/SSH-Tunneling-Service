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
 * Date  : 2020. 2. 16. 오전 9:43:10
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.lang;

import org.springframework.http.HttpStatus;

/**
 * 요청 데이터 처리에 따라서 {@link HttpStatus}로 처리되는 상황.
 * 
 * @since 2020. 2. 16.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public abstract class HttpResponseWrappedException extends RuntimeException {

    /**
     *
     * @since 2020. 2. 16.
     */
    private static final long serialVersionUID = 4010311377185938548L;

    /**
     * 
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException() {
    }

    /**
     * @param message
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException(String message) {
        super(message);
    }

    /**
     * @param message
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException(String format, Object... args) {
        super(String.format(format, args));
    }

    /**
     * @param message
     * @param cause
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param cause
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException(Throwable cause) {
        super(cause);
    }

    /**
     * 
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     * @param format
     * @param args
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String format, Object... args) {
        super(String.format(format, args), cause, enableSuppression, writableStackTrace);
    }

    /**
     * 
     * @param cause
     * @param format
     * @param args
     * @since 2020. 2. 16.
     */
    public HttpResponseWrappedException(Throwable cause, String format, Object... args) {
        super(String.format(format, args), cause);
    }

}
