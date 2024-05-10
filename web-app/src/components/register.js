import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import AuthService from "../services/authService.js";
import Header from "./header.js";


const Register = () => {
    const fixedInputClass="rounded-md appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
    const emailPattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
    const phonePattern = /^\d{11}$/;

    const [username, setUsername] = useState("");
    const [name, setName] = useState("");
    const [phone, setPhone] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const [validPhone, setValidPhone] = useState(false);
    const [validEmail, setValidEmail] = useState(false);

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
        setValidEmail(emailPattern.test(email));
        setMessage("");
    };

    const onChangePhone = (e) => {
        const phone = e.target.value;
        setPhone(phone);
        setValidPhone(phonePattern.test(phone));
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
        if (username === "" || password === ""){
          setMessage("Одно или несколько полей не заполнены");
          return;
        }

        if(!validEmail || !validPhone) {
          setMessage("Неправильное значние в поле для почты или телефона");
          return;
        }

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
            id="name-input"
            name="Имя"
            type="text"
            onChange={onChangeName}
            placeholder="Имя"
            value={name}
            className={fixedInputClass}/>
          <label
            for="name-input"
            class="pointer-events-none absolute left-3 top-0 mb-0 max-w-[90%] origin-[0_0] truncate pt-[0.37rem] leading-[2.15] text-neutral-500 transition-all duration-200 ease-out peer-focus:-translate-y-[1.15rem] peer-focus:scale-[0.8] peer-focus:text-primary peer-data-[twe-input-state-active]:-translate-y-[1.15rem] peer-data-[twe-input-state-active]:scale-[0.8] motion-reduce:transition-none dark:text-neutral-400 dark:peer-focus:text-primary"
            >Имя
          </label>
          <input
            name="Никнейм"
            placeholder="Никнейм"
            onChange={onChangeUsername}
            value={username}
            className={fixedInputClass}/>
          <input
            name="Почта"
            type="email"
            placeholder="Почта"
            onChange={onChangeEmail}
            value={email}
            className={fixedInputClass}/>
          <input
            type="tel"
            name="Номер телефона"
            onChange={onChangePhone}
            value={phone}
            className={fixedInputClass}
            placeholder="123"
            pattern="[0-9]{3}"
            required/>
          <input
            type="password"
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
