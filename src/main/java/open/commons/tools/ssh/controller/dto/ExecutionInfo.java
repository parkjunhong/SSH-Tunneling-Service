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
 * Date  : 2020. 2. 13. 오후 6:00:15
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.controller.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import open.commons.spring.web.annotation.RequestValueConverter;
import open.commons.spring.web.annotation.RequestValueSupported;

/**
 * SSH Tunneling 연결 실행 예약 정보
 * 
 * <a href="http://tools.ietf.org/html/rfc7159">APPLICATION_JSON</a> source: <br>
 * 
 * <pre>
 * 
 * [CASE - 0]
 * 
 * {
 *   "type": "now",
 *   "begin": 123456789,
 *   "end": {
 *     "type": "duration",
 *     "duration": "2d",
 *     "until": 123456789
 *   }
 * }
 * </pre>
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Validated
public class ExecutionInfo {
    @NotNull
    private ExecutionType type;

    @NotNull
    @Min(1)
    private Long begin;

    @NotNull
    @Valid
    private ExecutionEnd end;

    /**
     * 
     * @since 2020. 2. 13.
     */
    public ExecutionInfo() {
    }

    /**
     *
     * @return the begin
     *
     * @since 2020. 2. 13.
     */
    public Long getBegin() {
        return begin;
    }

    /**
     *
     * @return the end
     *
     * @since 2020. 2. 13.
     */
    public ExecutionEnd getEnd() {
        return end;
    }

    /**
     *
     * @return the type
     *
     * @since 2020. 2. 13.
     */
    public ExecutionType getType() {
        return type;
    }

    /**
     * @param begin
     *            the begin to set
     *
     * @since 2020. 2. 13.
     */
    public void setBegin(Long begin) {
        this.begin = begin;
    }

    /**
     * @param end
     *            the end to set
     *
     * @since 2020. 2. 13.
     */
    public void setEnd(ExecutionEnd end) {
        this.end = end;
    }

    /**
     * @param type
     *            the type to set
     *
     * @since 2020. 2. 13.
     */
    public void setType(ExecutionType type) {
        this.type = type;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 2. 13.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 2. 13.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExecutionInfo [type=");
        builder.append(type);
        builder.append(", begin=");
        builder.append(begin);
        builder.append(", end=");
        builder.append(end);
        builder.append("]");
        return builder.toString();
    }

    /** SSH Tunneling 연결 종료 정보 */
    public static class ExecutionEnd {
        @NotNull
        private ExecutionEndType type;
        @Nullable
        @Pattern(regexp = "\\d+[s|m|h|d|y]", flags = Pattern.Flag.CASE_INSENSITIVE)
        private String duration;
        @Nullable
        @Min(1)
        private Long until;

        /**
         *
         * @return the duration
         *
         * @since 2020. 2. 13.
         */
        public String getDuration() {
            return duration;
        }

        /**
         *
         * @return the type
         *
         * @since 2020. 2. 13.
         */
        public ExecutionEndType getType() {
            return type;
        }

        /**
         *
         * @return the until
         *
         * @since 2020. 2. 13.
         */
        public Long getUntil() {
            return until;
        }

        /**
         * @param duration
         *            the duration to set
         *
         * @since 2020. 2. 13.
         */
        public void setDuration(String duration) {
            this.duration = duration;
        }

        /**
         * @param type
         *            the type to set
         *
         * @since 2020. 2. 13.
         */
        public void setType(ExecutionEndType type) {
            this.type = type;
        }

        /**
         * @param until
         *            the until to set
         *
         * @since 2020. 2. 13.
         */
        public void setUntil(Long until) {
            this.until = until;
        }

        /**
         * <br>
         * 
         * <pre>
         * [개정이력]
         *      날짜    	| 작성자	|	내용
         * ------------------------------------------
         * 2020. 2. 13.		박준홍			최초 작성
         * </pre>
         *
         * @return
         *
         * @since 2020. 2. 13.
         * @author Park_Jun_Hong_(fafanmama_at_naver_com)
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("ExecutionEnd [type=");
            builder.append(type);
            builder.append(", duration=");
            builder.append(duration);
            builder.append(", until=");
            builder.append(until);
            builder.append("]");
            return builder.toString();
        }
    }

    @RequestValueSupported
    public static enum ExecutionEndType {
        /** duration */
        DURATION("duration"), //
        /** 지정 시간까지 실행.단위: timestamp by second */
        UNTIL("until"), //

        ;

        private String type;

        private ExecutionEndType(String type) {
            this.type = type;
        }

        /**
         *
         * @return a string of an instance of {@link ExecutionInfo.ExecutionEndType}
         */
        public String get() {
            return this.type;
        }

        /**
         * 
         * @param type
         *            a string for {@link ExecutionInfo.ExecutionEndType} instance.
         *
         * @return an instance of {@link ExecutionInfo.ExecutionEndType}
         *
         * @see #get(String, boolean)
         */
        public static ExecutionInfo.ExecutionEndType get(String type) {
            return get(type, false);
        }

        /**
         *
         * @param type
         *            a string for an instance of {@link ExecutionInfo.ExecutionEndType}.
         * @param ignoreCase
         *            ignore <code><b>case-sensitive</b></code> or not.
         *
         * @return an instance of {@link ExecutionInfo.ExecutionEndType}
         */
        @RequestValueConverter(hasIgnoreCase = true)
        public static ExecutionInfo.ExecutionEndType get(String type, boolean ignoreCase) {

            if (type == null) {
                throw new IllegalArgumentException("'type' MUST NOT be null. input: " + type);
            }

            if (ignoreCase) {
                for (ExecutionInfo.ExecutionEndType value : values()) {
                    if (value.type.equalsIgnoreCase(type)) {
                        return value;
                    }
                }
            } else {
                for (ExecutionInfo.ExecutionEndType value : values()) {
                    if (value.type.equals(type)) {
                        return value;
                    }
                }
            }

            throw new IllegalArgumentException(
                    "Unexpected 'type' value of 'ExecutionInfo.ExecutionEndType'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + type);
        }

        private static List<String> values0() {

            List<String> valuesStr = new ArrayList<>();

            for (ExecutionInfo.ExecutionEndType value : values()) {
                valuesStr.add(value.get());
            }

            return valuesStr;
        }

    }

    @RequestValueSupported
    public static enum ExecutionType {
        /** now */
        NOW("now"), //
        SCHEDULE("schedule");

        private String type;

        private ExecutionType(String type) {
            this.type = type;
        }

        /**
         *
         * @return a string of an instance of {@link ExecutionInfo.ExecutionType}
         */
        public String get() {
            return this.type;
        }

        /**
         * 
         * @param type
         *            a string for {@link ExecutionInfo.ExecutionType} instance.
         *
         * @return an instance of {@link ExecutionInfo.ExecutionType}
         *
         * @see #get(String, boolean)
         */
        public static ExecutionInfo.ExecutionType get(String type) {
            return get(type, false);
        }

        /**
         *
         * @param type
         *            a string for an instance of {@link ExecutionInfo.ExecutionType}.
         * @param ignoreCase
         *            ignore <code><b>case-sensitive</b></code> or not.
         *
         * @return an instance of {@link ExecutionInfo.ExecutionType}
         */
        @RequestValueConverter(hasIgnoreCase = true)
        public static ExecutionInfo.ExecutionType get(String type, boolean ignoreCase) {

            if (type == null) {
                throw new IllegalArgumentException("'type' MUST NOT be null. input: " + type);
            }

            if (ignoreCase) {
                for (ExecutionInfo.ExecutionType value : values()) {
                    if (value.type.equalsIgnoreCase(type)) {
                        return value;
                    }
                }
            } else {
                for (ExecutionInfo.ExecutionType value : values()) {
                    if (value.type.equals(type)) {
                        return value;
                    }
                }
            }

            throw new IllegalArgumentException(
                    "Unexpected 'type' value of 'ExecutionInfo.ExecutionType'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + type);
        }

        private static List<String> values0() {

            List<String> valuesStr = new ArrayList<>();

            for (ExecutionInfo.ExecutionType value : values()) {
                valuesStr.add(value.get());
            }

            return valuesStr;
        }
    }

}
