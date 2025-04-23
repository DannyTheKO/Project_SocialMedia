import axios from '../axiosConfig.js'

const USER_API_BASE_URL = '/users';

/*
* Dev note:
*
* anh fix lại cái response handle lại của em á,
* trước đó anh có thay đổi cái URL request API của back-end nha...
* */

// Handle Response
export const userApi = {
    // GET /api/v1/users/all
    getAllUsers: () => axios.get(`${USER_API_BASE_URL}/all`),

    // GET /api/v1/users/user/{userId}
    getUserById: (userId) => axios.get(`${USER_API_BASE_URL}/user/${userId}`),

    // POST /api/v1/users/create
    createUser: (userData) => axios.post(`${USER_API_BASE_URL}/create`, userData),

    // PUT /api/v1/users/user/update
    updateUser: (userData) => {
        // This can cause error
        // const formData = new FormData();
        // Object.keys(userData).forEach(key => {
        //     formData.append(key, userData[key]);
        // });

        return axios.put(`${USER_API_BASE_URL}/user/update`, userData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        });
    },

    // DELETE /api/v1/users/user/delete?userId={userId}
    deleteUser: (userId) => axios.delete(`${USER_API_BASE_URL}/user/delete?userId=${userId}`)
};