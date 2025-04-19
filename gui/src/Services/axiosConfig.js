import axios from "axios";

// Create instance detail clarity
const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/v1",
    headers: {
        // Default Content-Type
        'Content-Type': 'application/json',
    },
    withCredentials: true, // turn on send cookie in request
});


// Interceptor to handle response data
axiosInstance.interceptors.response.use(
    response => response.data,
    async error => {
        const originalRequest = error.config;
        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            try {
                // call API refresh token (cookie refreshToken auto send)
                await axiosInstance.post('/auth/refresh');
                // try again first request 
                return axiosInstance(originalRequest);
            } catch (refreshError) {
                // failure refresh, navigate to login
                window.location.href = '/login';
                return Promise.reject(refreshError);
            }
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;