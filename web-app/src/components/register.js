import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import AuthService from "../services/authService.js";
import Header from "./header.js";


const Register = () => {
    const fixedInputClass="rounded-md appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"

    const [username, setUsername] = useState("");
    const [name, setName] = useState("");
    const [phone, setPhone] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");

    const navigate = useNavigate();

    const onChangeUsername = (e) => {
        const username = e.target.value;
        setUsername(username);
        setMessage("");
    };

    const onChangeName = (e) => {
        const name = e.target.value;
        setName(name);
        setMessage("");
    };

    const onChangeEmail = (e) => {
        const email = e.target.value;
        setEmail(email);
        setMessage("");
    };

    const onChangePhone = (e) => {
        const phone = e.target.value;
        setPhone(phone);
        setMessage("");
    };

    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
        setMessage("");
    };

    const handleRegister = (e) => {
        e.preventDefault();
        setMessage("");
        
        AuthService.register(username, email, phone, name, password).then(
            (res) => {
                navigate("/login");
                window.location.reload();
            },
            (error) => setMessage("Ошибка сервера")
        );
    };

    return <div>
        <Header
          heading="Регистрация"
          paragraph="У вас уже есть аккаунт?"
          linkName="Войти"
          linkUrl="/login"/>
        <form className="mt-8 space-y-6">
          <input
            name="Имя"
            placeholder="Имя"
            onChange={onChangeName}
            value={name}
            className={fixedInputClass}/>
          <input
            name="Никнейм"
            placeholder="Никнейм"
            onChange={onChangeUsername}
            value={username}
            className={fixedInputClass}/>
          <input
            name="Почта"
            placeholder="Почта"
            onChange={onChangeEmail}
            value={email}
            className={fixedInputClass}/>
          <input
            name="Номер телефона"
            placeholder="Номер телефона"
            onChange={onChangePhone}
            value={phone}
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
            onClick={handleRegister}>
            Зарегестрироваться
        </button>
        {message && (
          <p className="mt-2 text-center text-sm text-gray-600 mt-5">
              {message}
          </p>
        )}
  </div>
}

export default Register;
