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
import {createSupplier, getSuppliers } from "../../services/supplierService";
import { DateField, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import { createIncident } from '../../services/incidentsService';

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

const CreateIncidentPopup = ({ onClose, onCreated }) => {
    const theme = useTheme();
    const { globalState, updateGlobalState } = useGlobalContext();
    const [dateFechaIncidente, setDateFechaIncidente] = useState(dayjs(''));
    const [dateFechaSolucion, setDateFechaSolucion] = useState(dayjs(''));

    const [proveedores, setProveedores] = useState([]);
    const [estadosIncidente, setEstadoIncidente] = useState([
        {id: 1, label: 'PENDIENTE' },
        {id: 2, label: 'SOLUCIONADO' },
    ])

    // Fields and Validations
    const [data, setData] = useState({
        descripcion: '',
        fechaIncidente: '',
        fechaSolucion: '',
        estado: '', // pendiente, solucionado, deprecado.
        proveedorId: '',
    });
    const defaultInvalid = {
        descripcion: false,
        fechaIncidente: false,
        fechaSolucion: false,
        estado: false,
        proveedorId: false,
    }
    const [invalid, setInvalid] = useState(defaultInvalid);

    const validForm = () => {
        const newState = {
            descripcion: !data.descripcion,
            fechaIncidente: !data.fechaIncidente,
            fechaSolucion: data.estado === 2 ? !data.fechaSolucion : false,
            estado: !data.estado,// pendiente, solucionado, deprecado.
            proveedorId: !data.proveedorId,
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

        data.fechaSolucion = data.estado === 2 ? data.fechaSolucion : null;
        await createIncident(data)
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
        getSuppliers()
            .then((res) => {
                if (res.error) {
                    updateGlobalState({
                        openSnackbar: true,
                        snackbarSeverity: "error",
                        snackbarMessage: res.message
                    });
                    return;
                }

                const proveedoresData = res.data.proveedores;
                setProveedores(proveedoresData);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    }, []);

    return (
        <>
            <DialogTitle>
                <Typography variant="h6" gutterBottom>
                    Nuevo Incidente
                </Typography>
                <HorizontalRule />
            </DialogTitle>
            <DialogContent>
                <TextField
                    error={invalid.descripcion}
                    label="Descripcion"
                    required
                    margin="dense"
                    name="descripcion"
                    value={data.descripcion || ''}
                    type="text"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateField
                        error={invalid.fechaIncidente}
                        name="fechaIncidente"
                        label="Fecha de Incidente"
                        value={dateFechaIncidente}
                        onChange={(newValue) => {
                            console.log(newValue)
                            let value = undefined
                            try {
                                value = newValue.toISOString()
                            }catch(err) {

                            }
                            setData({
                                ...data,
                                fechaIncidente: value,
                            })
                            setDateFechaIncidente(newValue)}
                        }
                    />

                </LocalizationProvider>
                <FormControl fullWidth margin="dense" variant="outlined">
                    <InputLabel>Estado del incidente</InputLabel>
                    <Select
                        error={invalid.estado}
                        required
                        value={data.estado}
                        onChange={(e) => {
                            handleChange(e, 'estado'); // Update parent state if needed
                        }}
                        label="Estado"
                    >
                        {estadosIncidente.map((item) => (
                            <MenuItem key={item.id} value={item.id}>
                                {item.label}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                {data.estado === 2 &&
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateField
                        error={invalid.fechaSolucion}
                        name="fechaSolucion"
                        label="Fecha de Solucion"
                        value={dateFechaSolucion}
                        onChange={(newValue) => {
                            console.log(newValue)
                            let value = undefined
                            try {
                                value = newValue.toISOString()
                            }catch(err) {

                            }
                            setData({
                                ...data,
                                fechaSolucion: value,
                            })
                            setDateFechaSolucion(newValue)}
                        }
                    />

                </LocalizationProvider>
                }
                <FormControl fullWidth margin="dense" variant="outlined">
                    <InputLabel>Proveedor</InputLabel>
                    <Select
                        error={invalid.proveedorId}
                        required
                        value={data.proveedorId}
                        onChange={(e) => {
                            handleChange(e, 'proveedorId'); // Update parent state if needed
                        }}
                        label="Proveedor"
                    >
                        {proveedores.map((proveedor) => (
                            <MenuItem key={proveedor.id} value={proveedor.id}>
                                {proveedor.nombre}
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

export default CreateIncidentPopup;
