package com.poo.orion.service;

import com.poo.orion.Model.Produto;
import com.poo.orion.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto findById(Long id){
        return repository.findByIdProduto(id).orElse(null);
    }

}
