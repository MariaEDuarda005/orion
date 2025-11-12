package com.poo.orion.Controller;

import com.poo.orion.DTO.CupomDTO;
import com.poo.orion.Repository.CupomRepository;
import com.poo.orion.Service.CupomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupons")
public class CupomController {

    private final CupomService service;

    public CupomController(CupomService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CupomDTO>> getAllCupons() {
        return ResponseEntity.ok(service.getAllCupons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CupomDTO> getCupomById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCupomById(id));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<CupomDTO>> getCuponsAtivosValidos() {
        return ResponseEntity.ok(service.getCuponsAtivosValidos());
    }

    @PostMapping("/criar")
    public ResponseEntity<CupomDTO> criarCupom(@RequestBody CupomDTO dto) {
        CupomDTO criado = service.createCupom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CupomDTO> atualizarCupom(
            @PathVariable Long id,
            @RequestBody CupomDTO dto
    ) {
        return ResponseEntity.ok(service.putCupom(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCupom(@PathVariable Long id) {
        service.deleteCupom(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        service.deleteAllCupons();
        return ResponseEntity.noContent().build();
    }
}
