package elethu.ikamva.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {
    @Bean
    public Docket apiDocsConfig() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiEndPointsInfo())
                .tags(new Tag("Elethu Ikamva", "Endpoint for Elethu Ikamva backend"))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }

    private ApiInfo apiEndPointsInfo(){
        return new ApiInfoBuilder()
                .title("Elethu Ikamva REST APIs")
                .description("Elethu Ikamva Management REST API")
                .contact(new Contact("Zuko Yawa", "wwww.notyet.africa", "czyawa@gmail.com"))
                .license("Ikamva 1.0.0")
                .licenseUrl("https://opensource.org/licenses/MIT")
                .version("1.0.0")
                .build();
    }
}
