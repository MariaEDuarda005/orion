import { Routes, Route } from "react-router-dom";
import Home from "../page/home";
import About from "../page/about";
import Produtos from "../page/produtos";
import Admin from "../page/admin";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/sobre" element={<About />} />
      <Route path="/produtos" element={<Produtos />} />
      <Route path="/admin" element={<Admin />} />
    </Routes>
  );
}
