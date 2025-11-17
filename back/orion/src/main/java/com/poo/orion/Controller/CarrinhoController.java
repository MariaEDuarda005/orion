package com.poo.orion.Controller;

import com.poo.orion.DTO.CarrinhoDTO;
import com.poo.orion.Service.CarrinhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService service;

    public CarrinhoController(CarrinhoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> getCarrinhoById(@PathVariable Long id){
        return ResponseEntity.ok(service.getCarrinhoById(id));
    }

    @GetMapping
    public ResponseEntity<List<CarrinhoDTO>> getAllCarrinho(){
        return ResponseEntity.ok(service.getAllCarrinhos());
    }

    @PostMapping("/criar")
    public ResponseEntity<CarrinhoDTO> criarCarrinho(@RequestBody CarrinhoDTO dto) {
        return ResponseEntity.ok(service.createCarrinho(dto));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CarrinhoDTO> atualizarCarrinho(@PathVariable Long id, @RequestBody CarrinhoDTO dto) {
        return ResponseEntity.ok(service.updateCarrinho(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarrinho(@PathVariable Long id){
        service.deleteCarrinho(id);
        return ResponseEntity.noContent().build();
    }

    // Finalizar carrinho -> criar pedido
    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizarCarrinho(@RequestParam Long clienteId,
                                               @RequestParam(required = false) Long cupomId){
        return ResponseEntity.ok(service.finalizarCarrinho(clienteId, cupomId));
    }
}
