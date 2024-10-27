import React, { useEffect, useState } from "react";
import EditClientPopup from "../components/PopUps/EditClientPopup";
import { getClients, deleteClient } from '../services/clientService';
import {
    Button,
    Dialog,
    Select,
    MenuItem,
    InputLabel,
    FormControl,
    Grid,
    Box,
    DialogTitle,
    DialogContent, DialogContentText, TextField, DialogActions
} from "@mui/material";
import { useGlobalContext } from "../context/GlobalContext";
import GenericTable from "../components/GenericTable";
import {Add, Delete, Edit} from "@mui/icons-material";
import CreateClientPopup from "../components/PopUps/CreateClientPopup";

const ClientsPage = () => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [clients, setClients] = useState([]);
    const [filteredClients, setFilteredClients] = useState([]);
    const [selectedClient, setSelectedClient] = useState(null);

    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);

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

    const handleAddClient = () => {
        setCreateDialogOpen(true);
    };

    const handleEditClient = (client) => {
        setSelectedClient(client);
        setEditDialogOpen(true);
    };

    const handleDeleteClient = async (clientId) => {
        await deleteClient(clientId);
        setClients(clients.filter(client => client.id !== clientId));
    };

    const handleClientUpdated = async () => {
        reloadData();
    };

    const handleClientCreated = async () => {
        reloadData();
    };

    const columns = [
        { id: 'cuit', label: 'CUIT' },
        { id: 'nombre', label: 'Nombre' },
        { id: 'apellido', label: 'Apellido' },
        { id: 'email', label: 'Email' },
        { id: 'fechaNacimiento', label: 'Fecha de nacimiento' },
    ];

    const actions = [
        {
            label: 'Editar',
            onClick: (row) => handleEditClient(row),
            icon: <Edit />
        },
        {
            label: 'Borrar',
            onClick: (row) => handleDeleteClient(row.id),
            color: 'secondary',
            icon: <Delete />
        },
    ];

    return (
        <div>
            <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
                <Grid item xs={2}>
                    <h2>Clientes</h2>
                </Grid>
            </Grid>
            <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
                <Grid item xs={2}>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleAddClient}
                    >
                        <Add />
                        {`Nuevo cliente`}
                    </Button>
                </Grid>
            </Grid>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <GenericTable columns={columns} data={clients} actions={actions} entityType="Cliente"/>
                </Grid>
            </Grid>

            {/*EDIT CLIENT*/}
            <Dialog open={editDialogOpen} onClose={() => setEditDialogOpen(false)}>
                <EditClientPopup
                    onClose={() => setEditDialogOpen(false)}
                    clientId={selectedClient?.id}
                    onClientUpdated={handleClientUpdated}
                />
            </Dialog>

            {/*ADD CLIENT*/}
            <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
                <CreateClientPopup
                    onClose={() => setCreateDialogOpen(false)}
                    onClientCreated={handleClientCreated}
                />
            </Dialog>
        </div>
    );
};

export default ClientsPage;
