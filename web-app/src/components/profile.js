import React from "react";
import { useNavigate } from "react-router-dom";

import AuthService from "../services/authService.js";

const Profile = () => {
  const currentUser = AuthService.getCurrentEmploee();

  const navigate = useNavigate();

  const moveToWork = (e) => {
    navigate("/" + currentUser.role);
    window.location.reload();
  };

  return (
    <div className="container">
      <p className="mt-3 mb-6 text-center text-xl font-semibold text-gray-900">
        Вы успешно вошли в систему!
      </p>
      <div className="container max-w-screen-2xl px-10 py-6 mx-auto rounded-lg shadow-sm dark:bg-gray-100">
        <h2 className="text-gray-1000 flex justify-center font-bold text-2xl mt-2">Модератор</h2>
        <div className="flex justify-center px-5 mt-3">
          <img className="h-32 w-32 p-2 rounded-full" src="https://source.unsplash.com/150x150/?portrait"/>
        </div>
        <div className="text-center px-14">
          <h2 className="text-gray-1000 text-3xl font-bold">{currentUser.username}</h2>
          <a className="text-gray-800 mt-2 hover:text-blue-500">{currentUser.email}</a>
          <h2 className="text-gray-800 mt-2">Номер телефона: {currentUser.phone}</h2>
          <h2 className="mt-2 text-gray-800 text-sm">Информация о сотруднике Информация о сотруднике Информация о сотруднике Информация о сотруднике</h2>
        </div>
      </div>
      <button
          type="button"
          className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 mt-10"
          onClick={moveToWork}>
        Начать работу
      </button>
    </div>
  );
};

export default Profile;