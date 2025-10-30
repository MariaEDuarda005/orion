package com.poo.orion.controller;

import com.poo.orion.DTO.ProdutoDTO;
import com.poo.orion.Model.Produto;
import com.poo.orion.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> getId(@PathVariable Long id) {
        Produto produto = service.findById(id);
        return ResponseEntity.ok(ProdutoDTO.from(produto));
    }
}
