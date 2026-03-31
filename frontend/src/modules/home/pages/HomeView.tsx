import { Link } from "react-router-dom";

const HomeView = () => {
  return (
    <div style={{
      backgroundColor: "#F5EFEB",
      color: "#2F4156",
      fontFamily: "Arial, sans-serif",
      minHeight: "100vh",
      width: "100vw",
      margin: "0",
      padding: "0",
      display: "flex",
      flexDirection: "column",
      justifyContent: "space-between"
    }}>
      <header style={{
        display: "flex",
        padding: "10px 20px",
        backgroundColor: "#567C8D",
        width: "100%"
      }}>
        <Link to="/register">
          <button style={{
            margin: "5px",
            padding: "10px 15px",
            backgroundColor: "#C8D8E6",
            border: "none",
            borderRadius: "5px",
            color: "#2F4156",
            cursor: "pointer"
          }}>
            Cadastro
          </button>
        </Link>
        <Link to="/login">
          <button style={{
            margin: "5px",
            padding: "10px 15px",
            backgroundColor: "#C8D8E6",
            border: "none",
            borderRadius: "5px",
            color: "#2F4156",
            cursor: "pointer"
          }}>
            Login
          </button>
        </Link>
        <Link to="/about">
          <button style={{
            margin: "5px",
            padding: "10px 15px",
            backgroundColor: "#C8D8E6",
            border: "none",
            borderRadius: "5px",
            color: "#2F4156",
            cursor: "pointer"
          }}>
            Sobre o App
          </button>
        </Link>
      </header>

      <main style={{
        textAlign: "center",
        padding: "20px",
        flexGrow: 1,
        width: "100%"
      }}>
        <h1>Bem-vindo ao nosso site de passagens aéreas!</h1>
        <p>Escolha sua próxima viagem e decole conosco.</p>
        <span style={{ display: "block", fontSize: "50px" }}>✈️</span>
      </main>

      <footer style={{
        textAlign: "center",
        backgroundColor: "#FFFFFF",
        borderTop: "1px solid gray",
        width: "100%"
      }}>
        <p>Copyright 2025 Empresa Aérea DAC®</p>
      </footer>
    </div>
  );
};

export default HomeView;
