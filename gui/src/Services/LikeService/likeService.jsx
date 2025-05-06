import axios from '../axiosConfig';

const LIKE_API_BASE_URL = '/likes';

export const likeApi = {
    // GET /api/v1/likes/post?postId={postId}
    getAllLikesByPostId: (postId) =>
        axios.get(`${LIKE_API_BASE_URL}/post?postId=${postId}`),

    // GET /api/v1/likes/comment?commentId={commentId}
    getAllLikesByCommentId: (commentId) =>
        axios.get(`${LIKE_API_BASE_URL}/comment?commentId=${commentId}`),

    getAllLikesBySharedPostId: (sharedPostId) =>
        axios.get(`${LIKE_API_BASE_URL}/shared-post?sharedPostId=${sharedPostId}`),

    // GET /api/v1/likes/count/post?postId={postId}
    getLikesCountByPostId: (postId) =>
        axios.get(`${LIKE_API_BASE_URL}/count/post?postId=${postId}`),

    // GET /api/v1/likes/count/comment?commentId={commentId}
    getLikesCountByCommentId: (commentId) =>
        axios.get(`${LIKE_API_BASE_URL}/count/comment?commentId=${commentId}`),

    getLikesCountBySharedPostId: (sharedPostId) =>
        axios.get(`${LIKE_API_BASE_URL}/count/shared-post?sharedPostId=${sharedPostId}`),

    // PUT /api/v1/likes/like?postId={postId} hoặc /likes/like?commentId={commentId}
    toggleLike: (postId, commentId, sharedPostId) => {
        const params = {};
        if (postId) params.postId = postId;
        if (commentId) params.commentId = commentId;
        if (sharedPostId) params.sharedPostId = sharedPostId;

        return axios.put(`${LIKE_API_BASE_URL}/like`, null, { params });
    },
};  