import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";

import AuthService from "../services/authService.js";
import Header from "./header.js";

const Login = () => {
  const fixedInputClass="rounded-md appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"

  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
    setMessage("");
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
    setMessage("");
  };

  const handleLogin = (e) => {
    e.preventDefault();
    setMessage("");
    setLoading(true);

    AuthService.login(username).then(
      (res) => {
        if (res.data.password === password) {
          localStorage.setItem("user", JSON.stringify(res.data));
          navigate("/profile");
          window.location.reload();
        }
        else setMessage("Неправильный никнейм или пароль");
      },
      (error) =>
        setMessage("Неправильный никнейм или пароль")
    );
  };

  return <div>
    <Header
      heading="Войдите в аккаунт"
      paragraph="Еще нет аккаунта? "
      linkName="Зарегестрируйтесь"
      linkUrl="/login"/>
    <form className="mt-8 space-y-6">
      <input
        name="Никнейм"
        placeholder="Никнейм"
        onChange={onChangeUsername}
        value={username}
        className={fixedInputClass}/>
      <input
        name="Пароль"
        placeholder="Пароль"
        onChange={onChangePassword}
        value={password}
        className={fixedInputClass}/>
    </form>
      <button
          type="button"
          className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 mt-10"
          onClick={handleLogin}>
        Войти
      </button>
      {message && (
          <p className="mt-2 text-center text-sm text-gray-600 mt-5">
              {message}
          </p>
        )}
  </div>
};

export default Login;