package com.poo.orion.DTO;

import com.poo.orion.Model.Cliente;

public record ClienteDTO(String nomeCliente, String telefoneCliente, String cpf) {
    public static ClienteDTO from(Cliente c) {
        return new ClienteDTO(c.getNomeCliente(), c.getTelefoneCliente(), c.getCpf());
    }
}
