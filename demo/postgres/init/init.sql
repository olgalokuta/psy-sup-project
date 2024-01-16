----------------------------------------------------------------------------
create table "User" (
	id SERIAL PRIMARY KEY,
	phone text UNIQUE,       -- номер телефона при регистрации
	email text,              -- почта при регистрации
	username text UNIQUE,           -- имя пользователя
	password text,
	pfp integer,                -- фото/аватарка
	gender boolean,             -- гендер
	birthdate date,          -- дата рождения, при регистрации
	topics integer[]         -- темы, которые интересны
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table Status (
	id SERIAL PRIMARY KEY,
	status bool
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table UserStatus (
	id SERIAL PRIMARY KEY,
	id_user integer REFERENCES "User",
	id_status integer REFERENCES Status,
	time_beg  timestamp,
	time_end  timestamp
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table Topic (
	id SERIAL PRIMARY KEY,
	topic text
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table Post (
	id SERIAL PRIMARY KEY,
	iduser integer REFERENCES "User", -- id автора поста
	posted datetime,                       -- опубликован?
	moderated bool,                    -- отмодерирован?
	"public" bool,                     -- публичный пост?
	topics integer[],                  -- под какие темы подходит
	"content" text                     -- текст поста 
)
----------------------------------------------------------------------------

----------------------------------------------------------------------------
create table "Comment" (
	id SERIAL PRIMARY KEY,
	id_post integer REFERENCES Post,   -- к какому посту относится
	id_user integer REFERENCES "User", -- id автора
	id_answer_comment integer,         -- id комментария, на который отвечают, если такой есть
	posted datetime,                       -- опубликован?
	сontent text,                      -- текст комментария
	moderated bool                     -- отмодерирован?
)
----------------------------------------------------------------------------

