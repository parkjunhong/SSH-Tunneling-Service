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
 * Date  : 2020. 2. 15. 오전 12:54:35
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.controller.dto;

import java.util.ArrayList;
import java.util.List;

import open.commons.spring.web.annotation.RequestValueConverter;
import open.commons.spring.web.annotation.RequestValueSupported;

/**
 * 
 * @since 2020. 2. 15.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@RequestValueSupported
public enum RemotePortFwdListType {
    /** Plain */
    PLAIN("plain"), //
    /** JSON */
    JSON("json"), //

    ;

    private String type;

    private RemotePortFwdListType(String type) {
        this.type = type;
    }

    /**
     *
     * @return a string of an instance of {@link RemotePortFwdListType}
     */
    public String get() {
        return this.type;
    }

    /**
     * 
     * @param type
     *            a string for {@link RemotePortFwdListType} instance.
     *
     * @return an instance of {@link RemotePortFwdListType}
     *
     * @see #get(String, boolean)
     */
    public static RemotePortFwdListType get(String type) {
        return get(type, false);
    }

    /**
     *
     * @param type
     *            a string for an instance of {@link RemotePortFwdListType}.
     * @param ignoreCase
     *            ignore <code><b>case-sensitive</b></code> or not.
     *
     * @return an instance of {@link RemotePortFwdListType}
     */
    @RequestValueConverter(hasIgnoreCase = true)
    public static RemotePortFwdListType get(String type, boolean ignoreCase) {

        if (type == null) {
            throw new IllegalArgumentException("'type' MUST NOT be null. input: " + type);
        }

        if (ignoreCase) {
            for (RemotePortFwdListType value : values()) {
                if (value.type.equalsIgnoreCase(type)) {
                    return value;
                }
            }
        } else {
            for (RemotePortFwdListType value : values()) {
                if (value.type.equals(type)) {
                    return value;
                }
            }
        }

        throw new IllegalArgumentException(
                "Unexpected 'type' value of 'RemotePortFwdListType'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + type);
    }

    private static List<String> values0() {

        List<String> valuesStr = new ArrayList<>();

        for (RemotePortFwdListType value : values()) {
            valuesStr.add(value.get());
        }

        return valuesStr;
    }

}
