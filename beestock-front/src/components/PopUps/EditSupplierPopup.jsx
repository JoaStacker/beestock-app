// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
import { createClient, getClients, getOneClient, updateClient } from '../../services/clientService';
import { Button, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import locales from "../../local/en";
import { useGlobalContext } from "../../context/GlobalContext";
import {getOneSupplier, updateSupplier} from "../../services/supplierService";

const EditSupplierPopup = ({ onClose, supplierId, onUpdated }) => {
    const { globalState, updateGlobalState } = useGlobalContext();

    // Fields and Validations
    const [data, setData] = useState({
        nombre: '',
        correo: ''
    });
    const defaultInvalid = {
        nombre: false,
        correo: false
    }
    const [invalid, setInvalid] = useState(defaultInvalid);
    const validForm = () => {
        const newState = {
            nombre: !data.nombre,
            correo: !data.correo,
        };
        setInvalid(newState);
        return Object.values(newState).every(el => el === false);
    };


    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [data]);

    useEffect(() => {
        getOneSupplier(supplierId)
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

                const supplier = res.data;
                setData(supplier);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    }, [supplierId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validForm()) return;

        updateGlobalState({ loadingPage: true });
        let payload = {
            nombre: data.nombre,
            correo: data.correo
        }
        await updateSupplier(supplierId, payload)
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
            <DialogTitle>{`Editar proveedor`}</DialogTitle>
            <DialogContent>
                <TextField
                    error={invalid.nombre}
                    label="Nombre"
                    required
                    margin="dense"
                    name="nombre"
                    value={data.nombre || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                <TextField
                    error={invalid.correo}
                    label="Correo"
                    required
                    margin="dense"
                    name="correo"
                    value={data.correo || ''}
                    type="correo"
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

export default EditSupplierPopup;
