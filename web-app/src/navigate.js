import React from "react";
import { Routes, Route} from "react-router-dom";

import Login from "./components/login.js";
import Home from "./components/home.js";
import Profile from "./components/profile.js";
import Title from "./components/title.js";


export default function Navigate(){
    return <div className="container mt-3">
      <Title />
    <Routes>
      <Route exact path={"/"} element={<Home />} />
      <Route exact path={"/home"} element={<Home />} />
      <Route exact path={"/login"} element={<Login />} />
      <Route exact path={"/profile"} element={<Profile />} />
    </Routes>
  </div>
}
