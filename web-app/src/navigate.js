import React from "react";
import { Routes, Route} from "react-router-dom";

import Login from "./components/login.js";
import Home from "./components/home.js";
import Profile from "./components/profile.js";
import Title from "./components/title.js";
import BoardModerator from "./components/boardModerator.js";
import BoardPsycologist from "./components/boardPsychologist.js";


export default function Navigate(){
    return <div className="container mt-3">
      <Title />
    <Routes>
      <Route exact path={"/"} element={<Login />} />
      <Route exact path={"/home"} element={<Home />} />
      <Route exact path={"/profile"} element={<Profile />} />
      <Route exact path={"/moderator"} element={<BoardModerator />} />
      <Route exact path={"/psycologist"} element={<BoardPsycologist />} />
    </Routes>
  </div>
}
