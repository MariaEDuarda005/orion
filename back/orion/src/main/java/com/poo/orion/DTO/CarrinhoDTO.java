package com.poo.orion.DTO;

import com.poo.orion.Model.Carrinho;

import java.math.BigDecimal;

public record CarrinhoDTO(
        Long idCarrinho,
        Long idProduto,
        int quantidade
) {
    public static CarrinhoDTO from(Carrinho c) {
        return new CarrinhoDTO(
                c.getIdCarrinho(),
                c.getIdProduto(),
                c.getQuantidade()
        );
    }
}
