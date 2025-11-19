package com.poo.orion.DTO;

import com.poo.orion.Model.PedidoItem;

import java.math.BigDecimal;

public record PedidoItemDTO(
        Long idProduto,
        String nomeProduto,
        BigDecimal precoUnitario,
        int quantidade,
        BigDecimal subtotal
) {
    public static PedidoItemDTO from(PedidoItem item) {
        return new PedidoItemDTO(
                item.getIdProduto(),
                item.getNomeProduto(),
                item.getPrecoUnitario(),
                item.getQuantidade(),
                item.getSubtotal()
        );
    }
}