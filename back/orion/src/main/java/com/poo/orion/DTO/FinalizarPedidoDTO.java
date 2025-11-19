package com.poo.orion.DTO;

public record FinalizarPedidoDTO(
    Long cupomId,
    Long clienteId,
    String nome,
    String cpf,
    String numero
) {
}
