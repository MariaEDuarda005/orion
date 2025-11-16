package com.poo.orion.repository;

import com.poo.orion.Enum.Categoria;
import com.poo.orion.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCategoria(Categoria categoria);

    Optional<Produto> findByIdProduto(Long idProduto);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Produto p WHERE LOWER(p.nome) = LOWER(:nome)")
    boolean existsByNameIgnoreCase(@Param("nome") String nome);
}
