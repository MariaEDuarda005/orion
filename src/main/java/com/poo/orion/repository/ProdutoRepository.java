package com.poo.orion.repository;

import com.poo.orion.Enum.Categoria;
import com.poo.orion.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCategoria(Categoria categoria);

    Optional<Produto> findByIdProduto(Long idProduto);

    Optional<Produto> findByNomeIgnoreCase(String nome);
}
