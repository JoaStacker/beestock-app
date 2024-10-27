// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
import { createClient, getClients, getOneClient, updateClient } from '../../services/clientService';
import { Button, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import locales from "../../local/en";
import { useGlobalContext } from "../../context/GlobalContext";
import {getOneInteraction, updateInteraction} from "../../services/interactionsService";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {DateField, LocalizationProvider} from "@mui/x-date-pickers";
import dayjs from "dayjs";

const EditClientPopup = ({ onClose, interactionId, onUpdated }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [dateFechaInteraccion, setDateFechaInteraccion] = useState(dayjs(''));

    // Fields and Validations
    const [data, setData] = useState({
        fechaInteraccion: '',
        medio: '',
    });
    const defaultInvalid = {
        fechaInteraccion: false,
        medio: false,
    }
    const [invalid, setInvalid] = useState(defaultInvalid);
    const validForm = () => {
        const newState = {
            fechaInteraccion: !data.fechaInteraccion,
            medio: !data.medio,
        };
        setInvalid(newState);
        return Object.values(newState).every(el => el === false);
    };


    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [data]);

    useEffect(() => {
        getOneInteraction(interactionId)
            .then((res) => {
                updateGlobalState({ loadingPage: false });

                if (res.error) {
                    updateGlobalState({
                        openSnackbar: true,
                        snackbarSeverity: "error",
                        snackbarMessage: res.message
                    });
                    return;
                }

                const data = res.data;
                setData(data);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    }, [interactionId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validForm()) return;

        updateGlobalState({ loadingPage: true });
        await updateInteraction(interactionId, data)
            .then((res) => {
                updateGlobalState({ loadingPage: false });

                if (res.error) {
                    updateGlobalState({
                        openSnackbar: true,
                        snackbarSeverity: "error",
                        snackbarMessage: res.message
                    });
                    return;
                }

                updateGlobalState({
                    openSnackbar: true,
                    snackbarSeverity: "success",
                    snackbarMessage: locales.updated
                });
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });

        onUpdated();
        onClose();
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setData(prevData => ({ ...prevData, [name]: value }));
    };

    return (
        <>
            <DialogTitle>{`Editar interacción`}</DialogTitle>
            <DialogContent>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateField
                        error={invalid.fechaInteraccion}
                        name="fechaInteraccion"
                        label="Fecha de interaccion"
                        value={dateFechaInteraccion}
                        onChange={(newValue) => {
                            let value = undefined
                            try {
                                value = newValue.toISOString()
                            }catch(err) {

                            }
                            setData({
                                ...data,
                                fechaInteraccion: value,
                            })
                            setDateFechaInteraccion(newValue)}
                        }
                    />
                </LocalizationProvider>
                <TextField
                    error={invalid.medio}
                    label="Medio de interacción"
                    required
                    margin="dense"
                    name="medio"
                    value={data.medio || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="primary">
                    Cancelar
                </Button>
                <Button onClick={handleSubmit} color="primary">
                    Guardar
                </Button>
            </DialogActions>
        </>
    );
};

export default EditClientPopup;
