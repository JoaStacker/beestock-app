// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
import {
    Box,
    Button, Chip,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    InputLabel, MenuItem, Select,
    TextField,
    Typography,
} from "@mui/material";
import locales from "../../local/en";
import { useGlobalContext } from "../../context/GlobalContext";
import { HorizontalRule} from "@mui/icons-material";
import {getLocalidadesByProvinciaId, getPaises, getProvinciasByPaisId} from "../../services/locationService";
import { createEmployee } from '../../services/employeesService';
import { getPositions } from '../../services/employeesService copy';
import { DateField, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";

const CreateEmployeePopup = ({ onClose, onCreated }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [dateFechaIngreso, setDateFechaIngreso] = useState(dayjs(''));

    const [paises, setPaises] = useState([]);
    const [provincias, setProvincias] = useState([]);
    const [localidades, setLocalidades] = useState([]);
    const [selectedPais, setSelectedPais] = useState('');
    const [selectedProvincia, setSelectedProvincia] = useState('');
    const [puestos, setPuestos] = useState([]);

    // Fields and Validations
    const [data, setData] = useState({
        dni: '',
        nombre: '',
        apellido: '',
        email: '',
        numero: '',
        calle: '',
        piso: '',
        fechaIngreso: '',
        localidadId: '',
    });
    const defaultInvalid = {
        dni: false,
        nombre: false,
        apellido: false,
        email: false,
        numero: false,
        calle: false,
        piso: false,
        fechaIngreso: '',
        localidadId: false,
    }
    const [invalid, setInvalid] = useState(defaultInvalid);

    const validForm = () => {
        const newState = {
            dni: !data.dni,
            nombre: !data.nombre,
            apellido: !data.apellido,
            email: !data.email,
            numero: !data.numero,
            calle: !data.calle,
            piso: !data.piso,
            fechaIngreso: !data.fechaIngreso,
            localidadId: !data.localidadId,
        };
        setInvalid(newState);
        return Object.values(newState).every(el => el === false);
    };

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

        await createEmployee(data)
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

    
    useEffect(() => {
        getPositions()
            .then((res) => {
                if (res.error) {
                    updateGlobalState({
                        openSnackbar: true,
                        snackbarSeverity: "error",
                        snackbarMessage: res.message
                    });
                    return;
                }

                const puestosData = res.data.puestos;
                setPuestos(puestosData);
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
                    Nuevo Empleado
                </Typography>
                <HorizontalRule />
            </DialogTitle>
            <DialogContent>
                <TextField
                    error={invalid.dni}
                    label="DNI"
                    required
                    margin="dense"
                    name="dni"
                    value={data.dni || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
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
                    error={invalid.apellido}
                    label="Apellido"
                    required
                    margin="dense"
                    name="apellido"
                    value={data.apellido || ''}
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
                    value={data.email || ''}
                    type="email"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                
                <FormControl fullWidth margin="dense" variant="outlined">
                    <InputLabel>Puesto</InputLabel>
                    <Select
                        required
                        value={data.puestoId || ''}
                        onChange={(e) => {
                            handleChange(e, 'puestoId'); // Update parent state if needed
                        }} // Update parent state for localidad
                        label="Puesto"
                    >
                        {puestos.map((item) => (
                            <MenuItem key={item.id} value={item.id}>
                                {item.nombre}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateField
                        error={invalid.fechaIngreso}
                        name="fechaIngreso"
                        label="Fecha de ingreso"
                        value={dateFechaIngreso}
                        onChange={(newValue) => {
                            console.log(newValue)
                            let value = undefined
                            try {
                                value = newValue.toISOString()
                            }catch(err) {

                            }
                            setData({
                                ...data,
                                fechaIngreso: value,
                            })
                            setDateFechaIngreso(newValue)}
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
                    value={data.calle || ''}
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
                    value={data.numero || ''}
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
                    value={data.piso || ''}
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
                        value={data.localidadId || ''}
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

export default CreateEmployeePopup;
