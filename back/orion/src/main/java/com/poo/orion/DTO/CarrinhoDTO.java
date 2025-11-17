package com.poo.orion.DTO;

import com.poo.orion.Model.Carrinho;

public record CarrinhoDTO(Long produtoId, int quantidade) {
    public static CarrinhoDTO from(Carrinho c) {
        return new CarrinhoDTO(
                c.getProduto().getIdProduto(),
                c.getQuantidade()
        );
    }
}