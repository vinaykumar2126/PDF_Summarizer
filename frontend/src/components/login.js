import { useState } from "react";
import axios from "axios";
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => { //explicit async
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/auth/login",
        {username,password},
        {headers: {
          "Content-Type": "application/json"}}
      );

      const token = response.data; // backend returns raw text
      localStorage.setItem("token", token);

      setMessage("Login successful! Token stored.");
      navigate("/pdfupload");  // Redirect to PDF upload page after login
      console.log("TOKEN:", token);

    } catch (error) {
      if(error.response && error.response.status===401){
        setMessage("Invalid credentials");
      }else{
        setMessage("Error connecting to backend");
      }
      console.error(error, + message);
    }
  };

  return (
    <div style={{ width: "300px", margin: "40px auto", textAlign: "center" }}>
      <h2>Login</h2>

      <form onSubmit={handleSubmit}>
        <input 
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          style={{ width: "100%", padding: "10px", marginBottom: "10px" }}
        />

        <input 
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={{ width: "100%", padding: "10px", marginBottom: "10px" }}
        />

        <button 
          type="submit"
          style={{ width: "100%", padding: "10px", cursor: "pointer" }}
        >
          Login
        </button>
      </form>
    </div> 
  );
}

