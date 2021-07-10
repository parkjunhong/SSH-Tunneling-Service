/*
 *
 * This file is generated under this project, "SSH-Tunneling-Service".
 *
 * Date  : 2021. 7. 10. 오전 10:35:28
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import open.commons.tools.ssh.controller.ApiV1;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @since 2021. 7. 10.
 * @version
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
@Configuration
@EnableSwagger2
public class SpringfoxConfig {

    @SuppressWarnings("rawtypes")
    private ApiInfo apiInfo(String version) {
        return new ApiInfo(//
                "SSH Tunneling Request & Manage", // title
                "Supports capabilities to request and manage SSH Tunneling to multiple SSH Servers..", // description
                version, // version
                "https://github.com/parkjunhong/SSH-Tunneling-Service", // terms of Service URL
                null, // contact
                "Park, Jun Hong.", // license
                null, // license URL
                new ArrayList<VendorExtension>() // vendor extension
        );
    }

    @Bean
    public Docket v1() {
        String group = "v1";
        return new Docket(DocumentationType.SWAGGER_2) //
                .groupName(group) //
                .select() //
                .apis(RequestHandlerSelectors.withClassAnnotation(ApiV1.class)).build() //
                .apiInfo(apiInfo(group));
    }
}
