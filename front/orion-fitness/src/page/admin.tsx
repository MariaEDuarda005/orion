import { useEffect, useState } from "react";
import api from "../services/api";
import type { produtosData } from "../interface/produtosData.tsx";
import "../css/admin.css";
import EditModal from "./editModal.tsx";
import CreateModal from "./createModal.tsx";
import type { produtosEdit } from "../interface/produtoEdit.tsx";

export default function Admin() {
  const [produtos, setProdutos] = useState<produtosData[]>([]);
  const [loading, setLoading] = useState(true);
  const [editProduto, setEditProduto] = useState<produtosEdit | null>(null);
  const [showCreate, setShowCreate] = useState(false);

  async function loadProdutos() {
    try {
      const response = await api.get<produtosData[]>("/produtos");
      setProdutos(response.data);
    } catch (error) {
      console.error("Erro ao carregar produtos:", error);
    } finally {
      setLoading(false);
    }
  }

  async function handleDelete(id: number | undefined) {
    if (confirm(`Tem certeza que deseja excluir o produto?`)) {
      try {
        const produto = produtos.find(p => p.id === id);
        if (produto) {
          await api.delete(`/produtos/${produto.id}`); 
          loadProdutos();
        }
      } catch (error) {
        console.error("Erro ao excluir:", error);
      }
    }
  }

  useEffect(() => {
    loadProdutos();
  }, []);

  if (loading) return <p>Carregando produtos...</p>;

  return (
    <div className="admin-container">
      <h1>🧑‍💼 Painel Administrativo</h1>

      <button className="btn-create" onClick={() => setShowCreate(true)}>+ Novo Produto</button>

      <table className="produtos-table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Estoque</th>
            <th>Preço (R$)</th>
            <th>Categoria</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {produtos.map((p, index) => (
            <tr key={index}>
              <td>{p.nome}</td>
              <td>{p.descricao}</td>
              <td>{p.estoque}</td>
              <td>{p.preco.toFixed(2)}</td>
              <td>{p.categoria}</td>
              <td>
                <button className="btn-edit" onClick={() => setEditProduto(p)}>Editar</button>
                <button className="btn-delete" onClick={() => handleDelete(p.id)}>Excluir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {editProduto && (
        <EditModal produto={editProduto} onClose={() => setEditProduto(null)} reload={loadProdutos} />
      )}

      {showCreate && (
        <CreateModal onClose={() => setShowCreate(false)} reload={loadProdutos} />
      )}
    </div>
  );
}
