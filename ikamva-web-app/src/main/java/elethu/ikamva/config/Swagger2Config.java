package elethu.ikamva.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket apiDocsConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Elethu API v1.")
                .apiInfo(apiEndPointsInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("elethu.ikamva.restcontrollers"))
                .paths(PathSelectors.regex("/api/v1.*"))
                .build();
    }

    private ApiInfo apiEndPointsInfo(){
        return new ApiInfoBuilder()
                .title("Elethu Ikamva REST API's")
                .description("Elethu Ikamva Management REST API")
                .contact(new Contact("Zuko Yawa", "wwww.notyet.africa", "zuko@email.com"))
                .license("Ikamva 1.0.0")
                .licenseUrl("http://local.africa")
                .version("1.0.0")
                .build();
    }

//    private Pre<String> paths () {
//       return Predicates.and(
//               PathSelectors.regex("/api/.*"),
//               Predicates.not(PathSelectors.regex("/error.*")));
//    }
}
