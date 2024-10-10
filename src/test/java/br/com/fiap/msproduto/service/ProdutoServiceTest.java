package br.com.fiap.msproduto.service;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.reporitory.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdutoServiceTest {

    @Test
    @DisplayName("Deve trazer a lista dos produtos cadastrados")
    void listarProdutos(){
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
    @Test
    @DisplayName("Deve trazer um produto ao realizar a busca por Id")
    void deveBuscarProdutoPorId(){
        //criando mock do produtoRepository
        ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);

        // Criando a instância do serviço e injetando o mock
        ProdutoService produtoService = new ProdutoService(produtoRepository);

        // Criando um produto para simular a busca
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descricao Teste");
        produto.setQuantidade_estoque(10);
        produto.setPreco(100.00);

        // Simulando o comportamento do repository ao buscar o produto por ID
        int produtoId = 1;
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        Optional<Produto> produtoEncontrado = produtoService.buscaProdutoPorId(produtoId);

        assertNotNull(produtoEncontrado);
        assertTrue(produtoEncontrado.isPresent());
        assertEquals(produto.getId(), produtoEncontrado.get().getId());
        assertEquals("Produto Teste", produtoEncontrado.get().getNome());

    }

    @Test
    @DisplayName("Deve cadastrar um produto")
    void cadastrarProduto(){
        ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);
        ProdutoService produtoService = new ProdutoService(produtoRepository);

        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descricao Teste");
        produto.setQuantidade_estoque(10);
        produto.setPreco(100.00);

        when(produtoRepository.save(produto)).thenReturn(produto);
        Produto produtoCadastrado = produtoService.cadastrarProduto(produto);

        assertNotNull(produtoCadastrado);
        assertEquals(produto.getId(),  produtoCadastrado.getId());
        assertEquals(produto.getNome(), produtoCadastrado.getNome());
        assertEquals(produto.getDescricao(), produtoCadastrado.getDescricao());
        assertEquals(produto.getPreco(), produtoCadastrado.getPreco());
        assertEquals(produto.getQuantidade_estoque(), produtoCadastrado.getQuantidade_estoque());


    }
    @Test
    @DisplayName("Deve atualizar um produto")
    void atualizarProduto(){

        ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);
        ProdutoService produtoService = new ProdutoService(produtoRepository);

        Produto produto = new Produto();
        produto.setDescricao("Descricao");
        produto.setId(1);
        produto.setNome("Nome");
        produto.setPreco(10.0d);
        produto.setQuantidade_estoque(1);
        Optional<Produto> resultado = Optional.of(produto);

        Produto produto2 = new Produto();
        produto2.setDescricao("Descricao");
        produto2.setId(1);
        produto2.setNome("Nome");
        produto2.setPreco(10.0d);
        produto2.setQuantidade_estoque(1);
        when(produtoRepository.save(Mockito.any())).thenReturn(produto2);
        when(produtoRepository.findById(Mockito.<Integer>any())).thenReturn(resultado);

        Produto novoProduto = new Produto();
        novoProduto.setDescricao("Descricao");
        novoProduto.setId(1);
        novoProduto.setNome("Nome");
        novoProduto.setPreco(10.0d);
        novoProduto.setQuantidade_estoque(1);

        Produto atualizarProdutoResultado = produtoService.atualizarProduto(1, novoProduto);
        verify(produtoRepository).findById(eq(1));
        verify(produtoRepository).save(isA(Produto.class));
        assertSame(produto2, atualizarProdutoResultado);


    }
}
