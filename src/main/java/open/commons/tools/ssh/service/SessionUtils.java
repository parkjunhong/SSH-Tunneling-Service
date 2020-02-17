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
 * Date  : 2020. 2. 17. 오후 5:58:42
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.service;

import java.util.function.Function;

import open.commons.function.TripleFunction;

import com.jcraft.jsch.Session;

/**
 * 
 * @since 2020. 2. 17.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class SessionUtils {

    /**
     * Session 식별정보를 제공한다.
     * 
     * @param u
     *            username
     * @param h
     *            SSH Server host
     * @param p
     *            SSH Server port
     */
    public static final TripleFunction<String, String, Integer, String> GENERATE_SESSION_KEY = (u, h, p) -> String.join(":", u, h, String.valueOf(p));
    public static final Function<Session, String> GET_SESSION_KEY = s -> {
        return GENERATE_SESSION_KEY.apply(s.getUserName(), //
                s.getHost(), //
                s.getPort() //
        );
    };

    /**
     * Remote Port Forwarding 식별정보를 제공한다.
     * 
     * @param r
     *            remote port
     * @param h
     *            Service Host
     * @param p
     *            Service port
     */
    public static final TripleFunction<Integer, String, Integer, String> REMOTE_PORT_FORWARDING_KEYGEN = (r, h, p) -> String.join(":", String.valueOf(r), h, String.valueOf(p));

    private SessionUtils() {
    }

}
