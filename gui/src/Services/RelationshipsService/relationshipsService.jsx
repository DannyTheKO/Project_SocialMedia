import axios from '../axiosConfig';

const RELATIONSHIPS_API_BASE_URL = '/relationships';

export const relationshipsApi = {
    // userId1 ( nguời gửi request được xác thực và lấy qua token )

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

    // PUT /api/v1/relationships/{relationshipId}/update
    updateRelationship: (relationshipId, status) =>
        axios.put(
            `${RELATIONSHIPS_API_BASE_URL}/${relationshipId}/update`,
            { status },
            { headers: { 'Content-Type': 'application/json' } }
        ),

    // DELETE /api/v1/relationships/{relationshipId}/delete
    deleteRelationship: (relationshipId) =>
        axios.delete(`${RELATIONSHIPS_API_BASE_URL}/${relationshipId}/delete`)
};