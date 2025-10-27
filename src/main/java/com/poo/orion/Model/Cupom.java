package com.poo.orion.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cupom")
@Data
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCupom;

    private String codigo;
    private float percentualDesconto;
    private boolean ativo;

    @OneToOne(mappedBy = "cupom")
    private Pedido pedido;
}