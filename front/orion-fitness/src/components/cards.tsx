import { produtosEdit } from "../interface/produtosEdit";

interface ProdutoProps {
  data: produtosEdit;
  imagem: string;
}

export default function Produto({ data, imagem }: ProdutoProps) {
  return (
    <div className="item-produtos">
      <img src={imagem} alt={data.nome} />

      <p className="text1-produto">{data.nome}</p>

      {/* Aqui deixei estrelas fixas — você pode colocar dinâmicas se quiser */}
      <div className="estrelas">
        <i className="bi bi-star-fill"></i>
        <i className="bi bi-star-fill"></i>
        <i className="bi bi-star-fill"></i>
        <i className="bi bi-star-fill"></i>
        <i className="bi bi-star-fill"></i>
      </div>

      <p className="text2-produto">R$ {data.preco.toFixed(2)}</p>
      <p className="text3-produto">Preço à vista com desconto</p>

      <button className="comprar-produto">Comprar</button>
    </div>
  );
}
