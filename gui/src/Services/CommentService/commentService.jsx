import axios from 'axios'

const REST_API_BASE_URL = '/api/v1/comments';

// các URL API có thể thay đổi sau

export const getPostComment = (postID) => {
    return axios.get(`${REST_API_BASE_URL}/all/post?postId=${postID}`);
}

export const createComment = (userId, postId, postInfo) => axios.post(`${REST_API_BASE_URL}/create?userId=${userId}&postId=${postId}'`, postInfo);

export const updateComment = (commentID, commentInfo) => axios.put(`${REST_API_BASE_URL}/comment/${commentID}/update`, commentInfo);

// ẩn comment đối với userID
export const hideComment = (commentID, userID) => axios.delete(REST_API_BASE_URL + `/user/${commentID}/delete`);