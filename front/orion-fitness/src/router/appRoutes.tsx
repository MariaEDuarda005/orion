import { Routes, Route } from "react-router-dom";
import Home from "../page/home";
import About from "../page/about";
import Produtos from "../page/produtos";
import Admin from "../page/admin";
import Cart from "../page/cart"; 

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/sobre" element={<About />} />
      <Route path="/produtos" element={<Produtos />} />
      <Route path="/admin" element={<Admin />} />
      <Route path="/carrinho" element={<Cart />} /> 
    </Routes>
  );
}

