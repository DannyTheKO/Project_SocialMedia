import axios from 'axios';

const REST_API_BASE_URL = '/api/v1/posts';

export const getAllPosts = () => {
    return axios.get(`${REST_API_BASE_URL}/all`);
};

export const getUserPosts = (userID) => {
    return axios.get(`${REST_API_BASE_URL}/user/${userID}/posts`); // cần làm thêm ở backend
};

export const createPost = (userID, postInfo) =>
    axios.post(`${REST_API_BASE_URL}/user/${userID}/create`, postInfo);

export const getPost = (postID) =>
    axios.get(`${REST_API_BASE_URL}/post/${postID}`);

export const updatePost = (postID, postInfo) =>
    axios.put(`${REST_API_BASE_URL}/post/${postID}/update`, postInfo);

export const deletePost = (postID) =>
    axios.delete(`${REST_API_BASE_URL}/post/${postID}/delete`);