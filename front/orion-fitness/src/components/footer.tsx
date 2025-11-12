import Logo from "../assets/logo.png"
import { FaFacebook, FaInstagram, FaLinkedin } from "react-icons/fa";
import "../css/footer.css";

export default function Footer() {
  return (
    <footer>
        <div className="logo">
            <img src={Logo} alt="Logo Orion Fitness" />  
        </div>
        <div className="gerais">
            <p className="direitos">&copy; {new Date().getFullYear()} Nome da Sua Empresa. Todos os direitos reservados.</p>
            <div className="infos">
                <p>Localização: Rua Teste de teste, 598 - Parque Teste</p>
                <p>Telefone: (19) 99999-9999</p>
            </div>
            <div>
                <p>Siga nossa redes sociais</p>
                <div className="redes">
                    <FaFacebook />
                    <FaInstagram />
                    <FaLinkedin />
                </div>
            </div>
        </div>
    </footer>
  );
}
