import React, { useState, useEffect } from "react";

import DataService from "../services/dataService.js";
import AuthService from "../services/authService.js";

import Entry from "./entry.js";


const BoardModerator = () => {
    const currentUser = AuthService.getCurrentUser();

    const [message, setMessage] = useState("");
    const [allEntriesList, setAllEntriesList] = useState([]);
    const [gotAllEntries, setGotAllEntries] = useState(false);
    const [entr, setEntr] = useState(null);

    const [index, setIndex] = useState(0);
    const [user, setUser] = useState(currentUser);
    const [gotAuthor, setGotAuthor] = useState(false);

    const getAllEntries = (e) => DataService.getEntries().then(
        (res) => {
          setAllEntriesList(res.data);
          setGotAllEntries(true);
        },
        (error) => []
    );

    const getUser = (id) => {
        setMessage("");
        DataService.getUserById(id).then(
            (res) => {
              setUser(res.data);
              setEntr(allEntriesList[index]);
              setGotAuthor(true);
            },
            (error) => setMessage("Ошибка сервера")
        );
    }

    const updateEntry = (e) => {
      const entry = allEntriesList[index];
      const moderated = {
        id: entry.id,
        iduser: entry.iduser,
        content: entry.content,
        posted: entry.posted,
        moderated: true,
        public: entry.public,
        topics: entry.topics
      }
      showOne();
      return DataService.updateEntry(entry.id, moderated);
    }

    useEffect(() => {
      if (allEntriesList.length)
        getUser(allEntriesList[0].iduser);
    }, [gotAllEntries]);

    //1) load all posts
    //2) if we have any post
    //3) render the first one
    //4) if button pressed
    //5) get new post and render it

    const loadEntries = (e) => {
        e.preventDefault();
        getAllEntries(e);
    };

    const showOne = () => {
      setIndex(index + 1);
      console.log(index);

      if (allEntriesList.length && index < allEntriesList.length) {
        console.log(index);
        console.log(allEntriesList);
        getUser(allEntriesList[index].iduser);
      }
      else {
        setMessage("Нет постов для модерации");
        //setGotAllEntries(false);
      }
    }

    return (
      <div className="container mt-8 text-base">
        Для модерации удостоверьтесь, что запись соответствует правилам безопасной среды,
        если это не так, отклоните ее.
        {message && (
          <p className="mt-2 text-center text-sm text-gray-600 mt-5">
              {message}
          </p>
        )}
        { gotAllEntries && gotAuthor && (!message) && <Entry nickname={user.username} date={entr.posted} content={entr.content} /> }
        { allEntriesList.length && (!message) ? (
            <div className="flex items-center justify-between mt-6">
              <button
                type="button"
                className="w-32 px-4 py-1 font-bold rounded dark:bg-violet-600 dark:text-gray-50"
                onClick={updateEntry}>
              Принять
              </button>
              <button
                  type="button"
                  className="w-32 px-4 py-1 font-bold rounded dark:bg-violet-600 dark:text-gray-50"
                  onClick={showOne}>
                Отклонить
              </button>
            </div>
          ) : (
            <button
              type="button"
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 mt-10"
              onClick={loadEntries}>
              Загрузить запись
            </button>
        )}
      </div>
    );
};

export default BoardModerator;
