package com.poo.orion.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "cupom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCupom;

    private String codigo;
    private float percentualDesconto;
    private boolean ativo;

    @Temporal(TemporalType.DATE)
    @Column(name = "validade_inicio")
    private Date validade_inicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "validade_final")
    private Date validade_final;

    @OneToMany(mappedBy = "cupom")
    private Pedido pedido;
}