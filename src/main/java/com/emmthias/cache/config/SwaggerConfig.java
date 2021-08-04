package com.emmthias.cache.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.emmthias.cache.constants.Constants.POM_VERSION;

/**
 * Swagger class intended for parameters and beans configuration.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value(POM_VERSION)
    private String codeVersion;

    @Autowired
    private TypeResolver typeResolver;

    /**
     * Creates a Docket with configuration attributes.
     *
     * @return the Docket bean configured with the base package and paths
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.emmthias.cache.controller"))
                .paths(PathSelectors.any()).build() //ant("/*/api/**")
                .useDefaultResponseMessages(false).apiInfo(apiInfo());
    }

    /**
     * Creates the Api Information bean.
     *
     * @return the ApiInfo bean with service details
     */
    private ApiInfo apiInfo() {
        return new ApiInfo("Cache Layer Implementation", "RESTful API for Cache Layer", codeVersion,
                "http://MyCacheLayer.com/terms",
                new Contact("MyCacheLayer", "", ""), "", "", Collections.emptyList());
    }

    /**
     * Creates the UiConfiguration bean used for swagger.
     *
     * @return the UiConfiguration bean instance
     */
    @Bean
    public UiConfiguration uiConfig() {
        String[] supportedMethods = {};
        return UiConfigurationBuilder.builder().supportedSubmitMethods(supportedMethods).build();
    }
}
