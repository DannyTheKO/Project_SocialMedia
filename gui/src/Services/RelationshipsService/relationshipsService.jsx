import axios from '../axiosConfig';

const RELATIONSHIPS_API_BASE_URL = '/relationships';

export const relationshipsApi = {
    // GET /api/v1/relationships/friends
    getFriends: () =>
        axios.get(`${RELATIONSHIPS_API_BASE_URL}/friends`),

    // POST /api/v1/relationships/create
    createRelationship: (userId2, status) =>
        axios.post(
            `${RELATIONSHIPS_API_BASE_URL}/create`,
            { userId2, status },
            { headers: { 'Content-Type': 'application/json' } }
        ),

    // DELETE /api/v1/relationships/{relationshipId}/delete
    deleteRelationship: (relationshipId) =>
        axios.delete(`${RELATIONSHIPS_API_BASE_URL}/${relationshipId}/delete`),

    checkFriendShip: (userId2) =>
        axios.get(`${RELATIONSHIPS_API_BASE_URL}/check-friendship?userId=${userId2}`),

    findRelationshipId: (userId2) =>
        axios.get(`${RELATIONSHIPS_API_BASE_URL}/find-relationship-id?userId2=${userId2}`),

    findPendingRelationshipId: (userId2) =>
        axios.get(`${RELATIONSHIPS_API_BASE_URL}/find-pending-relationship-id?userId2=${userId2}`),

    acceptFriendRequest: (relationshipId) =>
        axios.post(`${RELATIONSHIPS_API_BASE_URL}/accept?relationshipId=${relationshipId}`),

    rejectFriendRequest: (relationshipId) =>
        axios.post(`${RELATIONSHIPS_API_BASE_URL}/reject?relationshipId=${relationshipId}`),

    blockUser: (userId2) =>
        axios.post(
            `${RELATIONSHIPS_API_BASE_URL}/block`,
            { userId2 },
            { headers: { 'Content-Type': 'application/json' } }
        ),

    getPendingRequests: (page = 0, size = 10) =>
        axios.get(`${RELATIONSHIPS_API_BASE_URL}/pending-requests?page=${page}&size=${size}`),
};