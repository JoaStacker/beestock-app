// src/services/clientService.js
import axios from 'axios';

const API_URL = 'http://localhost:8000/interacciones'; // Update with your API URL

// Create an Axios instance with default settings
const axiosInstance = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    },
    transformResponse: [data => data]
});

// Handle API requests
const handleResponse = async (request) => {
    try {
        const response = await request;
        const data = await JSON.parse(response.data)
        console.log('Response:', data);

        if(data.statusCodeValue >= 200 && data.statusCodeValue < 300){
            return {
                data: data.body.data,
                error: false,
                message: data.body.message || "OK"
            };
        }else{
            return {
                data: data.body.data,
                error: true,
                message: data.body.message
            };
        }

    } catch (axiosError) {
        console.error('API request error:', axiosError);
        return {
            data: null,
            error: true,
            message: axiosError.message
        };
    }
};

export const getInteractionsByClient = async (id) => {
    return handleResponse(axiosInstance.get(`/cliente/${id}/`));
};

export const getOneInteraction = async (id) => {
    return handleResponse(axiosInstance.get(`/${id}/`));
};

export const createInteraction = async (data) => {
    return handleResponse(axiosInstance.post('/', data));
};

export const updateInteraction = async (id, data) => {
    return handleResponse(axiosInstance.put(`/${id}/`, data));
};

export const deleteInteraction = async (id) => {
    return handleResponse(axiosInstance.put(`/delete/${id}/`));
};
