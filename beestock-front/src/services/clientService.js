// src/services/clientService.js
import axios from 'axios';

const API_URL = 'http://localhost:8000/clientes'; // Update with your API URL

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

export const getClients = async () => {
    return handleResponse(axiosInstance.get('/'));
};

export const getOneClient = async (clientId) => {
    return handleResponse(axiosInstance.get(`/${clientId}/`));
};

export const createClient = async (clientData) => {
    return handleResponse(axiosInstance.post('/', clientData));
};

export const updateClient = async (clientId, clientData) => {
    return handleResponse(axiosInstance.put(`/update/${clientId}/`, clientData));
};

export const deleteClient = async (clientId) => {
    return handleResponse(axiosInstance.delete(`/${clientId}/`));
};


export const getCondicionesTributarias = async () => {
    return handleResponse(axiosInstance.get('/condiciones-tributarias/'));
};

