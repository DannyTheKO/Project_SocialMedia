import axios from '../axiosConfig';

export const timelineApi = {
    getTimeline: async () => {
        const response = await axios.get('/shared-posts/timeline');
        return response;
    },
};