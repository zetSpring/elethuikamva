package elethu.ikamva.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors
                .basePackage("elethu.ikamva.restcontrollers")).paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo(){
        return new ApiInfoBuilder().title("Student Spring Boot REST API")
                .description("Elethu Ikamva Management REST API")
                .contact(new Contact("Zuko Yawa", "wwww.notyet.africa", "zuko@email.com"))
                .license("Test 0.1")
                .licenseUrl("http://local.africa")
                .version("1.0.0")
                .build();
    }
}
