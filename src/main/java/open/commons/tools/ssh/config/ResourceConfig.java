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
 * Date  : 2020. 2. 13. 오후 5:21:20
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import open.commons.spring.web.handler.DefaultGlobalInterceptor;
import open.commons.spring.web.servlet.method.annotation.DefaultGlobalExceptionHandler;

/**
 * 
 * @since 2020. 2. 13.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Configuration
public class ResourceConfig {

    /**
     * 
     * @since 2020. 2. 13.
     */
    public ResourceConfig() {
    }

    @Bean
    @Primary
    public DefaultGlobalExceptionHandler getGlobalExceptionHandler() {
        return new DefaultGlobalExceptionHandler();
    }

    @Bean(name = DefaultGlobalInterceptor.BEAN_QUALIFIER)
    @Primary
    public DefaultGlobalInterceptor getGlobalInterceptor() {
        return new DefaultGlobalInterceptor();
    }
}
