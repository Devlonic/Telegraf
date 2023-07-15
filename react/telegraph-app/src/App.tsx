import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import DefaultLayout from "./components/containers/default/DefaultLayout";
import AuthLayout from "./components/containers/auth/AuthLayout";
import LoginPage from "./components/auth/LoginPage";
import RegistrationPage from "./components/auth/RegistrationPage";
import SignOutPage from "./components/auth/SignOutPage";
import IndexPage from "./components/chat/IndexPage";
import ChannelMessagesPage from "./components/chat/ChannelMessagesList";
import ChatPage from "./components/chat/ChatPage";

function App() {
  return (
    <Routes>
      <Route path="/" element={<DefaultLayout />}>
        <Route index element={<IndexPage />}></Route>
        <Route path="chat">
          <Route path="channel">
            <Route path=":id" element={<ChatPage />}></Route>
          </Route>
          <Route path="private">
            {/* todo */}
            <Route path=":id" element={<ChannelMessagesPage />}></Route>
          </Route>
        </Route>
      </Route>
      <Route path="/auth" element={<AuthLayout />}>
        <Route index element={<LoginPage />}></Route>
        <Route path="login" element={<LoginPage />}></Route>
        <Route path="registration" element={<RegistrationPage />}></Route>
        <Route path="signout" element={<SignOutPage />}></Route>
      </Route>
    </Routes>
  );
}

export default App;
