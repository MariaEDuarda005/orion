import { useState } from "react";
import { X } from "lucide-react";
import "../css/checkoutModal.css";

interface Props {
  onClose: () => void;
  total: number;
  onCheckout: (nome: string, telefone: string, cpf: string) => Promise<void>; // nova prop
}

export default function CheckoutModal({ onClose, total, onCheckout }: Props) {
  const [nome, setNome] = useState("");
  const [telefone, setTelefone] = useState("");
  const [cpf, setCpf] = useState("");

  const [loading, setLoading] = useState(false);

  async function handleSubmit() {
    if (!nome.trim() || !telefone.trim() || !cpf.trim()) {
      alert("Por favor, preencha todos os campos antes de enviar o pedido.");
      return;
    }

    try {
      setLoading(true);
      await onCheckout(nome, telefone, cpf); // chama a função que cria o cliente + pedido
      alert("Pedido finalizado com sucesso!");
      onClose();
    } catch (err) {
      console.error(err);
      alert("Erro ao finalizar o pedido.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <button className="modal-close" onClick={onClose}>
          <X size={22} />
        </button>

        <h2 className="modal-title">Finalizar Pedido</h2>

        <div className="modal-group">
          <label>Nome</label>
          <input
            type="text"
            placeholder="Seu nome completo"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />
        </div>

        <div className="modal-group">
          <label>Telefone</label>
          <input
            type="text"
            placeholder="(00) 00000-0000"
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
          />
        </div>

        <div className="modal-group">
          <label>CPF</label>
          <input
            type="text"
            placeholder="000.000.000-00"
            value={cpf}
            onChange={(e) => setCpf(e.target.value)}
          />
        </div>

        <button className="modal-submit" onClick={handleSubmit} disabled={loading}>
          {loading ? "Finalizando..." : `Enviar pedido (R$ ${total.toFixed(2)})`}
        </button>
      </div>
    </div>
  );
}