import { useState, useEffect } from "react";
import { Trash2 } from "lucide-react";
import type { CartData } from "../interface/card";
import type { produtosData } from "../interface/produtosData";
import CheckoutModal from "../components/checkoutModal";
import api from "../services/api";
import "../css/cart.css";

export default function Cart() {
  const [cart, setCart] = useState<CartData[]>([]);
  const [discount, setDiscount] = useState<number>(0);
  const [coupon, setCoupon] = useState<string>("");
  const [showModal, setShowModal] = useState<boolean>(false);

  // Buscar carrinho do backend e popular com produtos
  useEffect(() => {
    async function fetchCart() {
      try {
        const carrinhoResponse = await api.get<CartData[]>("/carrinho/usuario/1");
        const carrinhoData = carrinhoResponse.data;

        const produtosPromises = carrinhoData.map(async (item) => {
          const produtoResponse = await api.get<produtosData>(`/produtos/${item.produtoId}`);
          return { ...item, produto: produtoResponse.data };
        });

        const cartWithProducts = await Promise.all(produtosPromises);
        setCart(cartWithProducts);
      } catch (error) {
        console.error("Erro ao carregar carrinho:", error);
      }
    }

    fetchCart();
  }, []);

  const subtotal = cart.reduce((acc, item) => acc + item.precoUnitario * item.quantidade, 0);
  const total = subtotal - discount;

  const updateQuantity = (produtoId: number, delta: number) => {
    setCart((prev) =>
      prev.map((item) =>
        item.produtoId === produtoId
          ? { ...item, quantidade: Math.max(item.quantidade + delta, 1) }
          : item
      )
    );
  };

  const removeItem = (produtoId: number) => {
    setCart((prev) => prev.filter((item) => item.produtoId !== produtoId));
  };

  const applyCoupon = () => {
    if (coupon.toLowerCase() === "desconto10") {
      setDiscount(10);
      alert("Cupom aplicado! R$10 de desconto.");
    } else {
      alert("Cupom inválido!");
    }
  };

  // Função principal para finalizar compra
  const finalizarCompra = async (nome: string, telefone: string, cpf: string) => {
    try {
      // 1️⃣ Criar cliente no backend
      const clienteResponse = await api.post("/clientes", {
        nomeCliente: nome,
        telefoneCliente: telefone,
        cpf: cpf,
      });
      const cliente = clienteResponse.data;

      // 2️⃣ Finalizar carrinho -> gerar pedido
      const pedidoResponse = await api.post("/carrinho/finalizar", {
        clienteId: cliente.id,
        cupomId: coupon.toLowerCase() === "desconto10" ? 1 : null,
      });
      const pedido = pedidoResponse.data;

      // 3️⃣ Montar mensagem para WhatsApp
      let mensagem = `Olá ${nome}, seu pedido foi realizado:\n\n`;
      cart.forEach((item) => {
        mensagem += `${item.quantidade}x ${item.produto?.nome} - R$ ${(item.precoUnitario * item.quantidade).toFixed(2)}\n`;
      });
      mensagem += `\nTotal: R$ ${total.toFixed(2)}`;

      // 4️⃣ Abrir WhatsApp
      const whatsappUrl = `https://wa.me/${telefone.replace(/\D/g, "")}?text=${encodeURIComponent(mensagem)}`;
      window.open(whatsappUrl, "_blank");

      // 5️⃣ Limpar carrinho e fechar modal
      setCart([]);
      setShowModal(false);

      alert("Pedido finalizado com sucesso!");
    } catch (error) {
      console.error("Erro ao finalizar compra:", error);
      alert("Erro ao finalizar compra.");
    }
  };

  return (
    <div className="cart-container">
      <div style={{ flex: 1 }}>
        <h1 className="cart-title">Carrinho de compra</h1>

        <div className="cart-items">
          {cart.length === 0 && <p>Seu carrinho está vazio.</p>}

          {cart.map((item) => (
            <div className="cart-item" key={item.produtoId}>
              <div style={{ display: "flex", alignItems: "center", gap: "1rem", flex: 1 }}>
                {item.produto?.imagem ? (
                  <img src={item.produto.imagem} alt={item.produto.nome} />
                ) : (
                  <div
                    style={{
                      width: 75,
                      height: 75,
                      borderRadius: 8,
                      background: "#f3f3f3",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                      color: "#999",
                    }}
                  >
                    IMG
                  </div>
                )}

                <div className="cart-item-info">
                  <h4>{item.produto?.nome}</h4>
                  <p>R$ {item.precoUnitario.toFixed(2)}</p>
                </div>
              </div>

              <div style={{ display: "flex", alignItems: "center", gap: "0.5rem" }}>
                <button className="cart-qty-btn" onClick={() => updateQuantity(item.produtoId, -1)}>
                  -
                </button>
                <span>{item.quantidade}</span>
                <button className="cart-qty-btn" onClick={() => updateQuantity(item.produtoId, 1)}>
                  +
                </button>
              </div>

              <div className="cart-item-price">
                <strong>R$ {(item.precoUnitario * item.quantidade).toFixed(2)}</strong>
              </div>

              <button
                className="cart-delete-btn"
                onClick={() => removeItem(item.produtoId)}
                aria-label="Remover item"
              >
                <Trash2 size={18} />
              </button>
            </div>
          ))}
        </div>
      </div>

      <aside className="cart-summary">
        <h3>Resumo do pedido</h3>

        <div className="coupon-container">
          <input
            type="text"
            placeholder="Cupom de desconto"
            className="coupon-input"
            value={coupon}
            onChange={(e) => setCoupon(e.target.value)}
          />
          <button className="coupon-btn" onClick={applyCoupon}>
            Aplicar
          </button>
        </div>

        <div>
          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <p>SubTotal</p>
            <p>R$ {subtotal.toFixed(2)}</p>
          </div>

          <div style={{ display: "flex", justifyContent: "space-between", color: "#666" }}>
            <p>Desconto</p>
            <p>- R$ {discount.toFixed(2)}</p>
          </div>

          <div className="cart-total">
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <span>Total</span>
              <span>R$ {total.toFixed(2)}</span>
            </div>
          </div>
        </div>

        <button className="checkout-btn" onClick={() => setShowModal(true)}>
          Finalizar compra
        </button>
      </aside>

      {showModal && (
        <CheckoutModal
          onClose={() => setShowModal(false)}
          onCheckout={finalizarCompra}
          total={total}
        />
      )}
    </div>
  );
}