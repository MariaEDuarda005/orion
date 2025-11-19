package com.poo.orion.Model;

import com.poo.orion.Enum.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "produto")
@Getter
@Setter
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
}
