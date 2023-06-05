import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import DefaultLayout from "./components/containers/default/DefaultLayout";
import AuthLayout from "./components/containers/auth/AuthLayout";

function App() {
  return (
    <Routes>
      <Route path="/" element={<DefaultLayout />}></Route>
      <Route path="/auth" element={<AuthLayout />}></Route>
      <Route path="/chats" element={<DefaultLayout />}></Route>
    </Routes>
  );
}

export default App;
