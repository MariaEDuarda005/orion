package com.poo.orion.Service;

import com.poo.orion.DTO.CarrinhoDTO;
import com.poo.orion.DTO.PedidoItemDTO;
import com.poo.orion.Model.Carrinho;
import com.poo.orion.Model.Pedido;
import com.poo.orion.Model.PedidoItem;
import com.poo.orion.Model.Produto;
import com.poo.orion.Repository.PedidoItemRepository;
import com.poo.orion.Repository.PedidoRepository;
import com.poo.orion.Repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoItemService {

    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoItemService(PedidoItemRepository pedidoItemRepository,
                             PedidoRepository pedidoRepository,
                             ProdutoRepository produtoRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public void createItem(List<Carrinho> carrinhoList, Pedido pedido) {

        List<PedidoItem> pedidoItens = carrinhoList.stream().map(carrinho -> {
            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setIdProduto(carrinho.getIdProduto());
            item.setQuantidade(carrinho.getQuantidade());
            return item;
        }).toList();

        pedidoItemRepository.saveAll(pedidoItens);
    }
}
