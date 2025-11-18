import { useState } from "react";
import { X } from "lucide-react";
import "../css/checkoutModal.css";
import type { CartData } from "../interface/card";
import api from "../services/api";

interface Props {
  onClose: () => void;
  cart: CartData[];
  discount: number;
  coupon: string;
  onClearCart: () => void;
}

export default function CheckoutModal({ onClose, cart, discount, coupon, onClearCart }: Props) {
  const [nome, setNome] = useState("");
  const [telefone, setTelefone] = useState("");
  const [cpf, setCpf] = useState("");
  const [loading, setLoading] = useState(false);

  const total = cart.reduce((acc, item) => acc + item.precoUnitario * item.quantidade, 0) - discount;

  async function handleSubmit() {
    if (!nome.trim() || !telefone.trim() || !cpf.trim()) {
      alert("Preencha todos os campos!");
      return;
    }

    try {
      setLoading(true);

      // Criar cliente
      const clienteResponse = await api.post("/clientes", { nomeCliente: nome, telefoneCliente: telefone, cpf });
      const cliente = clienteResponse.data;

      // Criar pedido
      await api.post("/pedidos/criar", {
        clienteId: cliente.id,
        cupomId: coupon.toLowerCase() === "desconto10" ? 1 : null,
        itens: cart.map(item => ({
          produtoId: item.produtoId,
          quantidade: item.quantidade,
        })),
      });

      // Gerar mensagem do WhatsApp
      let mensagem = `Olá ${nome}, seu pedido foi realizado:\n\n`;
      cart.forEach(item => {
        mensagem += `${item.quantidade}x ${item.produto?.nome} - R$ ${(item.precoUnitario * item.quantidade).toFixed(2)}\n`;
      });
      mensagem += `\nTotal: R$ ${total.toFixed(2)}`;

      // Abrir WhatsApp
      const whatsappUrl = `https://wa.me/${telefone.replace(/\D/g, "")}?text=${encodeURIComponent(mensagem)}`;
      window.open(whatsappUrl, "_blank");

      // Limpar carrinho e fechar modal
      onClearCart();
      onClose();
      alert("Pedido finalizado com sucesso!");
    } catch (error) {
      console.error(error);
      alert("Erro ao finalizar pedido.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <button className="modal-close" onClick={onClose}>X</button>
        <h2>Finalizar Pedido</h2>

        <input type="text" placeholder="Nome" value={nome} onChange={e => setNome(e.target.value)} />
        <input type="text" placeholder="Telefone" value={telefone} onChange={e => setTelefone(e.target.value)} />
        <input type="text" placeholder="CPF" value={cpf} onChange={e => setCpf(e.target.value)} />

        <button onClick={handleSubmit} disabled={loading}>
          {loading ? "Finalizando..." : `Enviar pedido (R$ ${total.toFixed(2)})`}
        </button>
      </div>
    </div>
  );
}