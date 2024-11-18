import GenericTable from "../components/GenericTable";
import IncidentList from "../components/IncidentList";
import {useGlobalContext} from "../context/GlobalContext";
import React, {useEffect, useState} from "react";
import {getSuppliers} from "../services/supplierService";
import {deleteClient} from "../services/clientService";
import {Add, Delete, Edit} from "@mui/icons-material";
import {Box, Button, Dialog, Grid} from "@mui/material";
import EditSupplierPopup from "../components/PopUps/EditSupplierPopup";
import CreateSupplierPopup from "../components/PopUps/CreateSupplierPopup";
import {getIncidents} from "../services/incidentsService";
import CreateIncidentPopup from "../components/PopUps/CreateIncidentPopUp";
import EditIncidentPopup from "../components/PopUps/EditIncidentPopUp";

const IncidentsPage = () => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [data, setData] = useState([]);
    const [selectedData, setSelectedData] = useState(null);

    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);

    const reloadData = () => {
      updateGlobalState({ loadingPage: true });
      setData([]);
      getIncidents()
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

            const incidentsData = res.data.incidentes;
            setData(incidentsData);
          })
          .catch((error) => {
            updateGlobalState({ loadingPage: false });
            console.error(error);
          });
    };

    useEffect(() => {
      reloadData();
    }, []);

    const handleAdd = () => {
      setCreateDialogOpen(true);
    };

    const handleEdit = (proveedor) => {
      setSelectedData(proveedor);
      setEditDialogOpen(true);
    };

    const handleDelete = async (id) => {
      await deleteClient(id);
      setData(data.filter(el => el.id !== id));
    };

    const handleUpdated = async () => {
      reloadData();
    };

    const handleCreated = async () => {
      reloadData();
    };

    const columns = [
      { id: 'id', label: 'ID' },
      { id: 'descripcion', label: 'Descripcion' },
      { id: 'fechaIncidente', label: 'Fecha de incidente' },
      { id: 'fechaSolucion', label: 'Fecha de solucion' },
      { id: 'estado', label: 'Estado' },
      { id: 'nombreProveedor', label: 'Proveedor' },
    ];

    const actions = [
      {
        label: 'Editar',
        onClick: (row) => handleEdit(row),
        icon: <Edit />
      },
      {
        label: 'Borrar',
        onClick: (row) => handleDelete(row.id),
        color: 'secondary',
        icon: <Delete />
      },
    ];

    return (
        <Box sx={{ flexGrow: 1, padding: 2 }}>
          <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
            <Grid item xs={2}>
              <h2>Incidentes</h2>
            </Grid>
          </Grid>
          <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
            <Grid item xs={2}>
              <Button
                  variant="contained"
                  color="primary"
                  onClick={handleAdd}
              >
                <Add />
                {`Nuevo incidente`}
              </Button>
            </Grid>
          </Grid>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <GenericTable columns={columns} data={data} actions={actions} entityType="Proveedores"/>
            </Grid>
          </Grid>

          {/*EDIT INCIDIENT*/}
          <Dialog open={editDialogOpen} onClose={() => setEditDialogOpen(false)}>
            <EditIncidentPopup
              onClose={() => setEditDialogOpen(false)}
              incidentId={selectedData?.id}
              onUpdated={handleUpdated}
            />
          </Dialog>

          {/*ADD INCIDIENT*/}
          <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
            <CreateIncidentPopup
              onClose={() => setCreateDialogOpen(false)}
              onCreated={handleCreated}
            />
          </Dialog>
        </Box>
    );
};

export default IncidentsPage;
