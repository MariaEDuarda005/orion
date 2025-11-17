package com.poo.orion.Service;

import com.poo.orion.DTO.CarrinhoDTO;
import com.poo.orion.DTO.PedidoDTO;
import com.poo.orion.Model.*;
import com.poo.orion.Repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final CupomRepository cupomRepository;
    private final ProdutoRepository produtoRepository;
    private final CarrinhoRepository carrinhoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, CupomRepository cupomRepository, ProdutoRepository produtoRepository, CarrinhoRepository carrinhoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.cupomRepository = cupomRepository;
        this.produtoRepository = produtoRepository;
        this.carrinhoRepository = carrinhoRepository;
    }

    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoDTO::from)
                .collect(Collectors.toList());
    }

    public PedidoDTO getPedidoById(Long id) {
        return PedidoDTO.from(pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado")));
    }

    public PedidoDTO createPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setDataPedido(new Date());

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        if(dto.cupomId() != null){
            Cupom cupom = cupomRepository.findById(dto.cupomId())
                    .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
            pedido.setCupom(cupom);
        }

        List<Carrinho> carrinhoItens = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for(CarrinhoDTO itemDto : dto.itens()){
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Carrinho item = new Carrinho();
            item.setProduto(produto);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(BigDecimal.valueOf(produto.getPreco()));
            item.setPedido(pedido);
            carrinhoItens.add(item);

            valorTotal = valorTotal.add(item.getSubTotal());
        }

        pedido.setItens(carrinhoItens);
        pedido.setValorTotal(valorTotal);

        Pedido salvo = pedidoRepository.save(pedido);
        return PedidoDTO.from(salvo);
    }

    public void deletePedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }
}