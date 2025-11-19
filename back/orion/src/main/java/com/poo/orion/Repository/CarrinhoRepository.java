package com.poo.orion.Repository;

import com.poo.orion.Model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    // Buscar todos os itens que ainda não foram associados a um pedido
    List<Carrinho> findByPedidoIsNull();

    // Buscar itens de um cliente específico que ainda não foram associados a pedido
    List<Carrinho> findByClienteIdCliente(Long clienteId);

    void deleteByClienteIdCliente(Long idCliente);
}
