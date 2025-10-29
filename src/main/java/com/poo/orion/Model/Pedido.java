package com.poo.orion.Model;

import com.poo.orion.Enum.Status;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Temporal(TemporalType.DATE)
    private Date dataPedido;

    private float valorTotal;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Um pedido tem vários itens
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Carrinho> itens;

    @OneToOne
    @JoinColumn(name = "id_cupom")
    private Cupom cupom;
}
