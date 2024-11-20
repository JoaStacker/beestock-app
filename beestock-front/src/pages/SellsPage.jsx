import React, { useEffect, useState } from "react";
import { getSells, getSellInfo, paySell } from '../services/sellsService'; // Asumiendo que tienes un servicio para actualizar el estado de la venta
import { Box, Grid, Button, Dialog, TableContainer, Paper, Table, TableHead, TableCell, TableRow, TableBody, DialogActions, DialogContent, DialogTitle, Typography } from "@mui/material";
import { useGlobalContext } from "../context/GlobalContext";
import GenericTable from "../components/GenericTable";
import { Add, Work } from "@mui/icons-material";
import CreateSellPopup from "../components/PopUps/CreateSellPupup";

const SellsPage = () => {
  const { globalState, updateGlobalState } = useGlobalContext();
  const [data, setData] = useState([]);
  const [selectedData, setSelectedData] = useState(null);
  const [createDialogOpen, setCreateDialogOpen] = useState(false);
  const [detailsDialogOpen, setDetailsDialogOpen] = useState(false);

  // Función para recargar los datos de ventas
  const reloadData = () => {
    updateGlobalState({ loadingPage: true });
    setData([]);
    getSells()
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
        setData(res.data.ventas);
      })
      .catch((error) => {
        updateGlobalState({ loadingPage: false });
        console.error(error);
      });
  };

  useEffect(() => {
    reloadData();
  }, []);

  // Función para manejar la apertura del dialogo de detalles
  const handleDataInfo = (ventaId) => {
    getSellInfo(ventaId)  // Asumiendo que esta función obtiene los detalles de la venta
      .then((res) => {
        if (res.error) {
          updateGlobalState({
            openSnackbar: true,
            snackbarSeverity: "error",
            snackbarMessage: res.message
          });
          return;
        }
        setSelectedData(res.data);  // Guardamos los detalles de la venta
        setDetailsDialogOpen(true);  // Abrimos el dialogo con los detalles
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleCloseDetailsDialog = () => {
    setDetailsDialogOpen(false);
    setSelectedData(null);  // Limpiamos los datos seleccionados cuando se cierra el dialogo
  };

  // Función para marcar la venta como pagada
  const handleMarkAsPaid = (ventaId) => {
    updateGlobalState({ loadingPage: true });
    paySell(ventaId, {estado: 1})  // Suponiendo que 1 es el estado "Pagado"
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
          snackbarMessage: "Venta marcada como pagada"
        });

        reloadData();  // Recargar los datos para reflejar el cambio
        setDetailsDialogOpen();
      })
      .catch((error) => {
        updateGlobalState({ loadingPage: false });
        console.error(error);
      });
  };

  const columns = [
    { id: 'id', label: 'ID' },
    { id: 'montoTotal', label: 'Monto' },
    { id: 'estadoNombre', label: 'Estado' },
    { id: 'fechaVenta', label: 'Fecha' },
    { id: 'clienteNombre', label: 'Cliente' },
  ];

  const actions = [
    {
      label: 'Ver información de venta',
      onClick: (row) => handleDataInfo(row.id),  // Usamos el id de la venta
      icon: <Work />
    }
  ];

  return (
    <Box sx={{ flexGrow: 1, padding: 2 }}>
      <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
        <Grid item xs={2}>
          <h2>Ventas</h2>
        </Grid>
      </Grid>
      <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
        <Grid item xs={2}>
          <Button variant="contained" color="primary" onClick={() => setCreateDialogOpen(true)}>
            <Add />
            Nueva venta
          </Button>
        </Grid>
      </Grid>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <GenericTable columns={columns} data={data} actions={actions} entityType="Venta" />
        </Grid>
      </Grid>

      {/* Dialogo de Detalles de Venta */}
      <Dialog open={detailsDialogOpen} onClose={handleCloseDetailsDialog}>
        <DialogTitle>Detalles de la Venta</DialogTitle>
        <DialogContent>
          {selectedData && (
            <div>
              <Typography variant="h6" gutterBottom>
                Información General
              </Typography>
              <Typography variant="body1">
                <strong>ID de Venta:</strong> {selectedData.id}
              </Typography>
              <Typography variant="body1">
                <strong>Cantidad de cuotas:</strong> {selectedData.cantidadCuotas}
              </Typography>
              <Typography variant="body1">
                <strong>Monto Total:</strong> {selectedData.montoTotal}
              </Typography>
              <Typography variant="body1" >
                <strong>Estado:</strong> <strong style={{ 
                              color: (selectedData.estadoNombre === "PAGADO" ? 'green' : 'red'),
                            }}>{selectedData.estadoNombre}</strong>
              </Typography>

              {/* Botón para marcar como pagada */}
              {selectedData.estadoNombre === "PENDIENTE" && ( // Estado pendiente
                <Button 
                  variant="contained" 
                  color="primary" 
                  onClick={() => handleMarkAsPaid(selectedData.id)}
                  sx={{ marginTop: 2 }}
                >
                  MARCAR COMO PAGADA
                </Button>
              )}

              <Typography variant="h6" gutterBottom sx={{ marginTop: 2 }}>
                Detalles de Venta
              </Typography>
              <TableContainer component={Paper}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Horas Vendidas</TableCell>
                      <TableCell>Precio por Hora</TableCell>
                      <TableCell>Tipo de Servicio</TableCell>
                      <TableCell>Sub Total</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {selectedData.detallesVenta?.map((detalle, index) => {
                      const subTotal = parseFloat(detalle.horasVendidas || 0) * parseFloat(detalle.precioHora || 0);
                      return (
                        <TableRow key={index}>
                          <TableCell>{detalle.horasVendidas}</TableCell>
                          <TableCell>{detalle.precioHora}</TableCell>
                          <TableCell>{detalle.tipoServicio}</TableCell>
                          <TableCell>{subTotal.toFixed(2)}</TableCell>
                        </TableRow>
                      );
                    })}
                  </TableBody>
                </Table>
              </TableContainer>
            </div>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDetailsDialog} color="primary">
            Cerrar
          </Button>
        </DialogActions>
      </Dialog>

      {/* Popup para Crear Nueva Venta */}
      <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
        <CreateSellPopup onClose={() => setCreateDialogOpen(false)} onCreated={reloadData} />
      </Dialog>
    </Box>
  );
};

export default SellsPage;
