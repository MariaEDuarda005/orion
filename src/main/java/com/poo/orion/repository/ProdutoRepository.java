package com.poo.orion.repository;

import com.poo.orion.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByImagem(String imagem);

    Optional<Produto> findByIdProduto(Long idProduto);
}
