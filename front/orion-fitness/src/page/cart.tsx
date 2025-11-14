
import { useState } from "react";
import { Trash2 } from "lucide-react";
import CheckoutModal from "../components/checkoutModal";
import "../css/cart.css";

interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
  img?: string;
}

export default function Cart() {
  const [cart, setCart] = useState<CartItem[]>([
    { id: 1, name: "Creatina Monohidratada 300g", price: 119.9, quantity: 1 },
    { id: 2, name: "Regata DryFit Performance – Preta", price: 69.9, quantity: 2 },
  ]);

  const [discount, setDiscount] = useState<number>(60.4);
  const [coupon, setCoupon] = useState<string>("");
  const [showModal, setShowModal] = useState<boolean>(false);

  const subtotal = cart.reduce((acc, item) => acc + item.price * item.quantity, 0);
  const total = subtotal - discount;

  const updateQuantity = (id: number, delta: number) => {
    setCart((prev) =>
      prev.map((item) =>
        item.id === id
          ? { ...item, quantity: Math.max(item.quantity + delta, 1) }
          : item
      )
    );
  };

  const removeItem = (id: number) => {
    setCart((prev) => prev.filter((item) => item.id !== id));
  };

  const applyCoupon = () => {
    if (coupon.toLowerCase() === "desconto10") {
      setDiscount(10);
      alert("Cupom aplicado! R$10 de desconto.");
    } else {
      alert("Cupom inválido!");
    }
  };

  return (
    <div className="cart-container">
      <div style={{ flex: 1 }}>
        <h1 className="cart-title">Carrinho de compra</h1>

        <div className="cart-items">
          {cart.length === 0 && <p>Seu carrinho está vazio.</p>}

          {cart.map((item) => (
            <div className="cart-item" key={item.id}>
              <div style={{ display: "flex", alignItems: "center", gap: "1rem", flex: 1 }}>
                {item.img ? (
                  <img src={item.img} alt={item.name} />
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
                  <h4>{item.name}</h4>
                  <p>R$ {item.price.toFixed(2)}</p>
                </div>
              </div>

              <div style={{ display: "flex", alignItems: "center", gap: "0.5rem" }}>
                <button className="cart-qty-btn" onClick={() => updateQuantity(item.id, -1)}>
                  -
                </button>
                <span>{item.quantity}</span>
                <button className="cart-qty-btn" onClick={() => updateQuantity(item.id, 1)}>
                  +
                </button>
              </div>

              <div className="cart-item-price">
                <strong>R$ {(item.price * item.quantity).toFixed(2)}</strong>
              </div>

              <button
                className="cart-delete-btn"
                onClick={() => removeItem(item.id)}
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

      {showModal && <CheckoutModal onClose={() => setShowModal(false)} total={total} />}
    </div>
  );
}
