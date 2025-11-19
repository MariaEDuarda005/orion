package com.poo.orion.Controller;

import com.poo.orion.DTO.PedidoItemDTO;
import com.poo.orion.Model.PedidoItem;
import com.poo.orion.Service.PedidoItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido-item")
public class PedidoItemController {

    private final PedidoItemService service;

    public PedidoItemController(PedidoItemService service) {
        this.service = service;
    }

    // Listar todos os itens de um pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PedidoItemDTO>> getItensByPedido(@PathVariable Long pedidoId) {
        List<PedidoItemDTO> itens = service.getItensByPedido(pedidoId);
        return ResponseEntity.ok(itens);
    }

    // Criar um novo item de pedido
    @PostMapping("/criar")
    public ResponseEntity<PedidoItemDTO> criarItem(@PathVariable Long pedidoId, @RequestBody PedidoItemDTO dto) {
        PedidoItemDTO criado = service.createItem(pedidoId, dto);
        return ResponseEntity.ok(criado);
    }

    // Atualizar item de pedido
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PedidoItemDTO> atualizarItem(@PathVariable Long id, @RequestBody PedidoItemDTO dto) {
        PedidoItemDTO atualizado = service.updateItem(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar item de pedido
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
