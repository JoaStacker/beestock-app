// src/services/clientService.js
import axios from 'axios';

const API_URL = 'http://localhost/clientes'; // Update with your API URL

export const getClients = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const createClient = async (clientData) => {
    const response = await axios.post(API_URL, clientData);
    return response.data;
};

export const updateClient = async (clientId, clientData) => {
    const response = await axios.put(`${API_URL}/${clientId}`, clientData);
    return response.data;
};

export const deleteClient = async (clientId) => {
    const response = await axios.delete(`${API_URL}/${clientId}`);
    return response.data;
};
