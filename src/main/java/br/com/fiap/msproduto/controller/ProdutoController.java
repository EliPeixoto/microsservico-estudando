package br.com.fiap.msproduto.controller;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping()
    public List<Produto> listarProdutos() {
        return produtoService.listarProduto();
    }

    @GetMapping("/{id}")
    public Optional<Produto>  buscaProdutoPorId(@PathVariable int id){
        return produtoService.buscaProdutoPorId(id);
    }

    @PutMapping("/{idProduto}")
    public Produto atualizarProduto(@PathVariable Integer idProduto,
                                    @RequestBody Produto novoProduto){
        return produtoService.atualizarProduto(idProduto, novoProduto);
    }
    @PostMapping
    public Produto cadastrarProduto(@RequestBody Produto produto){
        return produtoService.cadastrarProduto(produto);
    }

    @PutMapping("/atualizar/estoque/{id}/{quantidade}")
    public Produto atualizaEstoque(@PathVariable Integer id, @PathVariable int quantidade){
        return produtoService.atualizarEstoque(id, quantidade);
    }

    @DeleteMapping("/{produtoId}")
    public void deletaProduto(@PathVariable Integer produtoId){
         produtoService.deletaProduto(produtoId);
    }


}

