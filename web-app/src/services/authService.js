import axios from "axios";

const API_URL = "http://localhost:8080/api/employees";
const role = "psychologist"

const register = (username, email, phone, name, password) => {
  return axios.post(API_URL, {
    username,
    email,
    phone,
    name,
    password,
    role
  });
};

const login = (username) => {
  return axios({
    url: API_URL + "/name/" + username,
    method: "GET"
  })
};

const getCurrentEmploee = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  register,
  login,
  getCurrentEmploee
}

export default AuthService;