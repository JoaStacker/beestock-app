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

const EditClientPopup = ({ onClose, onClientCreated }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [dateFechaNacimiento, setDateFechaNacimiento] = useState(dayjs(''));

    // Fields and Validations
    const [client, setClient] = useState({
        cuit: '',
        nombre: '',
        apellido: '',
        email: '',
        calle: '',
        numero: '',
        piso: '',
        fechaNacimiento: '',
        localidadId: '',
        condicionTributariaId: ''

    });
    const defaultInvalid = {
        cuit: false,
        nombre: false,
        apellido: false,
        email: false,
        calle: false,
        numero: false,
        piso: false,
        fechaNacimiento: false,
        localidadId: false,
        condicionTributariaId: false
    }
    const [invalid, setInvalid] = useState(defaultInvalid);

    const validForm = () => {
        const newState = {
            cuit: !client.cuit,
            nombre: !client.nombre,
            apellido: !client.apellido,
            email: !client.email,
            calle: !client.calle,
            numero: !client.numero,
            piso: !client.piso,
            fechaNacimiento: !client.fechaNacimiento,
            localidadId: !client.localidadId,
            condicionTributariaId: !client.condicionTributariaId,
        };
        setInvalid(newState);
        return Object.values(newState).every(el => el === false);
    };

    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [client]);

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
        await createClient(client)
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
                    snackbarMessage: locales.createdCliente
                });
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });

        onClientCreated();
        onClose();
    };

    const handleChange = (e, propName = null) => {
        let { name, value } = e.target;
        if (propName) {
            name = propName;
        }
        setClient(prevClient => ({ ...prevClient, [name]: value }));
    };


    const [condicionesTributarias, setCondicionesTributarias] = useState([]);
    const [paises, setPaises] = useState([]);
    const [provincias, setProvincias] = useState([]);
    const [localidades, setLocalidades] = useState([]);
    const [selectedPais, setSelectedPais] = useState('');
    const [selectedProvincia, setSelectedProvincia] = useState('');

    useEffect(() => {
        // Fetch the list of countries (paises)
        getCondicionesTributarias()
            .then((res) => {
                if (res.error) {
                    updateGlobalState({
                        openSnackbar: true,
                        snackbarSeverity: "error",
                        snackbarMessage: res.message
                    });
                    return;
                }

                const condicionesData = res.data.condicionesTributarias;
                setCondicionesTributarias(condicionesData);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    }, []);

    useEffect(() => {
        // Fetch the list of countries (paises)
        getPaises()
            .then((res) => {
                if (res.error) {
                    updateGlobalState({
                        openSnackbar: true,
                        snackbarSeverity: "error",
                        snackbarMessage: res.message
                    });
                    return;
                }

                const paisesData = res.data.paises;
                setPaises(paisesData);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    }, []);

    useEffect(() => {
        // Fetch the provinces (provincias) based on selected country (pais)
        if (selectedPais) {
            getProvinciasByPaisId(selectedPais)
                .then((res) => {
                    if (res.error) {
                        updateGlobalState({
                            openSnackbar: true,
                            snackbarSeverity: "error",
                            snackbarMessage: res.message
                        });
                        return;
                    }

                    const provinciasData = res.data.provincias;
                    setProvincias(provinciasData);
                })
                .catch((error) => {
                    updateGlobalState({ loadingPage: false });
                    console.error(error);
                });
        }
    }, [selectedPais]);

    useEffect(() => {
        // Fetch the localities (localidades) based on selected province (provincia)
        if (selectedProvincia) {
            getLocalidadesByProvinciaId(selectedProvincia)
                .then((res) => {
                    if (res.error) {
                        updateGlobalState({
                            openSnackbar: true,
                            snackbarSeverity: "error",
                            snackbarMessage: res.message
                        });
                        return;
                    }

                    const localidadesData = res.data.localidades;
                    setLocalidades(localidadesData);
                })
                .catch((error) => {
                    updateGlobalState({ loadingPage: false });
                    console.error(error);
                });
        }
    }, [selectedProvincia]);

    return (
        <>
            <DialogTitle>
                <Typography variant="h6" gutterBottom>
                    Nuevo cliente
                </Typography>
                <HorizontalRule />
            </DialogTitle>
            <DialogContent>
                <Typography variant="subtitle1" gutterBottom>
                    Datos fiscales
                </Typography>
                <TextField
                    error={invalid.cuit}
                    label="CUIT"
                    required
                    margin="dense"
                    name="cuit"
                    value={client.cuit || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                <FormControl fullWidth margin="dense" variant="outlined">
                    <InputLabel>Condicion tributaria</InputLabel>
                    <Select
                        error={invalid.condicionTributariaId}
                        required
                        value={client.condicionTributariaId}
                        onChange={(e) => {
                            handleChange(e, 'condicionTributariaId'); // Update parent state if needed
                        }}
                        label="Condicion tributaria"
                    >
                        {condicionesTributarias.map((condicion) => (
                            <MenuItem key={condicion.id} value={condicion.id}>
                                {condicion.tipo}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
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
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateField
                        error={invalid.fechaNacimiento}
                        name="fechaNacimiento"
                        label="Fecha de Nacimiento"
                        value={dateFechaNacimiento}
                        onChange={(newValue) => {
                            console.log(newValue)
                            let value = undefined
                            try {
                                value = newValue.toISOString()
                            }catch(err) {

                            }
                            setClient({
                                ...client,
                                fechaNacimiento: value,
                            })
                            setDateFechaNacimiento(newValue)}
                        }
                    />

                </LocalizationProvider>
                <Typography variant="subtitle1" gutterBottom>
                    Direccion
                </Typography>
                <TextField
                    error={invalid.calle}
                    label="Calle"
                    required
                    margin="dense"
                    name="calle"
                    value={client.calle || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                <TextField
                    error={invalid.numero}
                    label="Numero"
                    required
                    margin="dense"
                    name="numero"
                    value={client.numero || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                <TextField
                    error={invalid.piso}
                    label="Piso"
                    required
                    margin="dense"
                    name="piso"
                    value={client.piso || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                {/* Selectors for Pais, Provincia, and Localidad */}
                <FormControl fullWidth margin="dense" variant="outlined">
                    <InputLabel>Pais</InputLabel>
                    <Select
                        required
                        value={selectedPais}
                        onChange={(e) => {
                            setSelectedPais(e.target.value);
                            handleChange(e); // Update parent state if needed
                        }}
                        label="Pais"
                    >
                        {paises.map((pais) => (
                            <MenuItem key={pais.id} value={pais.id}>
                                {pais.nombre}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <FormControl fullWidth margin="dense" variant="outlined" disabled={!selectedPais}>
                    <InputLabel>Provincia</InputLabel>
                    <Select
                        required
                        value={selectedProvincia}
                        onChange={(e) => {
                            setSelectedProvincia(e.target.value);
                            handleChange(e); // Update parent state if needed
                        }}
                        label="Provincia"
                    >
                        {provincias.map((provincia) => (
                            <MenuItem key={provincia.id} value={provincia.id}>
                                {provincia.nombre}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <FormControl fullWidth margin="dense" variant="outlined" disabled={!selectedProvincia}>
                    <InputLabel>Localidad</InputLabel>
                    <Select
                        required
                        value={client.localidadId || ''}
                        onChange={(e) => {
                            handleChange(e, 'localidadId'); // Update parent state if needed
                        }} // Update parent state for localidad
                        label="Localidad"
                    >
                        {localidades.map((localidad) => (
                            <MenuItem key={localidad.id} value={localidad.id}>
                                {localidad.nombre}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
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
