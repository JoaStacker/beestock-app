import TopBar from "../components/TopBar";
import Sidebar from "../components/Sidebar";
import React from "react";
import {Box} from "@mui/material";

const BasePage = ({ children }) => {
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column'}}>
            <TopBar />
            <Box sx={{ display: 'flex', flexGrow: 1 }} >
                <Sidebar />
                { children }
            </Box>
        </Box>
    )
}

export default BasePage;
