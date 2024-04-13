import axios from "axios";

const API_URL = "http://localhost:8080/api/users";

const register = (username, email, phone, password, birth, pfp, gender, topics) => {
  return axios.post(API_URL, {
    username,
    email,
    phone,
    password,
    birth,
    pfp,
    gender,
    topics
  });
};

const login = (username) => {
  return axios({
    url: API_URL + "/name/" + username,
    method: "GET"
  })
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  register,
  login,
  getCurrentUser
}

export default AuthService;