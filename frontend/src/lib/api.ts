import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

export const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

import { mockApi } from './mockApi';

// Request interceptor
api.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor
api.interceptors.response.use(
    (response) => response,
    (error) => {
        const message = error.response?.data?.message || error.message || 'An error occurred';
        return Promise.reject({
            message,
            status: error.response?.status,
        });
    }
);

// OVERRIDE API WITH MOCK DATA FOR TESTING
const USE_MOCK = import.meta.env.VITE_USE_MOCK_API === 'true';

if (USE_MOCK) {
    console.log('⚠️ USING MOCK API');
    (api as any).get = mockApi.get;
    (api as any).post = mockApi.post;
    (api as any).patch = mockApi.patch;
    (api as any).delete = mockApi.delete;
}

export default api;
