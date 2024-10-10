package br.com.fiap.msproduto.controller;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
@AutoConfigureMockMvc
class ProdutoControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve devolver um erro 400 para busca por Id com erros")
    void deveDevolverErro400() throws Exception {
        //ARRANGE
        Mockito.when(produtoService.buscaProdutoPorId(1)).thenReturn(Optional.empty());
        //ACT
        var response = mvc.perform(
                get("/api/produto/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        //ASSERT
        Assertions.assertEquals(404, response.getStatus());
    }
    @Test
    @DisplayName("Deve devolver status 200 quando encontrado produto com ID")
    void deveDevolverStatus200() throws Exception{
        //ARRANGE
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        Mockito.when(produtoService.buscaProdutoPorId(1)).thenReturn(Optional.of(produto));
        //ACT
        var response = mvc.perform(
                get("/api/produto/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
}