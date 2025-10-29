package com.poo.orion.Model;

import com.poo.orion.Enum.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "produto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    private String imagem;
    private String descricao;
    private String nome;
    private int estoque;
    private float preco;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    // Um produto pode aparecer em vários itens de pedido
    @OneToMany(mappedBy = "produto")
    private List<Carrinho> itens;
}
