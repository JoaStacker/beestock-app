// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
import {
    createClient,
    getClients,
    getCondicionesTributarias,
    getOneClient,
    updateClient
} from '../../services/clientService';
import {
    Button,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    InputLabel, MenuItem, Select,
    TextField,
    Typography
} from "@mui/material";
import locales from "../../local/en";
import { useGlobalContext } from "../../context/GlobalContext";
import { HorizontalRule} from "@mui/icons-material";
import { getLocalidadesByProvinciaId, getPaises, getProvinciasByPaisId } from "../../services/locationService";
import { DateField, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import excludeVariablesFromRoot from "@mui/material/styles/excludeVariablesFromRoot";
import {createInteraction} from "../../services/interactionsService";

const CreateInteractionPopup = ({ clientId, onClose, onCreated }) => {
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

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validForm()) {
            updateGlobalState({
                openSnackbar: true,
                snackbarSeverity: "error",
                snackbarMessage: "Datos invalidos"
            });
            return;
        }

        updateGlobalState({ loadingPage: true });

        data.clienteId = clientId;
        data.empleadoId = globalState.user.empleado.id;

        await createInteraction(data)
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
                    snackbarMessage: locales.created
                });
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });

        onCreated();
        onClose();
    };

    const handleChange = (e, propName = null) => {
        let { name, value } = e.target;
        if (propName) {
            name = propName;
        }
        setData(prevData => ({ ...prevData, [name]: value }));
    };

    return (
        <>
            <DialogTitle>
                <Typography variant="h6" gutterBottom>
                    Nueva interacción
                </Typography>
                <HorizontalRule />
            </DialogTitle>
            <DialogContent>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateField
                        error={invalid.fechaInteraccion}
                        name="fechaInteraccion"
                        label="Fecha de interaccion"
                        value={dateFechaInteraccion}
                        onChange={(newValue) => {
                            console.log(newValue)
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

export default CreateInteractionPopup;
