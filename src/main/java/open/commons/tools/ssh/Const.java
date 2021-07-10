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
 * Date  : 2020. 2. 13. 오후 7:42:31
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class Const {
    /**
     * <p>
     * SSH Server System Account Username
     * </p>
     * 
     * Pattern: POSIX ("Portable Operating System Interface for Unix") standard (IEEE Standard 1003.1 2008) states:<br>
     * <ul>
     * <li><a href="https://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap03.html#tag_03_282">3.282 Portable
     * Filename Character Set</a>
     * <li><a href="https://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap03.html#tag_03_437">3.347 User
     * Name</a>
     * </ul>
     */
    public static final String REGEX_LINUX_USERNAME = "^[0-9a-zA-Z_\\.][0-9a-zA-Z_\\.\\-]+$";

    /** IPv4 & Domain Name */
    public static final String REGEX_IPV4_DOMAIN = "^" //
            + "(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}" // IPv4
            + "|" //
            + "[0-9a-zA-Z\\-_~]+(\\.[0-9a-zA-Z\\-_~]+){1,}" // Domain Name
            + "$";
}
