import React, { useState, useEffect } from "react";

import DataService from "../services/dataService.js";

const Home = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    DataService.getEntries().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <div className="container">
      <header className="jumbotron">
        <h3>Количество записей: {content.length}</h3>
      </header>
    </div>
  );
};

export default Home;