import { useState } from "react";
import axios from "axios";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [file, setFile] = useState(null);
  const [summary, setSummary] = useState("");

  const handleSubmit = async (e) => { //explicit async
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/auth/login",
        {username,password},
        {headers: {
          "Content-Type": "application/json"}}
      );

      // if (!response.ok) {
      //   setMessage("Invalid credentials");
      //   return;
      // }

      const token = response.data; // backend returns raw text
      localStorage.setItem("token", token);

      setMessage("Login successful! Token stored.");
      console.log("TOKEN:", token);

    } catch (error) {
      if(error.response && error.response.status===401){
        setMessage("Invalid credentials");
      }else{
        setMessage("Error connecting to backend");
      }
      console.error(error);
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

        <input
          type="file"
          accept="application/pdf"
          onChange={e => setFile(e.target.files[0])}
          style={{ width: "100%", marginBottom: "10px" }}
        />
        <button
          type="button"
          style={{ width: "100%", padding: "10px", cursor: "pointer", marginTop: "10px" }}
          onClick={async () => {
            if (!file) {
              setMessage("Please select a PDF file first.");
              return;
            }
            const token = localStorage.getItem("token");
            const formData = new FormData();
            formData.append("file", file);

            try {
              const response = await axios.post(
                "http://localhost:8080/api/pdf/summarize",
                formData,
                {
                  headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "multipart/form-data"
                  }
                }
              );
              setSummary(response.data); // assuming backend returns plain text summary
              setMessage("Summary received!");
            } catch (error) {
              setMessage("Error summarizing PDF");
              console.error(error);
            }
          }}
        >Upload PDF</button>
      </form>
      {summary && (<div style={{ marginTop: "20px", textAlign: "left" }}>
        <h3>Summary:</h3>
        <p>{summary}</p>
      </div>
      )}

      {message && <p style={{ marginTop: "10px" }}>{message}</p>}
      <button
        onClick={async () => {
          const token = localStorage.getItem("token");

          const response = await axios.get("http://localhost:8080/api/pdf/hello", {
            headers: {
              "Authorization": "Bearer " + token
            }
          });

          const text = response.data;
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
