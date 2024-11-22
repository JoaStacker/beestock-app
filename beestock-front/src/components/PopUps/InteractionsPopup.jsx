import React, { useEffect, useState } from "react";
import EditClientPopup from "./EditClientPopup";
import { getClients, deleteClient } from '../../services/clientService';
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
import { useGlobalContext } from "../../context/GlobalContext";
import GenericTable from "../GenericTable";
import {Add, Delete, Edit} from "@mui/icons-material";
import CreateClientPopup from "./CreateClientPopup";
import {deleteInteraction, getInteractionsByClient} from "../../services/interactionsService";
import CreateInteractionPopup from "./CreateInteractionPopup";
import EditInteractionPopup from "./EditInteractionPopup";

const InteractionsPopup = ({ idCliente, onClose }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [interactions, setInteractions] = useState([]);
    const [selectedInteraction, setSelectedInteraction] = useState(null);

    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);

    const reloadData = () => {
        updateGlobalState({ loadingPage: true });
        setInteractions([]);
        getInteractionsByClient(idCliente)
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

                const interactionsData = res.data.interacciones;
                setInteractions(interactionsData);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    };

    useEffect(() => {
        reloadData()
    }, [idCliente]);

    const handleAdd = () => {
        setCreateDialogOpen(true);
    };

    const handleEdit = (interaction) => {
        setSelectedInteraction(interaction);
        setEditDialogOpen(true);
    };

    const handleDelete = async (interactionId) => {
        await deleteInteraction(interactionId);
        setInteractions(interactions.filter(interaction => interaction.id !== interactionId));
    };

    const handleUpdated = async () => {
        reloadData();
    };

    const handleCreated = async () => {
        reloadData();
    };

    const columns = [
        { id: 'id', label: 'ID' },
        { id: 'fechaInteraccion', label: 'Fecha' },
        { id: 'medio', label: 'Medio de interacción' },
        { id: 'setEmpleadoNombre', label: 'Empleado' },
    ];

    const actions = [
        {
            label: '',
            onClick: (row) => handleEdit(row),
            icon: <Edit />
        },
        {
            label: '',
            onClick: (row) => handleDelete(row.id),
            color: 'secondary',
            icon: <Delete />
        },
    ];

    return (
        <Box sx={{ flexGrow: 1, padding: 2 }}>
            <h2>Interacciones</h2>
            <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
                <Grid item xs={2}>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleAdd}
                    >
                        <Add />
                        {`Nueva interacción`}
                    </Button>
                </Grid>
            </Grid>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <GenericTable columns={columns} data={interactions} actions={actions} entityType="Interacciones"/>
                </Grid>
            </Grid>

            {/*EDIT INTERACTION*/}
            <Dialog open={editDialogOpen} onClose={() => setEditDialogOpen(false)}>
                <EditInteractionPopup
                    interactionId={selectedInteraction?.id}
                    onClose={() => setEditDialogOpen(false)}
                    onUpdated={handleUpdated}
                />
            </Dialog>

            {/*ADD INTERACTION*/}
            <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
                <CreateInteractionPopup
                    clientId={idCliente}
                    onClose={() => setCreateDialogOpen(false)}
                    onCreated={handleCreated}
                />
            </Dialog>
        </Box>
    );
};

export default InteractionsPopup;
