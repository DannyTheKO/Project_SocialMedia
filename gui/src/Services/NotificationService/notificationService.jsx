import axios from '../axiosConfig.js'

const NOTIFICATION_API_BASE_URL = '/notifications';

export const notificationApi = {
    getUserNotification: async () =>
        axios.get(`${NOTIFICATION_API_BASE_URL}/user`)
            .catch(err => console.log(err)),

    countUnreadNotifications: async () =>
        axios.get(`${NOTIFICATION_API_BASE_URL}/user/count`)
            .catch(err => console.log(err)),

    markAsRead: (notificationId) =>
        axios.put(`${NOTIFICATION_API_BASE_URL}/${notificationId}/read`)
            .catch(err => console.log(err)),

    markAllAsRead: () =>
        axios.put(`${NOTIFICATION_API_BASE_URL}/readAll`)
            .catch(err => console.log(err)),

    deleteNotification: (notificationId) =>
        axios.delete(`${NOTIFICATION_API_BASE_URL}/${notificationId}/delete`)
            .catch(err => console.log(err)),

    deleteAllNotifications: () =>
        axios.delete(`${NOTIFICATION_API_BASE_URL}/deleteAll`)
            .catch(err => console.log(err)),
}