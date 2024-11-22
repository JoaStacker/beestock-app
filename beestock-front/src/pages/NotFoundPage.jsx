import React from 'react';
import { Box, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const NotFoundPage = () => {
    const navigate = useNavigate();

    const handleGoHome = () => {
        navigate('/login'); // Adjust to your desired route
    };

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
                textAlign: 'center'
            }}
        >
            <Typography variant="h4" gutterBottom>
                404 - Page Not Found
            </Typography>
            <Typography variant="subtitle1" gutterBottom>
                Sorry, the page you are looking for does not exist.
            </Typography>
            <Button variant="contained" onClick={handleGoHome}>
                Go to Home
            </Button>
        </Box>
    );
};

export default NotFoundPage;
