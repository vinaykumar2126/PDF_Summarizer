import './App.css';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import React, { Suspense, lazy } from 'react';

const Login = lazy(()=> import('./components/login'));
const Pdfupload = lazy(()=> import('./components/pdfupload'));

function App() {
  return (
    <Router>
      <Suspense fallback={<div>Loading...</div>}>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/pdfupload" element={<Pdfupload />} />
        </Routes>
      </Suspense>
    </Router>
  );
}

export default App;
