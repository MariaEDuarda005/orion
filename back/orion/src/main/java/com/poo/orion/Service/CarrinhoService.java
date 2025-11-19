package com.poo.orion.Service;

import com.poo.orion.DTO.CarrinhoDTO;
import com.poo.orion.Model.*;
import com.poo.orion.Repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ClienteRepository clienteRepository;
    private final CupomRepository cupomRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, ClienteRepository clienteRepository,
                           CupomRepository cupomRepository, ProdutoRepository produtoRepository,
                           PedidoRepository pedidoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.clienteRepository = clienteRepository;
        this.cupomRepository = cupomRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<CarrinhoDTO> getAllCarrinhos() {
        return carrinhoRepository.findAll().stream()
                .map(CarrinhoDTO::from)
                .collect(Collectors.toList());
    }

    public CarrinhoDTO getCarrinhoById(Long id) {
        Carrinho item = carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        return CarrinhoDTO.from(item);
    }

    public CarrinhoDTO createCarrinho(CarrinhoDTO dto) {
        Produto produto = produtoRepository.findById(dto.idProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Carrinho item = new Carrinho();
        item.setIdProduto(produto.getIdProduto());
        item.setNomeProduto(produto.getNome());
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(BigDecimal.valueOf(produto.getPreco()));

        return CarrinhoDTO.from(carrinhoRepository.save(item));
    }

    public CarrinhoDTO updateCarrinho(CarrinhoDTO dto) {
        // find carrinhoId

        // atualiza o carirnho (quantidade e da um save)
    }

    public void deleteCarrinho(Long id) {
        carrinhoRepository.deleteById(id);
    }
}