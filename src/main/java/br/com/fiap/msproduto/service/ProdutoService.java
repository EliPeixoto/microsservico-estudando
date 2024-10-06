package br.com.fiap.msproduto.service;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.reporitory.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProduto() {
     return produtoRepository.findAll();
    }


    public Optional<Produto> buscaProdutoPorId(int id){
        return produtoRepository.findById(id);
    }

    public Produto cadastrarProduto(Produto produto){
        return produtoRepository.save(produto);
    }
}
