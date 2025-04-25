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
        // If commentData is already FormData, use it directly
        let formData;
        if (commentData instanceof FormData) {
            formData = commentData;
        } else {
            // Otherwise create a new FormData and populate it
            formData = new FormData();
            Object.keys(commentData).forEach(key => {
                // Handle array values (like multiple files)
                if (Array.isArray(commentData[key])) {
                    commentData[key].forEach(item => {
                        formData.append(key, item);
                    });
                } else {
                    formData.append(key, commentData[key]);
                }
            });
        }

        // Log what's being sent (for debugging)
        console.log("Sending to API:");
        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + (pair[0] === 'media' ? '[File]' : pair[1]));
        }

        return axios.post(
            `${COMMENT_API_BASE_URL}/create?postId=${postId}`,
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
