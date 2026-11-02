package com.dio.academia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracao da documentacao OpenAPI (Swagger).
 *
 * A UI interativa fica disponivel em /swagger-ui.html e o documento JSON em
 * /v3/api-docs apos a aplicacao subir.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI academiaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Academia API")
                        .description("API RESTful de uma academia de ginastica, "
                                + "construida para praticar mapeamento objeto-relacional "
                                + "(ORM) com Spring Data JPA.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Desafio DIO - Spring Data JPA"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
