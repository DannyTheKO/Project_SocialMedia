import axios from '../axiosConfig.js'

const COMMENT_API_BASE_URL = '/comments';
/*
* Dev note:
*
* anh fix lại cái response handle lại của em á,
* trước đó anh có thay đổi cái URL request API của back-end nha...
* */

export const commentApi = {
    // GET /api/v1/comments/all
    getAllComments: () =>
        axios.get(`${COMMENT_API_BASE_URL}/all`),

    // GET /api/v1/comments/all/post?postId={postId}
    getPostComments: (postId) =>
        axios.get(`${COMMENT_API_BASE_URL}/all/post?postId=${postId}`),

    // POST /api/v1/comments/create?userId={userId}&postId={postId}
    createComment: (userId, postId, commentData) => {
        const formData = new FormData();
        Object.keys(commentData).forEach(key => {
            formData.append(key, commentData[key]);
        });
        return axios.post(
            `${COMMENT_API_BASE_URL}/create?userId=${userId}&postId=${postId}`,
            formData,
            {
                headers: { 'Content-Type': 'multipart/form-data' }
            }
        );
    },

    // PUT /api/v1/comments/comment/{commentId}/update
    updateComment: (commentId, commentData) => {
        const formData = new FormData();
        Object.keys(commentData).forEach(key => {
            formData.append(key, commentData[key]);
        });
        return axios.put(
            `${COMMENT_API_BASE_URL}/comment/${commentId}/update`,
            formData,
            {
                headers: { 'Content-Type': 'multipart/form-data' }
            }
        );
    },

    // DELETE /api/v1/comments/user/{commentId}/delete
    deleteComment: (commentId) =>
        axios.delete(`${COMMENT_API_BASE_URL}/user/${commentId}/delete`)
};


export const getPostComment = (postID) => {
    return axios.get(`${COMMENT_API_BASE_URL}/all/post?postId=${postID}`);
}

export const createComment = (userId, postId, postInfo) => axios.post(`${COMMENT_API_BASE_URL}/create?userId=${userId}&postId=${postId}'`, postInfo);

export const updateComment = (commentID, commentInfo) => axios.put(`${COMMENT_API_BASE_URL}/comment/${commentID}/update`, commentInfo);

// ẩn comment đối với userID
export const hideComment = (commentID, userID) => axios.delete(COMMENT_API_BASE_URL + `/user/${commentID}/delete`);