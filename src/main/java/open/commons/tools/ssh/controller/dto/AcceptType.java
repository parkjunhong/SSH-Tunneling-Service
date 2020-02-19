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

import org.springframework.http.MediaType;

import open.commons.spring.web.annotation.RequestValueConverter;
import open.commons.spring.web.annotation.RequestValueSupported;

/**
 * 
 * @since 2020. 2. 15.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@RequestValueSupported
public enum AcceptType {
    /** Plain */
    TEXT_PLAIN("text/plain"), //
    /** APPLICATION_JSON */
    APPLICATION_JSON("application/json"), //
    ;

    private String type;

    private AcceptType(String type) {
        this.type = type;
    }

    /**
     *
     * @return a string of an instance of {@link AcceptType}
     */
    public String get() {
        return this.type;
    }

    public MediaType mediaType() {
        String[] mt = this.type.split("/");
        return new MediaType(mt[0], mt[1]);
    }

    /**
     * 
     * @param type
     *            a string for {@link AcceptType} instance.
     *
     * @return an instance of {@link AcceptType}
     *
     * @see #get(String, boolean)
     */
    public static AcceptType get(String type) {
        return get(type, false);
    }

    /**
     *
     * @param type
     *            a string for an instance of {@link AcceptType}.
     * @param ignoreCase
     *            ignore <code><b>case-sensitive</b></code> or not.
     *
     * @return an instance of {@link AcceptType}
     */
    @RequestValueConverter(hasIgnoreCase = true)
    public static AcceptType get(String type, boolean ignoreCase) {

        if (type == null) {
            throw new IllegalArgumentException("'type' MUST NOT be null. input: " + type);
        }

        if (ignoreCase) {
            for (AcceptType value : values()) {
                if (value.type.equalsIgnoreCase(type)) {
                    return value;
                }
            }
        } else {
            for (AcceptType value : values()) {
                if (value.type.equals(type)) {
                    return value;
                }
            }
        }

        throw new IllegalArgumentException("Unexpected 'type' value of 'AcceptType'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + type);
    }

    private static List<String> values0() {

        List<String> valuesStr = new ArrayList<>();

        for (AcceptType value : values()) {
            valuesStr.add(value.get());
        }

        return valuesStr;
    }

}
