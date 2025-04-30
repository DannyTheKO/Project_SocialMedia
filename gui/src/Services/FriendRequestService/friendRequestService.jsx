import axios from '../axiosConfig'

const FRIEND_REQUEST_API_BASE_URL = '/friend-requests';

export const friendRequestApi = {

    // GET /api/v1/friend-requests/received
    getReceivedFriendRequests: (page = 0, size = 10) =>
        axios.get(`${FRIEND_REQUEST_API_BASE_URL}/received?page=${page}&size=${size}`),

    // POST /api/v1/friend-requests/create
    createFriendRequest: (toUserId) =>
        axios.post(
            `${FRIEND_REQUEST_API_BASE_URL}/create`,
            { toUserId },
            { headers: { 'Content-Type': 'application/json' } }
        ),

    // PUT /api/v1/friend-requests/{friendRequestId}/update
    updateFriendRequest: (friendRequestId, status) =>
        axios.put(
            `${FRIEND_REQUEST_API_BASE_URL}/${friendRequestId}/update`,
            { status },
            { headers: { 'Content-Type': 'application/json' } }
        ),

    // DELETE /api/v1/friend-requests/{friendRequestId}/delete
    deleteFriendRequest: (friendRequestId) =>
        axios.delete(`${FRIEND_REQUEST_API_BASE_URL}/${friendRequestId}/delete`)
};