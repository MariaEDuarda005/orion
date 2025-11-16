import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../services/api";
import imagemPadrao from "../assets/imagem.png";
import "../css/produtoIndividual.css";

interface Produto {
  id: number;
  nome: string;
  preco: number;
  descricao: string;
  categoria: string;
  imagem: string;
}

function ProdutoIndividual() {
  const { id } = useParams();
  const [produto, setProduto] = useState<Produto | null>(null);
  const [relacionados, setRelacionados] = useState<Produto[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    buscarProduto();
  }, [id]);

  async function buscarProduto() {
    try {
      setLoading(true);

      // Busca produto selecionado
      const response = await api.get(`/produtos/${id}`);
      setProduto(response.data);

      // Busca produtos relacionados (mesma categoria)
      const related = await api.get(
        `/produtos/categoria?categoria=${response.data.categoria}`
      );

      // Remove o próprio item da lista
      setRelacionados(related.data.filter((p: Produto) => p.id !== Number(id)));
    } catch (error) {
      console.error("Erro ao carregar produto", error);
    } finally {
      setLoading(false);
    }
  }

  if (loading || !produto) return <p>Carregando...</p>;

  return (
    <div className="produto-container">

      {/* BLOCO PRINCIPAL */}
      <div className="produto-main">
        <img
          src={produto.imagem ? produto.imagem : imagemPadrao}
          alt={produto.nome}
          className="produto-img"
        />

        <div className="produto-info">
          <h2>{produto.nome}</h2>
          <p className="produto-preco">R$ {produto.preco.toFixed(2)}</p>

          <div className="produto-stars">
            <i className="bi bi-star-fill"></i>
            <i className="bi bi-star-fill"></i>
            <i className="bi bi-star-fill"></i>
            <i className="bi bi-star-fill"></i>
            <i className="bi bi-star-half"></i>
          </div>

          <p className="produto-desc">{produto.descricao}</p>

          <button className="btn-add">Adicionar ao carrinho</button>
        </div>
      </div>

      {/* PRODUTOS RELACIONADOS */}
      <h3 className="titulo-relacionados">Produtos Relacionados</h3>

      <div className="relacionados-grid">
        {relacionados.map((item) => (
          <div className="item-produtos" key={item.id}>
            <img
              src={item.imagem ? item.imagem : imagemPadrao}
              alt={item.nome}
            />

            <p className="text1-produto">{item.nome}</p>

            <div className="estrelas">
              <i className="bi bi-star-fill"></i>
              <i className="bi bi-star-fill"></i>
              <i className="bi bi-star-fill"></i>
              <i className="bi bi-star-fill"></i>
              <i className="bi bi-star-half"></i>
            </div>

            <p className="text2-produto">R$ {item.preco.toFixed(2)}</p>

            <button
              className="comprar-produto"
              onClick={() => (window.location.href = `/produto/${item.id}`)}
            >
              Ver mais
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ProdutoIndividual;
