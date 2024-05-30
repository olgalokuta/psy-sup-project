import React, { useState, useEffect } from "react";

import DataService from "../services/dataService.js";
import AuthService from "../services/authService.js";

import Entry from "./entry.js";

const BoardModerator = () => {
    const currentUser = AuthService.getCurrentEmploee();

    const [message, setMessage] = useState("");
    const [entr, setEntr] = useState(null);
    const [user, setUser] = useState(currentUser);
    const [gotEntry, setGotEntry] = useState(false);
    const [gotAuthor, setGotAuthor] = useState(false);

    const getEnrtyForModeration = (e) => 
      DataService.getEntry().then(
        (res) => {
          setEntr(res.data);
          setGotEntry(true);
          setGotAuthor(false);
        },
        (error) => {
          setMessage("Ошибка сервера");
          setGotEntry(false);
        }
      );

    const getUser = (id) => {
      DataService.getUserById(id).then(
          (res) => {
            setUser(res.data);
            setGotAuthor(true);
          },
          (error) => {
            setMessage("Ошибка сервера");
            setGotAuthor(false);
          }
      );
    }

    const updateEntry = (e) => {
      const entry = entr;
      const moderated = {
        id: entry.id,
        iduser: entry.iduser,
        content: entry.content,
        posted: entry.posted,
        moderated: true,
        moderator: entry.moderator,
        visibility: "private",
        topics: entry.topics
      }
      setGotEntry(false);
      loadEntry(e);
      return DataService.updateEntry(entry.id, moderated);
    }

    useEffect(() => {
      if (gotEntry) {
        if (entr)
          getUser(entr.iduser);
        else
          setMessage("Нет постов для модерации");
      }
    }, [gotEntry]);

    //1) load all posts
    //2) if we have any post
    //3) render the first one
    //4) if button pressed
    //5) get new post and render it

    const loadEntry = (e) => {
        e.preventDefault();
        setMessage("");
        setGotEntry(false);
        getEnrtyForModeration(e);
    };

    return (
      <div className="container mt-8 text-base">
        Для модерации удостоверьтесь, что запись соответствует правилам безопасной среды,
        если это не так, отклоните ее.
        {message && (
          <p className="mt-2 text-center text-sm text-gray-600 mt-5">
              {message}
          </p>
        )}
        { gotAuthor && <Entry nickname={user.username} date={entr.posted} content={entr.content}/>}
        { gotEntry && (!message) ? (
          <div>
            <div className="flex items-center justify-between mt-6">
              <button
                  type="button"
                  className="w-32 px-4 py-1 font-bold rounded dark:bg-violet-600 dark:text-gray-50"
                  onClick={loadEntry}>
                Отклонить
              </button>
              <button
                type="button"
                className="w-32 px-4 py-1 font-bold rounded dark:bg-violet-600 dark:text-gray-50"
                onClick={updateEntry}>
              Принять
              </button>
            </div>
          </div>
          ) : (
            <button
              type="button"
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 mt-10"
              onClick={loadEntry}>
              Загрузить запись
            </button>
        )}
      </div>
    );
};

export default BoardModerator;
