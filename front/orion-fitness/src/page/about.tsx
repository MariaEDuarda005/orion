import "../css/about.css";

export default function Equipe() {
  return (
    <>
      <main className="about-container">
        <h1>Nossa Equipe de desenvolvimento</h1>

        <div className="team-wrapper">

          <div className="team-card">
            <div className="team-img carol-img"></div>
            <h3>Carolina Pinheiro</h3>
            <p>Matrícula: 202503740623</p>
          </div>

          <div className="team-card">
            <div className="team-img maria-img"></div>
            <h3>Maria Eduarda Ferreira</h3>
            <p>Matrícula: 202502219679</p>
          </div>

        </div>
      </main>
    </>
  );
}
