import React from "react";

import "./App.css";
import Navigate from "./navigate.js";

const App = () => {
    return <div className="min-h-full h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div className="max-w-md w-full space-y-8">
     <Navigate />
    </div>
  </div>
}

export default App;