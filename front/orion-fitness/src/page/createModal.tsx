import { useState } from "react";
import api from "../services/api";
import "../css/admin.css";

interface Props {
  onClose: () => void;
  reload: () => void;
}

export default function CreateModal({ onClose, reload }: Props) {
  const [form, setForm] = useState({
    nome: "",
    descricao: "",
    estoque: 0,
    preco: 0,
    categoria: "SUPLEMENTO"
  });

  const categorias = ["SUPLEMENTO", "LIFESTYLE_FITNESS", "ACESSORIOS_FITNESS"];

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await api.post("/produtos/criar", form);
      reload();
      onClose();
    } catch (error) {
      console.error("Erro ao criar produto:", error);
    }
  }

  return (
    <div className="modal">
      <div className="modal-content">
        <h2>Novo Produto</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" placeholder="Nome" value={form.nome} onChange={e => setForm({ ...form, nome: e.target.value })} required />
          <textarea placeholder="Descrição" value={form.descricao} onChange={e => setForm({ ...form, descricao: e.target.value })} required />
          <input type="number" placeholder="Estoque" value={form.estoque} onChange={e => setForm({ ...form, estoque: +e.target.value })} required />
          <input type="number" placeholder="Preço" value={form.preco} onChange={e => setForm({ ...form, preco: +e.target.value })} required />
          <select
            value={form.categoria}
            onChange={e => setForm({ ...form, categoria: e.target.value })}
          >
            {categorias.map(cat => (
              <option key={cat} value={cat}>
                {cat.replace("_", " ")}
              </option>
            ))}
          </select>

          <div className="modal-actions">
            <button type="submit">Salvar</button>
            <button onClick={onClose}>Cancelar</button>
          </div>
        </form>
      </div>
    </div>
  );
}
