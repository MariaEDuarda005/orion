package com.poo.orion.Service;

import com.poo.orion.DTO.ClienteDTO;
import com.poo.orion.Model.Cliente;
import com.poo.orion.Repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<ClienteDTO> getAllClientes() {
        return repository.findAll()
                .stream()
                .map(ClienteDTO::from)
                .collect(Collectors.toList());
    }

    public ClienteDTO getClienteById(Long id) {
        Cliente c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ClienteDTO.from(c);
    }

    public ClienteDTO createCliente(ClienteDTO dto) {
        if(repository.findByCpf(dto.cpf()).isPresent())
            throw new RuntimeException("CPF já cadastrado");

        Cliente cliente = new Cliente();
        cliente.setNomeCliente(dto.nomeCliente());
        cliente.setTelefoneCliente(dto.telefoneCliente());
        cliente.setCpf(dto.cpf());

        return ClienteDTO.from(repository.save(cliente));
    }

    public ClienteDTO updateCliente(Long id, ClienteDTO dto) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNomeCliente(dto.nomeCliente());
        cliente.setTelefoneCliente(dto.telefoneCliente());
        cliente.setCpf(dto.cpf());

        return ClienteDTO.from(repository.save(cliente));
    }

    public void deleteCliente(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if(!cliente.getPedidos().isEmpty())
            throw new RuntimeException("Cliente possui pedidos e não pode ser deletado");

        repository.delete(cliente);
    }
}
