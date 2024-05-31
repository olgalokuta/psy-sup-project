import axios from "axios";
import AuthService from "../services/authService.js";

const API_URL = "http://localhost:8080/api/";

const getEntry = () => {
  const emploeeId = AuthService.getCurrentEmploee().id
  return axios.get(API_URL + "entries/formoderation/" + emploeeId.toString());
};

const getComments = () => {
  return axios.get(API_URL + "comments");
};

const getUserById = (id) => {
  return axios.get(API_URL + "users/" + id.toString());
};

const updateEntry = (entryId, entr) => {
  return axios.put(API_URL + "entries/" + entryId.toString(), entr);
};

const DataService = {
    getEntry,
    getComments,
    getUserById,
    updateEntry
}

export default DataService;