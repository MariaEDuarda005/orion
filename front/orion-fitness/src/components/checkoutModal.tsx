import { useState } from "react";
import { X } from "lucide-react";
import "../css/checkoutModal.css";

interface Props {
  onClose: () => void;
  total: number;
}

export default function CheckoutModal({ onClose, total }: Props) {
  const [nome, setNome] = useState("");
  const [telefone, setTelefone] = useState("");
  const [cpf, setCpf] = useState("");

  function handleSubmit() {
    if (!nome.trim() || !telefone.trim() || !cpf.trim()) {
      alert("Por favor, preencha todos os campos antes de enviar o pedido.");
      return;
    }

  
    const msg = `
Novo pedido realizado:

Nome: ${nome}
Telefone: ${telefone}
CPF: ${cpf}

Total: R$ ${total.toFixed(2)}
    `;

    const numero = "5519999999"; 
    const url = `https://wa.me/${numero}?text=${encodeURIComponent(msg)}`;

    window.open(url, "_blank");

    onClose();
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

        <div class="modal-group">
          <label>CPF</label>
          <input
            type="text"
            placeholder="000.000.000-00"
            value={cpf}
            onChange={(e) => setCpf(e.target.value)}
          />
        </div>

        <button className="modal-submit" onClick={handleSubmit}>
          Enviar pedido (R$ {total.toFixed(2)})
        </button>
      </div>
    </div>
  );
}
