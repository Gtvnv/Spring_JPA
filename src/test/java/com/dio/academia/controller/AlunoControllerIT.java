package com.dio.academia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de integracao do recurso Aluno, exercitando a stack completa
 * (controller -> service -> repository -> H2) com dados do data.sql.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveListarAlunosDoSeed() throws Exception {
        mockMvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(2)));
    }

    @Test
    void deveBuscarAlunoPorId() throws Exception {
        mockMvc.perform(get("/api/alunos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.endereco.uf").value("SP"));
    }

    @Test
    void deveRetornar404ParaAlunoInexistente() throws Exception {
        mockMvc.perform(get("/api/alunos/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void deveCriarEExcluirAluno() throws Exception {
        String body = """
                {
                  "nome": "Teste Integracao",
                  "email": "teste.it@email.com",
                  "cpf": "900.900.900-90",
                  "dataNascimento": "1998-03-15"
                }
                """;

        String location = mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        mockMvc.perform(delete(location.substring(location.indexOf("/api"))))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRejeitarAlunoInvalido() throws Exception {
        String body = """
                {
                  "nome": "",
                  "email": "nao-eh-email",
                  "cpf": "",
                  "dataNascimento": "2999-01-01"
                }
                """;

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.email").exists())
                .andExpect(jsonPath("$.campos.nome").exists());
    }

    @Test
    void deveListarPaginado() throws Exception {
        mockMvc.perform(get("/api/alunos/paginado?page=0&size=1&sort=nome,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.totalElements").value(greaterThanOrEqualTo(2)));
    }
}
