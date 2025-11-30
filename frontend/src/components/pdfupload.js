import {useState} from 'react';
import axios from 'axios';

export default function PDFUpload() {
  const [file, setFile] = useState(null);
  const [summary, setSummary] = useState("");
  const [message, setMessage] = useState("");

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);

    }
    const handleUpload = async () => {
        if(!file){
            setMessage("Please select a file first");
            return;
        }
        const token = localStorage.getItem("token");
        const formData = new FormData();
        formData.append("file", file);
        try {
            const response = await axios.post("http://localhost:8080/api/pdf/summarize", formData, {
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "multipart/form-data"
                }
            });
            setSummary(response.data);
            setMessage("Summary received!");
        } catch (error) {
            setMessage("Error summarizing PDF");
            console.error(error,+ message);
        }
    }

    return (
        <div style={{ width: "300px", margin: "40px auto", textAlign: "center" }}>
            <h2>Upload PDF</h2>
            <input
                type="file"
                accept="application/pdf"
                onChange={handleFileChange}
                style={{ width: "100%", marginBottom: "10px" }}
            />
            <button
                type="button"
                style={{ width: "100%", padding: "10px", cursor: "pointer" }}
                onClick={handleUpload}
            >
                Upload PDF
            </button>
            {summary && (
                <div style={{ marginTop: "20px", textAlign: "left" }}>
                    <h3>Summary:</h3>
                    <p>{summary}</p>
                </div>
            )}
        </div>
    );
}