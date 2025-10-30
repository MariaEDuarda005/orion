package com.poo.orion.DTO;

import com.poo.orion.Model.Produto;

public record ProdutoDTO(Long id, String nome, String descricao, float preco) {
    public static ProdutoDTO from(Produto p) {
        return new ProdutoDTO(p.getIdProduto(), p.getNome(), p.getDescricao(), p.getPreco());
    }
}
