import GenericTable from "../components/GenericTable";
import {Box, Button, Dialog, Grid} from "@mui/material";
import {Add, Delete, Edit, ViewAgenda} from "@mui/icons-material";
import EditClientPopup from "../components/PopUps/EditClientPopup";
import CreateClientPopup from "../components/PopUps/CreateClientPopup";
import InteractionsPopup from "../components/PopUps/InteractionsPopup";
import React, {useEffect, useState} from "react";
import CreateSupplierPopup from "../components/PopUps/CreateSupplierPopup";
import {useGlobalContext} from "../context/GlobalContext";
import {deleteClient, getClients} from "../services/clientService";
import {getSuppliers} from "../services/supplierService";
import EditSupplierPopup from "../components/PopUps/EditSupplierPopup";

const SuppliersPage = () => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [data, setData] = useState([]);
    const [selectedData, setSelectedData] = useState(null);

    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);

    const reloadData = () => {
        updateGlobalState({ loadingPage: true });
        setData([]);
        getSuppliers()
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

                const suppliersData = res.data.proveedores;
                setData(suppliersData);
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

    const handleDelete = async (clientId) => {
        await deleteClient(clientId);
        setData(data.filter(client => client.id !== clientId));
    };

    const handleUpdated = async () => {
        reloadData();
    };

    const handleCreated = async () => {
        reloadData();
    };

    const columns = [
        { id: 'cuit', label: 'CUIT' },
        { id: 'nombre', label: 'Nombre' },
        { id: 'correo', label: 'Email' },
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
                      <h2>Proveedores</h2>
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
                          {`Nuevo proveedor`}
                      </Button>
                  </Grid>
              </Grid>
              <Grid container spacing={2}>
                  <Grid item xs={12}>
                      <GenericTable columns={columns} data={data} actions={actions} entityType="Proveedores"/>
                  </Grid>
              </Grid>

              {/*EDIT PROVEEDOR*/}
              <Dialog open={editDialogOpen} onClose={() => setEditDialogOpen(false)}>
                  <EditSupplierPopup
                      onClose={() => setEditDialogOpen(false)}
                      supplierId={selectedData?.id}
                      onUpdated={handleUpdated}
                  />
              </Dialog>

              {/*ADD PROVEEDOR*/}
              <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
                  <CreateSupplierPopup
                      onClose={() => setCreateDialogOpen(false)}
                      onCreated={handleCreated}
                  />
              </Dialog>
          </Box>
      );
};

export default SuppliersPage;
