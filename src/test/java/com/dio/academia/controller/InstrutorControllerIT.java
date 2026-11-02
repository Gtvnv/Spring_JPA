package com.dio.academia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de integracao do recurso Instrutor, com foco no relacionamento
 * ManyToMany com Modalidade.
 */
@SpringBootTest
@AutoConfigureMockMvc
class InstrutorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarInstrutorComSuasModalidades() throws Exception {
        mockMvc.perform(get("/api/instrutores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Pereira"))
                .andExpect(jsonPath("$.modalidades.length()").value(greaterThanOrEqualTo(1)));
    }

    @Test
    void deveFiltrarInstrutoresPorModalidade() throws Exception {
        mockMvc.perform(get("/api/instrutores?modalidade=Spinning"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana Souza"));
    }

    @Test
    void deveCriarInstrutorAssociandoModalidades() throws Exception {
        String body = """
                {
                  "nome": "Bruno Lima",
                  "cref": "CREF-55555-G/SP",
                  "modalidadeIds": [1, 2]
                }
                """;

        mockMvc.perform(post("/api/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.modalidades.length()").value(2));
    }

    @Test
    void deveRejeitarModalidadeInexistente() throws Exception {
        String body = """
                {
                  "nome": "Carla Dias",
                  "cref": "CREF-66666-G/SP",
                  "modalidadeIds": [9999]
                }
                """;

        mockMvc.perform(post("/api/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }
}
