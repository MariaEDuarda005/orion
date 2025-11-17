package com.poo.orion.Controller;

import com.poo.orion.Model.Pedido;
import com.poo.orion.Service.CarrinhoService;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CarrinhoService carrinhoService;

    public CheckoutController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/finalizar")
    public String finalizarCarrinho(
            @RequestParam Long clienteId,
            @RequestParam(required = false) Long cupomId,
            @RequestParam String numeroWhatsapp) {

        // Finaliza carrinho → cria Pedido
        Pedido pedido = carrinhoService.finalizarCarrinho(clienteId, cupomId);

        // Montar mensagem
        StringBuilder sb = new StringBuilder();
        sb.append("Olá! Segue meu pedido:\n");
        pedido.getItens().forEach(item -> {
            sb.append(item.getQuantidade())
                    .append("x ")
                    .append(item.getProduto().getNome())
                    .append(" - R$ ")
                    .append(item.getPrecoUnitario())
                    .append("\n");
        });
        sb.append("Total: R$ ").append(pedido.getValorTotal());

        // Codificar para URL
        String mensagem = URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8);

        // Gerar link do WhatsApp
        return "https://wa.me/" + numeroWhatsapp + "?text=" + mensagem;
    }
}
