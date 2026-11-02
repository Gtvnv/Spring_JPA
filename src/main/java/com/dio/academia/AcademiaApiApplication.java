package com.dio.academia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da API da academia.
 *
 * A anotacao {@link SpringBootApplication} habilita a auto-configuracao do
 * Spring Boot, o scan de componentes e a configuracao do Spring Data JPA.
 */
@SpringBootApplication
public class AcademiaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademiaApiApplication.class, args);
    }
}
