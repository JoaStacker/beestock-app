// GlobalContext.js
import React, { createContext, useContext, useState } from 'react';

const GlobalContext = createContext();

export const GlobalProvider = ({ children }) => {
    const [globalState, setGlobalState] = useState({
        // Initialize your global variables here
        user: JSON.parse(localStorage.getItem("user")),
        isAuthenticated: !!JSON.parse(localStorage.getItem("user")),
        theme: 'light',

        // Add more variables as needed
        loadingPage: false,

        openSnackbar: false,
        snackbarMessage: "",
        snackbarSeverity: "info",
    });

    const updateGlobalState = (newState) => {
        setGlobalState((prev) => ({ ...prev, ...newState }));
    };

    return (
        <GlobalContext.Provider value={{ globalState, updateGlobalState }}>
            {children}
        </GlobalContext.Provider>
    );
};

export const useGlobalContext = () => {
    return useContext(GlobalContext);
};
