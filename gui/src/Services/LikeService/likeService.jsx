import axios from '../axiosConfig';

const LIKE_API_BASE_URL = '/likes';

export const likeApi = {
    // GET /api/v1/likes/post?postId={postId}
    getAllLikesByPostId: (postId) =>
        axios.get(`${LIKE_API_BASE_URL}/post?postId=${postId}`),

    // GET /api/v1/likes/comment?commentId={commentId}
    getAllLikesByCommentId: (commentId) =>
        axios.get(`${LIKE_API_BASE_URL}/comment?commentId=${commentId}`),

    // GET /api/v1/likes/count/post?postId={postId}
    getLikesCountByPostId: (postId) =>
        axios.get(`${LIKE_API_BASE_URL}/count/post?postId=${postId}`),

    // GET /api/v1/likes/count/comment?commentId={commentId}
    getLikesCountByCommentId: (commentId) =>
        axios.get(`${LIKE_API_BASE_URL}/count/comment?commentId=${commentId}`),

    // PUT /api/v1/likes/like?postId={postId} hoặc /likes/like?commentId={commentId}
    toggleLike: (postId, commentId) => {
        const params = {};
        if (postId) params.postId = postId;
        if (commentId) params.commentId = commentId;
        return axios.put(`${LIKE_API_BASE_URL}/like`, null, { params });
    }
};