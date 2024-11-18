import React, { useEffect, useState } from "react";
import EditClientPopup from "./EditClientPopup";
import { getClients, deleteClient } from '../../services/clientService';
import {
    TableContainer,
    TableHead,
    TableRow,
    TableBody,
    Paper,
    Table,
    TableCell,
    Grid,
    Box,
    Typography,
    Button,
    FormControl,
    InputLabel,
    Select,
    MenuItem
} from "@mui/material";
import { useGlobalContext } from "../../context/GlobalContext";
import {Cancel, Edit, Save, SettingsApplications} from "@mui/icons-material";
import { createNewPosition, getPositionsHistoryByEmployee } from "../../services/historyPositionsService";
import { getPositions } from "../../services/positionsService";
import { updateEmployee } from "../../services/employeesService";
import locales from "../../local/en";
import { DateField, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";

const EmployeePositionsInfoPopup = ({ id, nombre, onClose }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [dateFechaIngreso, setDateFechaIngreso] = useState(dayjs(''));
    const [ history, setHistory] = useState([]);
    const [ puestos, setPuestos] = useState([]);
    const [ editMode, setEditMode] = useState(false);

     // Fields and Validations
    const [data, setData] = useState({
        puestoId: '',
        fechaIngreso: '',
    });
    const defaultInvalid = {
        puestoId: false,
        fechaIngreso: false,
    }
    const [invalid, setInvalid] = useState(defaultInvalid);
    const validForm = () => {
        const newState = {
            puestoId: !data.puestoId,
            fechaIngreso: !data.fechaIngreso,
        };
        setInvalid(newState);
        return Object.values(newState).every(el => el === false);
    };

    const handleChangePosition = async () => {
        if (!validForm()) return;

        updateGlobalState({ loadingPage: true });
        let payload = {
            puestoId: data.puestoId,
            empleadoId: id,
            fechaIngreso: data.fechaIngreso,
        }
        await createNewPosition(payload)
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

                setEditMode(false);
                reloadData();
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    };

    const handleChange = (e, propName = null) => {
        let { name, value } = e.target;
        if (propName) {
            name = propName;
        }
        setData(prevClient => ({ ...prevClient, [name]: value }));
    };

    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [data]);

    const reloadData = () => {
        updateGlobalState({ loadingPage: true });
        setHistory([]);
        getPositionsHistoryByEmployee(id)
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

                const history = res.data.historialPuestos;
                
                setHistory(history);
                setData({
                    puestoId: res.data.puestoId
                })
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    };

    const columns = [
        { id: 'puestoNombre', label: 'Puesto' },
        { id: 'fechaIngreso', label: 'Fecha de Ingreso' },
        { id: 'fechaSalida', label: 'Fecha de Salida' },
        { id: 'antiguedad', label: 'Antiguedad (años)' },
    ];

    useEffect(() => {
        reloadData()
    }, []);

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

    return (
        <Box sx={{ flexGrow: 1, padding: 2 }}>
            <h2>Historial de puestos</h2>
            Empleado: <h3>{nombre}</h3>
            <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
                <Grid item xs={6}>
                    <FormControl fullWidth margin="dense" variant="outlined" disabled={!editMode}>
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
                    {editMode &&
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
                    }
                </Grid>
                <Grid item xs={6}>
                    {
                        editMode ? <>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => {
                                    handleChangePosition();
                                }}
                            >
                                <Save />
                                {`Guardar puesto`}
                            </Button> 
                            <Button
                            variant="contained"
                            color="primary"
                            onClick={() => {
                                setInvalid(defaultInvalid);
                                setEditMode(false);
                            }}
                        >
                            <Cancel />
                            {`Cancelar`}
                        </Button> 
                     </>
                        : <Button
                            variant="contained"
                            color="primary"
                            onClick={() => {
                                setEditMode(true);
                            }}
                        >
                            <Edit />
                            {`Editar puesto`}
                        </Button>
                    }
                    
                </Grid>
            </Grid>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Box sx={{ maxHeight: 400, overflowY: 'auto' }}>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        {columns.map((column) => (
                                            <TableCell key={column.id}>{column.label}</TableCell>
                                        ))}
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {history.map((row, index) => (
                                        <TableRow key={index}>
                                            {columns.map((column) => (
                                                <TableCell key={column.id}>
                                                    {row[column.id] !== undefined ? row[column.id] : '-'}
                                                </TableCell>
                                            ))}
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
};

export default EmployeePositionsInfoPopup;
