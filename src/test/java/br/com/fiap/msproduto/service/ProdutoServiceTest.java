package br.com.fiap.msproduto.service;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.reporitory.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ProdutoServiceTest {

    @Test
    void deveListarProdutos(){
        //criando mock do produtoRepository
        ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);

        // Criando a instância do serviço e injetando o mock
        ProdutoService produtoService = new ProdutoService(produtoRepository);

        Produto produto1 = new Produto( );
        produto1.setId(1);
        produto1.setNome("Produto 1");
        produto1.setDescricao("Descricao Produto 1");
        produto1.setQuantidade_estoque(10);
        produto1.setPreco(20.00);

        Produto produto2 = new Produto( );
        produto2.setId(2);
        produto2.setNome("Produto 2");
        produto2.setDescricao("Descricao Produto 2");
        produto2.setQuantidade_estoque(10);
        produto2.setPreco(30.00);
        List<Produto> produtosSimulados = Arrays.asList(produto1, produto2);

        when(produtoRepository.findAll()).thenReturn(produtosSimulados);
        List<Produto> listaProduto = produtoService.listarProduto();

        assertNotNull(listaProduto);
        assertEquals(2, listaProduto.size());
        assertEquals("Produto 1", listaProduto.get(0).getNome());
        assertEquals("Produto 2", listaProduto.get(1).getNome());

    }
}
