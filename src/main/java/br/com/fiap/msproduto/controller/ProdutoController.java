package br.com.fiap.msproduto.controller;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> buscaProdutoPorId(@PathVariable int id) {
        try {
            Optional<Produto> produto = produtoService.buscaProdutoPorId(id);
            if (produto.isPresent()) {
                return ResponseEntity.ok(produto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }

    }

    @PutMapping("/{idProduto}")
    public Produto atualizarProduto(@PathVariable Integer idProduto,
                                    @RequestBody Produto novoProduto) {
        return produtoService.atualizarProduto(idProduto, novoProduto);
    }

    @PostMapping
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @PutMapping("/atualizar/estoque/{id}/{quantidade}")
    public Produto atualizaEstoque(@PathVariable Integer id, @PathVariable int quantidade) {
        return produtoService.atualizarEstoque(id, quantidade);
    }

    @DeleteMapping("/{produtoId}")
    public void deletaProduto(@PathVariable Integer produtoId) {
        produtoService.deletaProduto(produtoId);
    }


}

