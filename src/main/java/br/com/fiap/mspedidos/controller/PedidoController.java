package br.com.fiap.mspedidos.controller;

import br.com.fiap.mspedidos.model.Pedido;
import br.com.fiap.mspedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listarPedidos(){
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public Optional<Pedido> buscarPedidoPorId(@PathVariable String id){
        return pedidoService.buscarPedidoPorId(id);
    }

    @PostMapping
    public ResponseEntity <?>criarPedido(@RequestBody Pedido pedido){
    try{
        Pedido novoPedido = pedidoService.criarPedido(pedido);
        return new ResponseEntity(novoPedido, HttpStatus.CREATED);
    }catch(NoSuchElementException e){
        return new ResponseEntity<>("Um ou mais produtos nao estão disponiveis", HttpStatus.BAD_REQUEST);
        }
    }

}
