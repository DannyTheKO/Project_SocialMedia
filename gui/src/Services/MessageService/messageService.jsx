import axios from 'axios'

const REST_API_BASE_URL = '/api/v1/messages';

export const getMessagesData = (userId1, userId2) => {
    return axios.get(`${REST_API_BASE_URL}/all/${userId1}/${userId2}`)
}