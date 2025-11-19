package com.poo.orion.DTO;

import com.poo.orion.Model.Pedido;

import java.util.List;

public record PedidoDTO(
        Long clienteId,
        Long cupomId,
        List<PedidoItemDTO> itens
) {
    public static PedidoDTO from(Pedido p) {
        List<PedidoItemDTO> itensDTO = p.getItens().stream()
                .map(PedidoItemDTO::from)
                .toList();

        return new PedidoDTO(
                p.getCliente().getIdCliente(),
                p.getCupom() != null ? p.getCupom().getIdCupom() : null,
                itensDTO
        );
    }
}
