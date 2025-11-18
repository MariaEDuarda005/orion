package com.poo.orion.Controller;

import com.poo.orion.dto.ProdutoDTO;
import com.poo.orion.Model.Produto;
import com.poo.orion.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getId(@PathVariable Long id) {
        Produto produto = service.findById(id);
        return ResponseEntity.ok(ProdutoDTO.from(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos(){
        return ResponseEntity.ok(service.getAllProdutos());
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody @Valid ProdutoDTO produtoDTO){
        ProdutoDTO newProduto = service.createProduto(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduto);
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<ProdutoDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoDTO produtoDTO
    ) {
        return ResponseEntity.ok(service.putProduto(id, produtoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        service.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deletarTodos() {
        service.deleteAllProdutos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<ProdutoDTO>> getAllCategoria(
            @RequestParam(required = false) String categoria
    ){
        List<ProdutoDTO> produtos =
                (categoria != null)
                        ? service.getProdutosByCategoria(categoria)
                        : service.getAllProdutos();

        return ResponseEntity.ok(produtos);
    }
}