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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import open.commons.Result;
import open.commons.function.TripleConsumer;
import open.commons.spring.web.mvc.service.AbstractComponent;
import open.commons.tools.ssh.Const;
import open.commons.tools.ssh.controller.dto.AcceptType;
import open.commons.tools.ssh.controller.dto.ConnectionDTO;
import open.commons.tools.ssh.service.ISshTunnelingService;
import open.commons.tools.ssh.service.SshTunnelingInfo;
import open.commons.tools.ssh.service.impl.SshTunnelingService;
import open.commons.util.ArrayItr;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@RestController
@Validated
@RequestMapping("/connections")
@ApiV1
public class HomeController extends AbstractComponent {

    private static Function<Result<List<SshTunnelingInfo>>, Object> HANDLE_LIST_CONNECTIONS_TEXT = r -> {
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
                buf.append("\n");
                APPENDER.accept(idx++, itr.next().getTunnelingInfo(), buf);
            }
        } else {
            buf.append(r.getMessage());
        }

        return buf.toString();
    };

    private static Function<Result<String>, Object> HANDLE_CONNECT_OR_DIS_TEXT = r -> {
        StringBuffer buf = new StringBuffer();
        if (r.getResult()) {
            buf.append(r.getData());
            buf.append("\t");
        }
        buf.append(r.getMessage());

        return buf.toString();
    };

    /** SSH 연결 서비스 */
    private final ISshTunnelingService sshSvc;

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 13.      박준홍     최초작성
     * 2021. 7. 9.		박준홍		Bean 생성자 포함
     * </pre>
     * 
     * @param sshSvc
     *            SSH 연결 서비스
     * @since 2020. 2. 13.
     */
    @Autowired
    public HomeController(@Qualifier(SshTunnelingService.BEAN_QUALIFIER) ISshTunnelingService sshSvc) {
        this.sshSvc = sshSvc;
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
     * @param acceptType
     *            응답 데이터 타입.
     * @param serviceHost
     *            SSH Tunneling 을 통해서 접속하는 서비스 host.
     * @param servicePort
     *            SSH Tunneling 을 통해서 접속하는 서비스 포트.
     * @param connection
     *            SSH Tunneling 연결 생성 정보.
     * @return
     *
     * @since 2020. 2. 15.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @PutMapping(path = "/{service-host}/{service-port}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = { MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> connect(HttpServletRequest request, HttpServletResponse response //
            , @RequestHeader(value = HttpHeaders.ACCEPT, defaultValue = MediaType.APPLICATION_JSON_VALUE) @NotNull AcceptType acceptType //
            , @PathVariable("service-host") @Pattern(regexp = Const.REGEX_IPV4_DOMAIN) String serviceHost //
            , @PathVariable("service-port") @NotNull @Min(1) @Max(65535) int servicePort //
            , @RequestBody @Valid @NotNull ConnectionDTO connection //
    ) {

        Object resEntity = null;
        Result<String> resultSvc = sshSvc.connect(connection.getTunneling(), serviceHost, servicePort, connection.getExecution());

        if (acceptType != null) {
            resEntity = handleType(resultSvc, acceptType, HANDLE_CONNECT_OR_DIS_TEXT, null);
        } else {
            resEntity = resultSvc;
        }

        return ResponseEntity.ok().headers(headers(HttpHeaders.ACCEPT, acceptType.get())).body(resEntity);
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
     * @param acceptType
     * @param sessionId
     * @param remotePort
     * @return
     *
     * @since 2020. 2. 15.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @DeleteMapping(path = "/{session-id}/{remote-port}", produces = { MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> disconnect(HttpServletRequest request, HttpServletResponse response //
            , @RequestHeader(HttpHeaders.ACCEPT) @NotNull AcceptType acceptType //
            , @PathVariable("session-id") @NotEmpty String sessionId //
            , @PathVariable("remote-port") @Min(1) @Max(65535) int remotePort //
    ) {
        logger.debug("session: {}, remote-port: {}", sessionId, remotePort);

        Object resEntity = null;
        Result<String> resultSvc = sshSvc.disconnect(sessionId, remotePort);

        if (acceptType != null) {
            resEntity = handleType(resultSvc, acceptType, HANDLE_CONNECT_OR_DIS_TEXT, null);
        } else {
            resEntity = resultSvc;
        }

        return ResponseEntity.ok().headers(headers(HttpHeaders.ACCEPT, acceptType.get())).body(resEntity);
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
     * @param acceptType
     * @return
     *
     * @since 2020. 2. 15.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @GetMapping(path = "", produces = { MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getConnections(HttpServletRequest request, HttpServletResponse response //
            , @RequestHeader(value = HttpHeaders.ACCEPT, defaultValue = MediaType.APPLICATION_JSON_VALUE) @NotNull AcceptType acceptType //
    ) {
        Object resEntity = null;
        Result<List<SshTunnelingInfo>> resultSvc = sshSvc.listRemotePortForwardingAll();

        if (acceptType != null) {
            resEntity = handleType(resultSvc, acceptType, HANDLE_LIST_CONNECTIONS_TEXT, null);
        } else {
            resEntity = resultSvc;
        }

        return ResponseEntity.ok().headers(headers(HttpHeaders.ACCEPT, acceptType.get())).body(resEntity);
    }

    private <T> Object handleType(T entity, AcceptType rtype, Function<T, Object> handlePlain, Function<T, Object> handleJson) {
        Object result = null;
        switch (rtype) {
            case TEXT_PLAIN:
                result = handlePlain != null //
                        ? handlePlain.apply(entity) //
                        : entity;
                break;
            case APPLICATION_JSON:
                result = handleJson != null //
                        ? handleJson.apply(entity) //
                        : entity;
                break;
            default:
                result = entity;
                break;
        }

        return result;
    }

    private static HttpHeaders headers(String name, String... values) {
        HttpHeaders headers = new HttpHeaders();

        ArrayItr<String> itr = new ArrayItr<>(values);

        if (itr.hasNext()) {
            headers.set(name, itr.next());
        }

        while (itr.hasNext()) {
            headers.add(name, itr.next());
        }

        return headers;
    }
}
