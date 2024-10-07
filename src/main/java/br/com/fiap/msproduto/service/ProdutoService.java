package br.com.fiap.msproduto.service;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.reporitory.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProduto() {
        return produtoRepository.findAll();
    }


    public Optional<Produto> buscaProdutoPorId(int id) {
        return produtoRepository.findById(id);
    }

    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Integer id, Produto novoProduto) {
        Produto produtoExiste = produtoRepository.findById(id).orElse(null);
        if (produtoExiste != null) {
            produtoExiste.setNome(novoProduto.getNome());
            produtoExiste.setDescricao(novoProduto.getDescricao());
            produtoExiste.setQuantidade_estoque(novoProduto.getQuantidade_estoque());
            produtoExiste.setPreco(novoProduto.getPreco());
            return produtoRepository.save(produtoExiste);
        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public Produto atualizarEstoque(Integer id, int quantidade){
        Produto produto = produtoRepository.findById(id).orElse(null);
        if(produto != null){
            produto.setQuantidade_estoque(produto.getQuantidade_estoque() - quantidade);
            return produtoRepository.save(produto);
        }
        return null;
    }


    public void deletaProduto(Integer produtoId) {
       Produto produtoExiste = produtoRepository.findById(produtoId).orElse(null);

       if(produtoExiste != null){
           produtoRepository.delete(produtoExiste);
       }else{
           throw new NullPointerException("Produto não encontrado");
       }
    }
}
