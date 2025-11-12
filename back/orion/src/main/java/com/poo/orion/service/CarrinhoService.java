package com.poo.orion.Service;

import com.poo.orion.DTO.CarrinhoDTO;
import com.poo.orion.Model.Carrinho;
import com.poo.orion.Model.Pedido;
import com.poo.orion.Model.Produto;
import com.poo.orion.Repository.*;
import com.poo.orion.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<CarrinhoDTO> getAllCarrinhos() {
        return carrinhoRepository.findAll().stream().map(CarrinhoDTO::from).collect(Collectors.toList());
    }

    public CarrinhoDTO getCarrinhoById(Long id) {
        return CarrinhoDTO.from(carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado")));
    }

    public CarrinhoDTO createCarrinho(Long pedidoId, CarrinhoDTO dto) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Carrinho item = new Carrinho();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(BigDecimal.valueOf(produto.getPreco()));

        return CarrinhoDTO.from(carrinhoRepository.save(item));
    }

    public CarrinhoDTO updateCarrinho(Long id, CarrinhoDTO dto) {
        Carrinho item = carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(BigDecimal.valueOf(produto.getPreco()));

        return CarrinhoDTO.from(carrinhoRepository.save(item));
    }

    public void deleteCarrinho(Long id) {
        carrinhoRepository.deleteById(id);
    }
}
