package br.com.fiap.mspedidos.service;

import br.com.fiap.mspedidos.model.ItemPedido;
import br.com.fiap.mspedidos.model.Pedido;
import br.com.fiap.mspedidos.repository.PedidoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPedidoPorId(String id) {
        return pedidoRepository.findById(id);
    }

    public Pedido criarPedido(Pedido pedido) {
        boolean produtosDisponiveis = verificarDisponibiliadeProdutos(pedido.getItensPedidos());

        if (!produtosDisponiveis) {
            throw new NoSuchElementException("Um ou mais produtos não estão disponiveis");
        }
        double valorTotal = calcularValorTotal(pedido.getItensPedidos());
        pedido.setValorTotal(valorTotal);

        atualizarEstoqueProdutos(pedido.getItensPedidos());

        return pedidoRepository.save(pedido);
    }


    private boolean verificarDisponibiliadeProdutos(List<ItemPedido> itensPedido) {
        for (ItemPedido itemPedido : itensPedido) {
            Integer idProduto = itemPedido.getId();
            int quantidade = itemPedido.getQuantidade();

            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://localhost:9090/api/produto/{idProduto}",
                    String.class,
                    idProduto
            );
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoSuchElementException("Produto não encontrado");
            } else {

                try {
                    JsonNode produtoJson = objectMapper.readTree((response.getBody()));
                    int quantidadeEstoque = produtoJson.get("quantidade_estoque").asInt();
                    if (quantidadeEstoque < quantidade) {
                        return false;
                    }
                } catch (IOException e) {
                    // tratar depois
                }
            }
        }
        return true;
    }

    private double calcularValorTotal(List<ItemPedido> itensPedido) {
        double valorTotal = 0.0;

        for (ItemPedido itemPedido : itensPedido) {
            Integer idProduto = itemPedido.getId();
            int quantidade = itemPedido.getQuantidade();
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://localhost:9090/api/produto/{idProduto}",
                    String.class,
                    idProduto
            );
            if(response.getStatusCode() == HttpStatus.OK){
               try{
                   JsonNode produtoJson = objectMapper.readTree(response.getBody());
                   double preco = produtoJson.get("preco").asDouble();
                   valorTotal += preco * quantidade;
               }catch (IOException e){
                   //tratar depois
               }
            }
        }

        return valorTotal;
    }

    private void atualizarEstoqueProdutos(List<ItemPedido> itensPedido){
        for (ItemPedido itemPedido:itensPedido){
            Integer idProduto = itemPedido.getId();
            int quantidade = itemPedido.getQuantidade();

            restTemplate.put(
                    "http://localhost:9090/api/produto/atualizar/estoque/{id}/{quantidade}",
                    null,
                    idProduto,
                    quantidade
            );
        }
    }
}
