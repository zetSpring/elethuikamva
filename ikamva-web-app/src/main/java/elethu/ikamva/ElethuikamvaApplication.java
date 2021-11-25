package elethu.ikamva;

import elethu.ikamva.config.Swagger2Config;
import io.swagger.annotations.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(Swagger2Config.class)
public class ElethuikamvaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElethuikamvaApplication.class, args);
    }

}
