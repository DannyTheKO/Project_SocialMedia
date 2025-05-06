import axios from '../axiosConfig.js';

const SHARED_POST_API_BASE_URL = '/shared-posts';

export const sharedPostApi = {
    // GET /api/v1/shared-posts/all
    getAllSharedPosts: () =>
        axios.get(`${SHARED_POST_API_BASE_URL}/all`),

    // GET /api/v1/shared-posts/all/user?userId={userId}
    getAllSharedPostsByUserId: (userId) =>
        axios.get(`${SHARED_POST_API_BASE_URL}/all/user?userId=${userId}`),

    // GET /api/v1/shared-posts/post/{sharedPostId}
    getSharedPostById: (sharedPostId) =>
        axios.get(`${SHARED_POST_API_BASE_URL}/post/${sharedPostId}`),

    // POST /api/v1/shared-posts/post/create
    createSharedPost: (sharedPostData) => {
        return axios.post(`${SHARED_POST_API_BASE_URL}/post/create`, sharedPostData, {
            headers: { 'Content-Type': 'application/json' }
        });
    },

    // PUT /api/v1/shared-posts/post/{sharedPostId}/update
    updateSharedPost: (sharedPostId, sharedPostData) => {
        return axios.put(`${SHARED_POST_API_BASE_URL}/post/${sharedPostId}/update`, sharedPostData, {
            headers: { 'Content-Type': 'application/json' }
        });
    },

    // DELETE /api/v1/shared-posts/post/delete?sharedPostId={sharedPostId}
    deleteSharedPost: (sharedPostId) => {
        return axios.delete(`${SHARED_POST_API_BASE_URL}/post/delete?sharedPostId=${sharedPostId}`);
    },
};