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
 * Date  : 2020. 2. 16. 오전 9:51:55
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.servlet.method.annotation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import open.commons.collection.FIFOMap;
import open.commons.spring.web.servlet.method.annotation.DefaultGlobalExceptionHandler;
import open.commons.spring.web.utils.WebUtils;
import open.commons.tools.ssh.service.impl.RemotePortNotFoundException;
import open.commons.tools.ssh.service.impl.SshSessionNotFoundException;

/**
 * 
 * @since 2020. 2. 16.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@ControllerAdvice
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler {

    /**
     * 
     * @since 2020. 2. 16.
     */
    public GlobalExceptionHandler() {
    }

    /**
     * SSH Tunneling 요청을 진행하는 도중 발생하는 예외상황 처리. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 16.		박준홍			최초 작성
     * </pre>
     *
     * @param ex
     * @param request
     * @return
     *
     * @since 2020. 2. 16.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @ExceptionHandler(value = { SshSessionNotFoundException.class, //
            RemotePortNotFoundException.class, //
    })
    public ResponseEntity<Object> handleSshTunneling4xxException(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        FIFOMap<String, Object> entity = WebUtils.createEntity(request, ex, status);

        return handleExceptionInternal(ex, entity, new HttpHeaders(), status, request);
    }

}
