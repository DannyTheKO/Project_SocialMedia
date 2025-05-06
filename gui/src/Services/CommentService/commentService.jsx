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

    // GET /api/v1/comments/all/shared-post?sharedPostId={sharedPostId}
    getSharedPostComments: (sharedPostId) =>
        axios.get(`${COMMENT_API_BASE_URL}/all/shared-post?sharedPostId=${sharedPostId}`),

    // POST /api/v1/comments/create?userId={userId}&postId={postId}
    createComment: (postId, sharedPostId, commentData) => {
        let formData;
        if (commentData instanceof FormData) {
            formData = commentData;
        } else {
            formData = new FormData();
            Object.keys(commentData).forEach(key => {
                if (Array.isArray(commentData[key])) {
                    commentData[key].forEach(item => {
                        formData.append(key, item);
                    });
                } else {
                    formData.append(key, commentData[key]);
                }
            });
        }

        let url = `${COMMENT_API_BASE_URL}/create`;
        if (postId) {
            url += `?postId=${postId}`;
        } else if (sharedPostId) {
            url += `?sharedPostId=${sharedPostId}`;
        } else {
            throw new Error("Either postId or sharedPostId must be provided");
        }

        return axios.post(url, formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        });
    },

    /// PUT /api/v1/comments/comment/{commentId}/update?postId={postId} hoặc ?sharedPostId={sharedPostId}
    updateComment: (commentId, postId, sharedPostId, commentData) => {
        let url = `${COMMENT_API_BASE_URL}/comment/${commentId}/update`;
        if (postId) {
            url += `?postId=${postId}`;
        } else if (sharedPostId) {
            url += `?sharedPostId=${sharedPostId}`;
        } else {
            throw new Error("Either postId or sharedPostId must be provided");
        }

        let formData;
        if (commentData instanceof FormData) {
            formData = commentData;
        } else {
            formData = new FormData();
            Object.keys(commentData).forEach(key => {
                if (Array.isArray(commentData[key])) {
                    commentData[key].forEach(item => {
                        formData.append(key, item);
                    });
                } else {
                    formData.append(key, commentData[key]);
                }
            });
        }

        return axios.put(url, formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        });
    },

    // DELETE /api/v1/comments/user/{commentId}/delete
    deleteComment: (commentId) =>
        axios.delete(`${COMMENT_API_BASE_URL}/comment/delete?commentId=${commentId}`),
};
