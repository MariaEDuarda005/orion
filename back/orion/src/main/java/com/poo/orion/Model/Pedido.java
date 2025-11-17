package com.poo.orion.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Temporal(TemporalType.DATE)
    private Date dataPedido;

    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Um pedido pode ter vários itens do carrinho (produtos)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carrinho> itens;

    // Um pedido pode ter um cupom
    @ManyToOne
    @JoinColumn(name = "id_cupom")
    private Cupom cupom;
}