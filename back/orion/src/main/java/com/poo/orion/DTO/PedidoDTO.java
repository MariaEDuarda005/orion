package com.poo.orion.DTO;

import com.poo.orion.Model.Pedido;

import java.util.List;

public record PedidoDTO(
        Long clienteId,
        Long cupomId,
        List<CarrinhoDTO> itens,
        String status
) {
    public static PedidoDTO from(Pedido p) {
        List<CarrinhoDTO> itensDTO = p.getItens().stream()
                .map(CarrinhoDTO::from)
                .toList();

        return new PedidoDTO(
                p.getCliente().getIdCliente(),
                p.getCupom() != null ? p.getCupom().getIdCupom() : null,
                itensDTO,
                p.getStatus().name()
        );
    }
}

