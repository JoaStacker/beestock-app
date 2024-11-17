// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
import {
    Box,
    Button, Chip,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    InputLabel, MenuItem, OutlinedInput, Select,
    TextField,
    Typography,
} from "@mui/material";
import { useTheme } from "@mui/material/styles";
import locales from "../../local/en";
import { useGlobalContext } from "../../context/GlobalContext";
import { HorizontalRule} from "@mui/icons-material";
import dayjs from "dayjs";
import {createSupplier, getTiposServicio} from "../../services/supplierService";
import {getCondicionesTributarias} from "../../services/clientService";
import {getLocalidadesByProvinciaId, getPaises, getProvinciasByPaisId} from "../../services/locationService";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

function getStyles(id, data, theme) {
    return {
        fontWeight: data.some(s => s.id === id)
            ? theme.typography.fontWeightMedium
            : theme.typography.fontWeightRegular,
    };
}

const CreateInteractionPopup = ({ onClose, onCreated }) => {
    const theme = useTheme();
    const { globalState, updateGlobalState } = useGlobalContext();
    const [dateFechaInteraccion, setDateFechaInteraccion] = useState(dayjs(''));
    const [condicionesTributarias, setCondicionesTributarias] = useState([]);
    const [paises, setPaises] = useState([]);
    const [provincias, setProvincias] = useState([]);
    const [localidades, setLocalidades] = useState([]);
    const [selectedPais, setSelectedPais] = useState('');
    const [selectedProvincia, setSelectedProvincia] = useState('');
    const [tipoServicios, setTipoServicios] = useState([]);

    // Fields and Validations
    const [data, setData] = useState({
        piso: '',
        cuit: '',
        numero: '',
        calle: '',
        correo: '',
        tipoServicios: [],
        localidadId: '',
        nombre: ''
    });
    const defaultInvalid = {
        piso: false,
        cuit: false,
        numero: false,
        calle: false,
        correo: false,
        tipoServicios: false,
        localidadId: false,
        nombre: false,
    }
    const [invalid, setInvalid] = useState(defaultInvalid);

    const validForm = () => {
        const newState = {
            piso: !data.piso,
            cuit: !data.cuit,
            numero: !data.numero,
            calle: !data.calle,
            correo: !data.correo,
            tipoServicios: !data.tipoServicios || data.tipoServicios?.length === 0,
            localidadId: !data.localidadId,
            nombre: !data.nombre,
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

        await createSupplier(data)
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
        getTiposServicio().then((res) => {
            updateGlobalState({ loadingPage: false });

            if (res.error) {
                updateGlobalState({
                    openSnackbar: true,
                    snackbarSeverity: "error",
                    snackbarMessage: res.message
                });
                return;
            }

            const tipoServicios = res.data.tiposServicio;
            setTipoServicios(tipoServicios)
        }).catch((error) => {
            updateGlobalState({ loadingPage: false });
            console.error(error);
        });
    }, []);

    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [data]);

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

    const handleChangeServicio = (event) => {
        const {
            target: { value },
        } = event;
        setData(prevData => ({
            ...prevData,
            tipoServicios: typeof value === 'string' ? value.split(',') : value
        }));
    };

    return (
        <>
            <DialogTitle>
                <Typography variant="h6" gutterBottom>
                    Nuevo proveedor
                </Typography>
                <HorizontalRule />
            </DialogTitle>
            <DialogContent>
                <TextField
                    error={invalid.cuit}
                    label="CUIT"
                    required
                    margin="dense"
                    name="cuit"
                    value={data.cuit || ''}
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
                <FormControl sx={{ m: 1, width: 300 }}>
                    <InputLabel id="demo-multiple-chip-label">Servicios</InputLabel>
                    <Select
                        labelId="demo-multiple-chip-label"
                        id="demo-multiple-chip"
                        multiple
                        value={data.tipoServicios}
                        onChange={handleChangeServicio}
                        input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
                        renderValue={(selected) => (
                            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                {selected.map((value) => (
                                    <Chip key={value} label={tipoServicios.find(el => el.id === value).nombre} />
                                ))}
                            </Box>
                        )}
                        MenuProps={MenuProps}
                    >
                        {tipoServicios.map((servicio) => (
                            <MenuItem
                                key={servicio.id}
                                value={servicio.id}
                                style={getStyles(servicio.id, data.tipoServicios, theme)}
                            >
                                {servicio.nombre}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
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

export default CreateInteractionPopup;
