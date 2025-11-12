import { useState } from "react";
import api from "../services/api";
import type { produtosEdit } from "../interface/produtoEdit";
import "../css/admin.css";

interface Props {
  produto: produtosEdit;
  onClose: () => void;
  reload: () => void;
}

export default function EditModal({ produto, onClose, reload }: Props) {
  const [form, setForm] = useState({
    nome: produto.nome,
    descricao: produto.descricao,
    estoque: produto.estoque,
    preco: produto.preco,
    categoria: produto.categoria
  });

  const categorias = ["SUPLEMENTO", "LIFESTYLE_FITNESS", "ACESSORIOS_FITNESS"];

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    try {
      console.log("Enviando atualização para o produto:", produto.id);
      await api.put(`/produtos/atualizar/${produto.id}`, form);
      reload();
      onClose();
    } catch (error: any) {
      console.error("Erro ao atualizar produto:", error);
      alert("Erro ao atualizar produto: " + (error.response?.data?.message || error.message));
    }
  }

  return (
    <div className="modal">
      <div className="modal-content">
        <h2>Editar Produto</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            value={form.nome}
            onChange={e => setForm({ ...form, nome: e.target.value })}
          />
          <textarea
            value={form.descricao}
            onChange={e => setForm({ ...form, descricao: e.target.value })}
          />
          <input
            type="number"
            value={form.estoque}
            onChange={e => setForm({ ...form, estoque: +e.target.value })}
          />
          <input
            type="number"
            value={form.preco}
            onChange={e => setForm({ ...form, preco: +e.target.value })}
          />

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
