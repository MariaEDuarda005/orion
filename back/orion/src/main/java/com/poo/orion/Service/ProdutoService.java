package com.poo.orion.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.poo.orion.dto.ProdutoDTO;
import com.poo.orion.Enum.Categoria;
import com.poo.orion.Model.Produto;
import com.poo.orion.Repository.ProdutoRepository;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<ProdutoDTO> getAllProdutos(){
        return repository.findAll()
                .stream()
                .map(ProdutoDTO::from)
                .collect(Collectors.toList());
    }

    public ProdutoDTO createProduto(ProdutoDTO produtodto){
        Produto produto = produtodto.toEntity();
        Produto create = repository.save(produto);
        return ProdutoDTO.from(create);
    }

    public ProdutoDTO putProduto(Long id, ProdutoDTO produtodto) {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produtodto.nome() != null && !produtodto.nome().trim().isEmpty()) {
            String novoNome = produtodto.nome().trim();

            if (!produto.getNome().equalsIgnoreCase(novoNome)) {
                if (repository.existsByNameIgnoreCase(novoNome)) {
                    throw new RuntimeException("Ops! Esse nome de produto já está cadastrado");
                }
                produto.setNome(novoNome);
            }
        }

        if (produtodto.descricao() != null) {
            produto.setDescricao(produtodto.descricao());
        }

        produto.setEstoque(produtodto.estoque());
        produto.setPreco(produtodto.preco());

        if (produtodto.categoria() != null) {
            produto.setCategoria(produtodto.categoria());
        }

        Produto atualizado = repository.save(produto);
        return ProdutoDTO.from(atualizado);
    }

    public void deleteProduto(Long id){
        if (!repository.existsById(id)){
            throw new RuntimeException("Não existe um produto com esse ID");
        }
        repository.deleteById(id);
    }

    public void deleteAllProdutos(){
        repository.deleteAll();
    }

    public List<ProdutoDTO> getProdutosByCategoria(String categoria) {

        Categoria catEnum = Categoria.valueOf(categoria.toUpperCase());

        List<Produto> produtos = repository.findByCategoria(catEnum);

        return produtos.stream()
                .map(ProdutoDTO::from)
                .collect(Collectors.toList());
    }
}