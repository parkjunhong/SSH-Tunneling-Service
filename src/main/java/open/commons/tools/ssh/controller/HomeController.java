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
import open.commons.tools.ssh.controller.dto.RemotePortFwdListType;
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

    @Autowired
    @Qualifier(SshTunnelingService.BEAN_QUALIFIER)
    private ISshTunnelingService sshSvc;

    /**
     * 
     * @since 2020. 2. 13.
     */
    public HomeController() {
    }

    @PutMapping(path = "/{service-host}/{service-port}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> connect(HttpServletRequest request, HttpServletResponse response //
            , @PathVariable("service-host") @Pattern(regexp = Const.REGEX_IPV4) String serviceHost //
            , @PathVariable("service-port") @NotNull @Min(1) @Max(65535) int servicePort //
            , @RequestBody @Valid @NotNull ConnectionDTO connection //
    ) {
        logger.debug("connection: {}", connection);

        Object resEntity = sshSvc.connect(connection.getTunneling(), serviceHost, servicePort, connection.getExecution());

        return ResponseEntity.ok(resEntity);
    }

    @GetMapping(path = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getConnections(HttpServletRequest request, HttpServletResponse response //
            , @RequestParam("type") @Nullable RemotePortFwdListType listType//
    ) {
        Object resEntity = null;
        Result<List<SshTunnelingInfo>> resultSvc = sshSvc.listRemotePortForwardingAll();

        if (listType != null) {
            switch (listType) {
                case PLAIN:
                    if (resultSvc.getResult()) {
                        TripleConsumer<Integer, String, StringBuffer> APPENDER = (i, s, b) -> {
                            b.append("[");
                            b.append(i);
                            b.append("] ");
                            b.append(s);
                        };

                        StringBuffer buf = new StringBuffer();
                        Iterator<SshTunnelingInfo> itr = resultSvc.getData().iterator();
                        int idx = 0;
                        if (itr.hasNext()) {
                            APPENDER.accept(idx++, itr.next().getTunnelingInfo(), buf);
                        }

                        while (itr.hasNext()) {
                            APPENDER.accept(idx++, itr.next().getTunnelingInfo(), buf);
                        }
                        
                        buf.append("\n");
                        
                        resEntity = buf.toString();
                    } else {
                        resEntity = resultSvc;
                    }
                    break;
                case JSON:
                default:
                    resEntity = resultSvc;
                    break;
            }
        } else {
            resEntity = resultSvc;
        }

        return ResponseEntity.ok(resEntity);
    }

    @DeleteMapping(path = "/{session-id}/{remote-port}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Object> method(HttpServletRequest request, HttpServletResponse response //
            , @PathVariable("session-id") @NotEmpty String sessionId //
            , @PathVariable("remote-port") @Min(1) @Max(65535) int remotePort //
    ) {
        logger.debug("session: {}, remote-port: {}", sessionId, remotePort);

        Object resEntity = sshSvc.disconnect(sessionId, remotePort);

        return ResponseEntity.ok(resEntity);
    }
}
