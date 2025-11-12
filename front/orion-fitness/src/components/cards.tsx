import "../css/card.css";
import imagemGeral from "../assets/imagem.png"

interface CardProps {
  nome: string;
  preco: number;
  descricao: string;
  imagem?: string;
}

export function Card({ nome, preco, descricao, imagem }: CardProps) {
  return (
    <div className="card">
      <img src={imagem || imagemGeral} alt={nome} />
      <h2>{nome}</h2>
      <p>{descricao}</p>
      <p><b>Preço:</b> R$ {preco.toFixed(2)}</p>
    </div>
  );
}
