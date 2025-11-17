package com.poo.orion.Service;

import com.poo.orion.DTO.CarrinhoDTO;
import com.poo.orion.Model.*;
import com.poo.orion.Repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ClienteRepository clienteRepository;
    private final CupomRepository cupomRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, ClienteRepository clienteRepository, CupomRepository cupomRepository, ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
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
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Carrinho item = new Carrinho();
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

    // Finalizar carrinho -> criar pedido
    public Pedido finalizarCarrinho(Long clienteId, Long cupomId) {
        // Buscar todos os itens do carrinho que não estão associados a pedido
        List<Carrinho> carrinhoItems = carrinhoRepository.findByPedidoIsNull();
        if(carrinhoItems.isEmpty()) throw new RuntimeException("Carrinho vazio");

        Pedido pedido = new Pedido();

        // Buscar cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        // Buscar cupom (se fornecido)
        if (cupomId != null) {
            Cupom cupom = cupomRepository.findById(cupomId)
                    .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
            pedido.setCupom(cupom);
        }

        pedido.setDataPedido(new java.util.Date());

        // Calcular valor total
        BigDecimal total = carrinhoItems.stream()
                .map(Carrinho::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorTotal(total);

        pedidoRepository.save(pedido);

        // Associar itens do carrinho ao pedido
        carrinhoItems.forEach(item -> item.setPedido(pedido));
        carrinhoRepository.saveAll(carrinhoItems);

        return pedido;
    }
}
