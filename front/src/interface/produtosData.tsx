export interface produtosData {
  id: number;
  nome: string;
  descricao: string;
  imagem: string;
  preco: number;
  estoque: number;
  categoria: string;

  itens?: {
    id: number;
    quantidade: number;
  }[];
}
