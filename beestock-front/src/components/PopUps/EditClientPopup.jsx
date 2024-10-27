// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
import { createClient, getClients, getOneClient, updateClient } from '../../services/clientService';
import { Button, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import locales from "../../local/en";
import { useGlobalContext } from "../../context/GlobalContext";

const EditClientPopup = ({ onClose, clientId, onClientUpdated }) => {
    const { globalState, updateGlobalState } = useGlobalContext();

    // Fields and Validations
    const [client, setClient] = useState({
        nombre: '',
        apellido: '',
        email: ''
    });
    const defaultInvalid = {
        nombre: false,
        apellido: false,
        email: false
    }
    const [invalid, setInvalid] = useState(defaultInvalid);
    const validForm = () => {
        const newState = {
            nombre: !client.nombre,
            apellido: !client.apellido,
            email: !client.email,
        };
        setInvalid(newState);
        return Object.values(newState).every(el => el === false);
    };


    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [client]);

    useEffect(() => {
        getOneClient(clientId)
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

                const cliente = res.data;
                setClient(cliente);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    }, [clientId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validForm()) return;

        updateGlobalState({ loadingPage: true });
        await updateClient(clientId, client)
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
                    snackbarMessage: locales.updatedCliente
                });
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });

        onClientUpdated();
        onClose();
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setClient(prevClient => ({ ...prevClient, [name]: value }));
    };

    return (
        <>
            <DialogTitle>{`Editar cliente`}</DialogTitle>
            <DialogContent>
                <TextField
                    error={invalid.nombre}
                    label="Nombre"
                    required
                    margin="dense"
                    name="nombre"
                    value={client.nombre || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                <TextField
                    error={invalid.apellido}
                    label="Apellido"
                    required
                    margin="dense"
                    name="apellido"
                    value={client.apellido || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                <TextField
                    error={invalid.email}
                    label="Email"
                    required
                    margin="dense"
                    name="email"
                    value={client.email || ''}
                    type="email"
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
