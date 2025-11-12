package com.poo.orion.DTO;

import com.poo.orion.Enum.Categoria;
import com.poo.orion.Model.Produto;

public record ProdutoDTO(String nome, String descricao, int estoque, float preco, Categoria categoria) {
    public static ProdutoDTO from(Produto p) {
        return new ProdutoDTO(
                p.getNome(),
                p.getDescricao(),
                p.getEstoque(),
                p.getPreco(),
                p.getCategoria()
        );
    }

    public Produto toEntity() {
        Produto produto = new Produto();
        produto.setNome(this.nome);
        produto.setDescricao(this.descricao);
        produto.setEstoque(this.estoque);
        produto.setPreco(this.preco);
        produto.setCategoria(this.categoria);
        return produto;
    }
}
