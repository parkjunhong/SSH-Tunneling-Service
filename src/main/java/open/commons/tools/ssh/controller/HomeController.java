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
 * Date  : 2020. 2. 13. 오후 5:19:10
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.controller;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import open.commons.Result;
import open.commons.function.TripleConsumer;
import open.commons.spring.web.mvc.service.AbstractComponent;
import open.commons.tools.ssh.Const;
import open.commons.tools.ssh.controller.dto.ConnectionDTO;
import open.commons.tools.ssh.controller.dto.ResponseType;
import open.commons.tools.ssh.service.ISshTunnelingService;
import open.commons.tools.ssh.service.SshTunnelingInfo;
import open.commons.tools.ssh.service.impl.SshTunnelingService;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@RestController
@Validated
@RequestMapping("/connections")
public class HomeController extends AbstractComponent {

    private static Function<Result<List<SshTunnelingInfo>>, Object> HANDLE_LIST_CONNECTIONS = r -> {
        StringBuffer buf = new StringBuffer();

        if (r.getResult()) {
            TripleConsumer<Integer, String, StringBuffer> APPENDER = (i, s, b) -> {
                b.append("[");
                b.append(i);
                b.append("] ");
                b.append(s);
            };

            Iterator<SshTunnelingInfo> itr = r.getData().iterator();
            int idx = 0;
            if (itr.hasNext()) {
                APPENDER.accept(idx++, itr.next().getTunnelingInfo(), buf);
            }

            while (itr.hasNext()) {
                APPENDER.accept(idx++, itr.next().getTunnelingInfo(), buf);
            }
        } else {
            buf.append(r.getMessage());
        }

        return buf.toString();
    };

    private static Function<Result<String>, Object> HANDLE_CONNECT_OR_DIS = r -> {
        StringBuffer buf = new StringBuffer();
        if (r.getResult()) {
            buf.append(r.getData());
            buf.append("\t");
        }
        buf.append(r.getMessage());
        
        return buf.toString();
    };

    @Autowired
    @Qualifier(SshTunnelingService.BEAN_QUALIFIER)
    private ISshTunnelingService sshSvc;

    /**
     * 
     * @since 2020. 2. 13.
     */
    public HomeController() {
    }

    /**
     * SSH Tunneling 연결을 생성한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 15.		박준홍			최초 작성
     * </pre>
     *
     * @param request
     * @param response
     * @param serviceHost
     *            SSH Tunneling 을 통해서 접속하는 서비스 host.
     * @param servicePort
     *            SSH Tunneling 을 통해서 접속하는 서비스 포트.
     * @param connection
     *            SSH Tunneling 연결 생성 정보.
     * @param responseType
     *            응답 데이터 타입.
     * @return
     *
     * @since 2020. 2. 15.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @PutMapping(path = "/{service-host}/{service-port}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> connect(HttpServletRequest request, HttpServletResponse response //
            , @PathVariable("service-host") @Pattern(regexp = Const.REGEX_IPV4) String serviceHost //
            , @PathVariable("service-port") @NotNull @Min(1) @Max(65535) int servicePort //
            , @RequestBody @Valid @NotNull ConnectionDTO connection //
            , @RequestParam("rtype") @Nullable ResponseType responseType//
    ) {
        logger.debug("connection: {}", connection);

        Object resEntity = null;
        Result<String> resultSvc = sshSvc.connect(connection.getTunneling(), serviceHost, servicePort, connection.getExecution());

        if (responseType != null) {
            resEntity = handleType(resultSvc, responseType, HANDLE_CONNECT_OR_DIS, null);
        } else {
            resEntity = resultSvc;
        }

        return ResponseEntity.ok(resEntity);
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 15.		박준홍			최초 작성
     * </pre>
     *
     * @param request
     * @param response
     * @param sessionId
     * @param remotePort
     * @param responseType
     * @return
     *
     * @since 2020. 2. 15.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @DeleteMapping(path = "/{session-id}/{remote-port}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Object> disconnect(HttpServletRequest request, HttpServletResponse response //
            , @PathVariable("session-id") @NotEmpty String sessionId //
            , @PathVariable("remote-port") @Min(1) @Max(65535) int remotePort //
            , @RequestParam("rtype") @Nullable ResponseType responseType//
    ) {
        logger.debug("session: {}, remote-port: {}", sessionId, remotePort);

        Object resEntity = null;
        Result<String> resultSvc = sshSvc.disconnect(sessionId, remotePort);

        if (responseType != null) {
            resEntity = handleType(resultSvc, responseType, HANDLE_CONNECT_OR_DIS, null);
        } else {
            resEntity = resultSvc;
        }

        return ResponseEntity.ok(resEntity);
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 15.		박준홍			최초 작성
     * </pre>
     *
     * @param request
     * @param response
     * @param responseType
     * @return
     *
     * @since 2020. 2. 15.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @GetMapping(path = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getConnections(HttpServletRequest request, HttpServletResponse response //
            , @RequestParam("rtype") @Nullable ResponseType responseType//
    ) {
        Object resEntity = null;
        Result<List<SshTunnelingInfo>> resultSvc = sshSvc.listRemotePortForwardingAll();

        if (responseType != null) {
            resEntity = handleType(resultSvc, responseType, HANDLE_LIST_CONNECTIONS, null);
        } else {
            resEntity = resultSvc;
        }

        return ResponseEntity.ok(resEntity);
    }

    private <T> Object handleType(T entity, ResponseType rtype, Function<T, Object> handlePlain, Function<T, Object> handleJson) {
        Object result = null;
        switch (rtype) {
            case PLAIN:
                result = handlePlain != null //
                        ? handlePlain.apply(entity) //
                        : entity;
                break;
            case JSON:
                result = handleJson != null //
                        ? handlePlain.apply(entity) //
                        : entity;
                break;
            default:
                result = entity;
                break;
        }

        return result;
    }
}
