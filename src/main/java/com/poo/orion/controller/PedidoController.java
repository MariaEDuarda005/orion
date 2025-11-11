package com.poo.orion.Controller;

import com.poo.orion.DTO.PedidoDTO;
import com.poo.orion.Service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos(){
        return ResponseEntity.ok(service.getAllPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id){
        return ResponseEntity.ok(service.getPedidoById(id));
    }

    @PostMapping("/atualizar/{id}")
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable Long id, String status){
        return ResponseEntity.ok(service.updatePedidoStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        service.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
