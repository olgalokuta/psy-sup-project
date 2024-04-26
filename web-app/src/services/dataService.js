import axios from "axios";

const API_URL = "http://localhost:8080/api/";

const getEntries = () => {
  return axios.get(API_URL + "entries/formoderation");
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
    getEntries,
    getComments,
    getUserById,
    updateEntry
}

export default DataService;