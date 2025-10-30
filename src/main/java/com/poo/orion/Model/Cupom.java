package com.poo.orion.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "cupom")
@Getter
@Setter
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
    private Date validadeInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "validade_fim")
    private Date validadeFinal;
}