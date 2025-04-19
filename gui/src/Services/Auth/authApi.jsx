import axiosInstance from '../axiosConfig';

const AUTH_API_BASE_URL = '/auth';

export const authApi = {
    login: (username, password) =>
        axiosInstance.post(`${AUTH_API_BASE_URL}/login`, { username, password }),

    register: (username, firstname, lastname, email, password, birthday) =>
        axiosInstance.post(`${AUTH_API_BASE_URL}/register`, {
            username,
            firstname,
            lastname,
            email,
            password,
            birthday
        }),

    whoAmI: () =>
        axiosInstance.get(`${AUTH_API_BASE_URL}/whoAmI`),

    logout: () =>
        axiosInstance.post(`${AUTH_API_BASE_URL}/logout`), // TODO: build logout controller
};