import axios from 'axios'

const REST_API_BASE_URL = '/api/v1/users';

export const getAllUsers = () => {
    return axios.get(`${REST_API_BASE_URL}/all`);
}

export const createUser = (userInfo) => axios.post(`${REST_API_BASE_URL}/create'`, userInfo);

export const getUser = (userID) => axios.get(`${REST_API_BASE_URL}/user/${userID}`);

export const updateUser = (userID, userInfo) => axios.put(`${REST_API_BASE_URL}/user/${userID}/update`, userInfo);

export const deleteUser = (userID) => axios.delete(REST_API_BASE_URL + `/user/${userID}/delete`);