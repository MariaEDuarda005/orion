package com.poo.orion.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "produto")
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    private String imagem;
    private String descricao;
    private String nome;
    private int estoque;
    private float preco;

    // Um produto pode aparecer em vários itens de pedido
    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itens;
}
