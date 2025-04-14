import axios from '../axiosConfig.js';

const POST_API_BASE_URL = '/posts';

/*
* Dev note:
*
* anh fix lại cái response handle lại của em á,
* trước đó anh có thay đổi cái URL request API của back-end nha...
* */

export const postApi = {
    // GET /api/v1/posts/all
    getAllPosts: () =>
        axios.get(`${POST_API_BASE_URL}/all`),

    // GET /api/v1/posts/all/user?userId={userId}
    getAllPostsByUserId: (userId) =>
        axios.get(`${POST_API_BASE_URL}/all/user?userId=${userId}`),

    // GET /api/v1/posts/post/{postId}
    getPostById: (postId) =>
        axios.get(`${POST_API_BASE_URL}/post/${postId}`),

    // POST /api/v1/posts/post/create
    createPost: (postData) => {
        const formData = new FormData();
        Object.keys(postData).forEach(key => {
            formData.append(key, postData[key]);
        });
        return axios.post(`${POST_API_BASE_URL}/post/create`, formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        });
    },

    // PUT /api/v1/posts/post/{postId}/update
    updatePost: (userId, postId, postData) => {
        const formData = new FormData();
        Object.keys(postData).forEach(key => {
            formData.append(key, postData[key]);
        });
        return axios.put(`${POST_API_BASE_URL}/post/${postId}/update?userId=${userId}`, formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        });
    },

    // DELETE /api/v1/posts/post/delete?postId={postId}
    deletePost: (postId) =>
        axios.delete(`${POST_API_BASE_URL}/post/delete?postId=${postId}`)
};