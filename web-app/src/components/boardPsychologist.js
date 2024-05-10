import React from "react";
import AuthService from "../services/authService.js";


const BoardPsychologist = () => {
    const currentUser = AuthService.getCurrentUser();
  
    return (
      <div className="container">
        psycologist, hi!
      </div>
    );
};

export default BoardPsychologist;
