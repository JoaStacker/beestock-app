// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
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
import { getLocalidadesByProvinciaId, getPaises, getProvinciasByPaisId,  } from "../../services/locationService";
import { getOneEmployee, updateEmployee } from '../../services/employeesService';

const EditEmployeePopup = ({ onClose, id, onUpdated }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [paises, setPaises] = useState([]);
    const [provincias, setProvincias] = useState([]);
    const [localidades, setLocalidades] = useState([]);
    const [selectedPais, setSelectedPais] = useState('');
    const [selectedProvincia, setSelectedProvincia] = useState('');

    // Fields and Validations
    const [data, setData] = useState({
        dni: '',
        nombre: '',
        apellido: '',
        email: '',
        calle: '',
        numero: '',
        piso: '',
        localidadId: '',
    });
    const defaultInvalid = {
        dni: false,
        nombre: false,
        apellido: false,
        email: false,
        calle: false,
        numero: false,
        piso: false,
        localidadId: false,
    }
    const [invalid, setInvalid] = useState(defaultInvalid);
    const validForm = () => {
        const newState = {
            dni: !data.dni,
            nombre: !data.nombre,
            apellido: !data.apellido,
            email: !data.email,
            calle: !data.calle,
            numero: !data.numero,
            piso: !data.piso,
            fechaNacimiento: !data.fechaNacimiento,
            localidadId: !data.localidadId,
        };
        setInvalid(newState);
        return Object.values(newState).every(el => el === false);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validForm()) return;

        updateGlobalState({ loadingPage: true });
        let payload = {
            dni: data.dni,
            nombre: data.nombre,
            apellido: data.apellido,
            email: data.email,
            calle: data.calle,
            numero: data.numero,
            piso: data.piso,
            localidadId: data.localidadId,
        }
        await updateEmployee(id, payload)
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

    const handleChange = (e, propName = null) => {
        let { name, value } = e.target;
        if (propName) {
            name = propName;
        }
        setData(prevdata => ({ ...prevdata, [name]: value }));
    };

    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [data]);

    useEffect(() => {
        getOneEmployee(id)
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

                const empleado = res.data;
                setData(empleado)

                setSelectedPais(empleado.pais.id)
                setSelectedProvincia(empleado.provincia.id)
                setData({
                    dni: empleado.dni,
                    nombre: empleado.nombre,
                    apellido: empleado.apellido,
                    email: empleado.email,
                    calle: empleado.direccion.calle,
                    numero: empleado.direccion.numero,
                    piso: empleado.direccion.piso,
                    localidadId: empleado.localidad.id,
                });
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    }, [id]);

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
            <DialogTitle>{`Editar empleado`}</DialogTitle>
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

export default EditEmployeePopup;
