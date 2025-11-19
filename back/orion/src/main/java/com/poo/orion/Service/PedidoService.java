package com.poo.orion.Service;

import com.poo.orion.DTO.CarrinhoDTO;
import com.poo.orion.DTO.FinalizarPedidoDTO;
import com.poo.orion.DTO.PedidoDTO;
import com.poo.orion.DTO.PedidoItemDTO;
import com.poo.orion.Model.*;
import com.poo.orion.Repository.*;
import jakarta.transaction.Transactional;
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
    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoItemService pedidoItemService;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, CupomRepository cupomRepository, ProdutoRepository produtoRepository, CarrinhoRepository carrinhoRepository, PedidoItemRepository pedidoItemRepository, PedidoItemService pedidoItemService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.cupomRepository = cupomRepository;
        this.produtoRepository = produtoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoItemService = pedidoItemService;
    }

    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoDTO::from)
                .collect(Collectors.toList());
    }

    public PedidoDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return PedidoDTO.from(pedido);
    }

    @Transactional
    public void createPedido(FinalizarPedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setDataPedido(new Date());

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        if (dto.cupomId() != null) {
            Cupom cupom = cupomRepository.findById(dto.cupomId())
                    .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
            pedido.setCupom(cupom);
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        List<Carrinho> carrinhoList = this.carrinhoRepository.findByClienteIdCliente(dto.clienteId());

        this.pedidoItemService.createItem(carrinhoList, pedidoSalvo);
        this.carrinhoRepository.deleteByClienteIdCliente(dto.clienteId());
    }
}