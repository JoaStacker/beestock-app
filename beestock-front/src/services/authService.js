// src/services/clientService.js
import axios from 'axios';

const API_URL = 'http://localhost:8000/auth'; // Update with your API URL

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
const handleResponse = async (request, updateGlobalState=null) => {
  try {
        const response = await request;
        const data = await JSON.parse(response.data)
        console.log('Response:', data);

        if(data.status === 'OK'){
            return {
                data: data.data,
                error: false,
                message: data.message || "OK"
            };
        }else{
            console.log(data)
            if(updateGlobalState){
                updateGlobalState({
                    openSnackbar: true,
                    snackbarSeverity: "error",
                    snackbarMessage: data.message
                  });
            }
            return {
                data: data.data,
                error: true,
                message: data.message
            };
        }

    } catch (axiosError) {
        console.error('API request error:', axiosError);
        let data = JSON.parse(axiosError.response.data);
        console.log(data);
        return {
            data: null,
            error: true,
            message: data.message,
        };
    }
};

export const loginUser = async (data, updateGlobalState) => {
    return handleResponse(axiosInstance.post('/login', data), updateGlobalState);
};

export const signupUser = async (data) => {
    return handleResponse(axiosInstance.post(`/signup`, data));
};

