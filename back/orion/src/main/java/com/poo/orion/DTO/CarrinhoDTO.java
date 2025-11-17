package com.poo.orion.DTO;

import com.poo.orion.Model.Carrinho;

import java.math.BigDecimal;

public record CarrinhoDTO(Long produtoId, int quantidade, BigDecimal precoUnitario) {
    public static CarrinhoDTO from(Carrinho c) {
        return new CarrinhoDTO(
                c.getProduto().getIdProduto(),
                c.getQuantidade(),
                c.getPrecoUnitario()
        );
    }
}