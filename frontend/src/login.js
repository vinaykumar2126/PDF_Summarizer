import { useState } from "react";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        setMessage("Invalid credentials");
        return;
      }

      const token = await response.text(); // backend returns raw text
      localStorage.setItem("token", token);

      setMessage("Login successful! Token stored.");
      console.log("TOKEN:", token);

    } catch (error) {
      console.error(error);
      setMessage("Error connecting to backend");
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

      {message && <p style={{ marginTop: "10px" }}>{message}</p>}
      <button
        onClick={async () => {
          const token = localStorage.getItem("token");

          const response = await fetch("http://localhost:8080/api/pdf/hello", {
            method: "GET",
            headers: {
              "Authorization": "Bearer " + token
            }
          });

          const text = await response.text();
          alert(text);
          console.log("Protected API response:", text);
        }}
        style={{ marginTop: "20px", padding: "10px", width: "100%", cursor: "pointer" }}
      >
        Test Protected API
      </button>
    </div>
  );
}
