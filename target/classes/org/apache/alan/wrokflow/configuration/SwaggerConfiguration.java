package org.apache.alan.wrokflow.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:
 * @Author: Alan
 * @date: 2021-05-25 10:11
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfiguration {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API")
                .description("Api相关接口")
                .version("1.0.0")
                .license("aaaa")
                .licenseUrl("in")
                .contact(new Contact("workflow", "", "")).build();
    }

    @Bean
    public Docket weixinDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Api相关接口")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("org.apache.alan.wrokflow.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.apiInfo());
    }
}
