package br.com.fiap.mspedidos.repository;

import br.com.fiap.mspedidos.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, String> {
}
