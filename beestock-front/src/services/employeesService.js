// src/services/clientService.js
import axios from 'axios';

const API_URL = 'http://localhost:8000/empleados'; // Update with your API URL

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

export const getEmployees = async () => {
    return handleResponse(axiosInstance.get(`/`));
};

export const createEmployee = async (data) => {
    return handleResponse(axiosInstance.post('/', data));
};

export const getOneEmployee = async (id) => {
    return handleResponse(axiosInstance.get(`/${id}/`));
};

export const updateEmployee = async (id, data) => {
    return handleResponse(axiosInstance.put(`/${id}/`, data));
};

export const deleteEmployee = async (id) => {
    return handleResponse(axiosInstance.put(`/delete/${id}/`));
};