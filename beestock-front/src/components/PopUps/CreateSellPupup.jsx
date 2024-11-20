import React, { useEffect, useState } from 'react';
import {
    getClients,
} from '../../services/clientService';
import {
    Dialog,
    TableContainer,
    Paper,
    Table,
    TableHead,
    TableCell,
    TableRow,
    TableBody,
    Button,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    InputLabel, MenuItem, Select,
    TextField,
    Typography,
    FormControlLabel,
    Checkbox,
    Box,
    Grid,
    Grid2,
} from "@mui/material";
import { useGlobalContext } from "../../context/GlobalContext";
import { DateField, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import { createSell } from '../../services/sellsService';

const CreateSellPopup = ({ onClose, onCreated }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [dateFechaVenta, setDateFechaVenta] = useState(dayjs(''));
    const [clients, setClients] = useState([]);

    // Estado para manejar el diálogo de la línea de venta
    const [openLineaVentaDialog, setOpenLineaVentaDialog] = useState(false);
    const [detalleVenta, setDetalleVenta] = useState({
        horasVendidas: '',
        precioHora: '',
        tipoServicio: 'DESARROLLO'
    });

    // Fields and Validations
    const [data, setData] = useState({
        cantidadCuotas: '',
        detallesVenta: [],
        fechaVenta: '',
        clienteId: '',
    });

    const defaultInvalid = {
        cantidadCuotas: false,
        detallesVenta: false,
        fechaVenta: false,
        clienteId: false,
    };

    const [invalid, setInvalid] = useState(defaultInvalid);
    const [tipoFactura, setTipoFactura] = useState('');
    const [estado, setEstado] = useState(false);

    const validForm = () => {
        const newState = {
            cantidadCuotas: !data.cantidadCuotas,
            detallesVenta: !(data.detallesVenta.length > 0),
            fechaVenta: !data.fechaVenta,
            clienteId: !data.clienteId,
        };
        setInvalid(newState);
        console.log(newState);
        return Object.values(newState).every(el => el === false);
    };

    const handleEstadoChange = (event) => {
        setEstado(event.target.checked); // Si está marcado, estado será true (1); si no, false (0)
    };

    const handleDetalleChange = (e, field) => {
        setDetalleVenta({
            ...detalleVenta,
            [field]: e.target.value
        });
    };

    const handleAddDetalle = () => {
        setData({
            ...data,
            detallesVenta: [...data.detallesVenta, detalleVenta]
        });
        setDetalleVenta({ horasVendidas: '', precioHora: '', tipoServicio: 'DESARROLLO' });
        setOpenLineaVentaDialog(false);
    };

    const handleDeleteDetalle = (index) => {
        const newDetalles = data.detallesVenta.filter((_, i) => i !== index);
        setData({
            ...data,
            detallesVenta: newDetalles
        });
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
        let payload = {
            ...data,
            empleadoId: globalState.user.empleado.id,
            estado: estado ? 1 : 0 // Se agrega el estado al payload: 1 si está pagado, 0 si no
        };
        await createSell(payload)
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
                    snackbarMessage: "Venta creada exitosamente"
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
        setData(prevData => {
            const newData = { ...prevData, [name]: value };

            // Actualizar el tipo de factura cuando se seleccione un cliente
            if (name === 'clienteId') {
                const selectedClient = clients.find(client => client.id === value);
                if (selectedClient) {
                    const condicionId = selectedClient.condicionTributaria?.id;
                    if (condicionId === 1 || condicionId === 2) {
                        setTipoFactura('TIPO A - '+selectedClient.condicionTributaria?.tipo);
                    } else if (condicionId === 3 || condicionId === 4 || condicionId === 5) {
                        setTipoFactura('TIPO B - '+selectedClient.condicionTributaria?.tipo);
                    } else {
                        setTipoFactura('');
                    }
                }
            }
            return newData;
        });
    };

    const reloadData = () => {
        updateGlobalState({ loadingPage: true });
        setClients([]);
        getClients()
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

                const clientsData = res.data.clientes;
                setClients(clientsData);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    };

    useEffect(() => {
        reloadData();
    }, []);

    useEffect(() => {
        setInvalid(defaultInvalid);
    }, [data]);

    // Función para calcular el total de la venta
    const calculateTotalVenta = () => {
        return data.detallesVenta.reduce((total, detalle) => {
            const subTotal = parseFloat(detalle.horasVendidas || 0) * parseFloat(detalle.precioHora || 0);
            return total + subTotal;
        }, 0);
    };

    return (
        <Dialog open onClose={onClose}>
            <DialogTitle>
                <Typography variant="h6" gutterBottom>
                    Nueva venta
                </Typography>
            </DialogTitle>
            <DialogContent>
                <FormControl fullWidth margin="dense" variant="outlined">
                    <InputLabel>Cliente</InputLabel>
                    <Select
                        error={invalid.clienteId}
                        required
                        value={data.clienteId}
                        onChange={(e) => handleChange(e, 'clienteId')}
                        label="Cliente"
                    >
                        {clients.map((item) => (
                            <MenuItem key={item.id} value={item.id}>
                                {`[${item.cuit}] ${item.nombre} ${item.apellido}`}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                {/* Tipo de factura */}
                {data.clienteId && (
                    <Typography variant="body1" sx={{ marginTop: 2 }}>
                        Tipo de factura: <strong>{tipoFactura}</strong>
                    </Typography>
                )}

                <TextField
                    error={invalid.cantidadCuotas}
                    label="Cantidad de cuotas"
                    required
                    margin="dense"
                    name="cantidadCuotas"
                    value={data.cantidadCuotas || ''}
                    type="number"
                    onChange={handleChange}
                    fullWidth
                    variant="outlined"
                />
                
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateField
                        error={invalid.fechaVenta}
                        name="fechaVenta"
                        label="Fecha de Venta"
                        value={dateFechaVenta}
                        onChange={(newValue) => {
                            let value = undefined;
                            try {
                                value = newValue.toISOString();
                            } catch (err) {
                                // Handle error
                            }
                            setData({
                                ...data,
                                fechaVenta: value
                            });
                            setDateFechaVenta(newValue);
                        }}
                    />
                </LocalizationProvider>
                {/* Tabla de detalles de venta */}
                <TableContainer component={Paper} sx={{ marginTop: 2 }}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Horas Vendidas</TableCell>
                                <TableCell>Precio por Hora</TableCell>
                                <TableCell>Tipo de Servicio</TableCell>
                                <TableCell>Sub Total</TableCell>
                                <TableCell>Acción</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {data.detallesVenta.map((detalle, index) => {
                                const subTotal = parseFloat(detalle.horasVendidas || 0) * parseFloat(detalle.precioHora || 0);
                                return (
                                    <TableRow key={index}>
                                        <TableCell>{detalle.horasVendidas}</TableCell>
                                        <TableCell>{detalle.precioHora}</TableCell>
                                        <TableCell>{detalle.tipoServicio}</TableCell>
                                        <TableCell>{subTotal.toFixed(2)}</TableCell>
                                        <TableCell>
                                            <Button 
                                                onClick={() => handleDeleteDetalle(index)} 
                                                color="secondary"
                                                variant="outlined"
                                            >
                                                Eliminar
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
                
                <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
                    <Grid item xs={6}>
                        <Typography variant="h6" sx={{ marginTop: 2, fontWeight: 'bold' }}>
                            TOTAL DE LA VENTA: ${calculateTotalVenta().toFixed(2)}
                        </Typography>
                    </Grid>
                    <Grid item xs={6} sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                        {/* Checkbox para indicar si la venta está pagada */}
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={estado}
                                    onChange={handleEstadoChange}
                                    color="primary"
                                />
                            }
                            label="PAGADO"
                            sx={{ marginTop: 2 }}
                        />
                    </Grid>
                </Grid>

    
                <Button 
                    onClick={() => setOpenLineaVentaDialog(true)} 
                    color="primary" 
                    variant="outlined" 
                    sx={{ marginTop: 2 }}
                >
                    Agregar nueva línea de venta
                </Button>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="primary">
                    Cancelar
                </Button>
                <Button onClick={handleSubmit} color="primary">
                    Guardar
                </Button>
            </DialogActions>
            {/* Diálogo para agregar línea de venta */}
            <Dialog open={openLineaVentaDialog} onClose={() => setOpenLineaVentaDialog(false)}>
                <DialogTitle>Agregar línea de venta</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Horas Vendidas"
                        required
                        value={detalleVenta.horasVendidas}
                        onChange={(e) => handleDetalleChange(e, 'horasVendidas')}
                        type="number"
                        fullWidth
                        variant="outlined"
                        margin="dense"
                    />
                    <TextField
                        label="Precio por Hora"
                        required
                        value={detalleVenta.precioHora}
                        onChange={(e) => handleDetalleChange(e, 'precioHora')}
                        type="number"
                        fullWidth
                        variant="outlined"
                        margin="dense"
                    />
                    <FormControl fullWidth margin="dense" variant="outlined">
                        <InputLabel>Tipo de Servicio</InputLabel>
                        <Select
                            value={detalleVenta.tipoServicio}
                            onChange={(e) => handleDetalleChange(e, 'tipoServicio')}
                            label="Tipo de Servicio"
                        >
                            <MenuItem value="DESARROLLO">DESARROLLO</MenuItem>
                            <MenuItem value="SOPORTE">SOPORTE</MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenLineaVentaDialog(false)} color="primary">
                        Cancelar
                    </Button>
                    <Button onClick={handleAddDetalle} color="primary">
                        Agregar
                    </Button>
                </DialogActions>
            </Dialog>
        </Dialog>
    );
};


export default CreateSellPopup;
