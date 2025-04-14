import axios from "axios";

// Create instance detail clarity
const axiosInstance = axios.create({
    baseURL: "http://localhost:3000/api/v1",
    headers: {
        // Default Content-Type, change if necessary
        'Content-Type': 'application/json',
    }
});

// Interceptor to handle response data
axiosInstance.interceptors.response.use(
    response => response.data,
    error => Promise.reject(error)
);

export default axiosInstance;