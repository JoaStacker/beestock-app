import React from "react";
import {Box, Grid} from "@mui/material";

const HomePage = () => {
    return (
        <Box sx={{ flexGrow: 1, padding: 2 }}>
            <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
                <Grid item xs={2}>
                    <h2>Bienvenido</h2>
                </Grid>
            </Grid>
        </Box>
    )
}

export default HomePage;
