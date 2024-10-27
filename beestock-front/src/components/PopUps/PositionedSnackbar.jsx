import * as React from 'react';
import Box from '@mui/material/Box';
import Snackbar from '@mui/material/Snackbar';
import {useState} from "react";
import {useGlobalContext} from "../../context/GlobalContext";
import Alert from '@mui/material/Alert';

export default function PositionedSnackbar() {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [state, setState] = useState({
        vertical: 'top',
        horizontal: 'right',
    });
    const { vertical, horizontal} = state;

    const handleClose = () => {
        updateGlobalState({openSnackbar: false})
    };

    return (
        <Box sx={{ width: 500 }}>
            <Snackbar
                anchorOrigin={{ vertical, horizontal }}
                open={globalState.openSnackbar}
                onClose={handleClose}
                key={vertical + horizontal}
            >
                <Alert severity={globalState.snackbarSeverity}>
                    {globalState.snackbarMessage}
                </Alert>
            </Snackbar>
        </Box>
    );
}