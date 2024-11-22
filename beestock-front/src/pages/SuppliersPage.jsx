import React, { useEffect, useState } from "react";
import { Box, Button, Dialog, Grid,
    DialogContent,
    DialogTitle,
    DialogContentText,
    DialogActions,

 } from "@mui/material";
import { Add, Delete, Edit, EmojiEvents } from "@mui/icons-material";
import GenericTable from "../components/GenericTable";
import EditSupplierPopup from "../components/PopUps/EditSupplierPopup";
import CreateSupplierPopup from "../components/PopUps/CreateSupplierPopup";
import SuppliersRankingPopUp from "../components/PopUps/SuppliersRankingPopUp";
import { useGlobalContext } from "../context/GlobalContext";
import { getSuppliers, markSupplierAsDeleted, updateSupplier } from "../services/supplierService"; // Nuevo servicio para marcar como borrado

const SuppliersPage = () => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [data, setData] = useState([]);
    const [selectedData, setSelectedData] = useState(null);

    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);
    const [rankingDialogOpen, setRankingDialogOpen] = useState(false);
    const [confirmDeleteDialogOpen, setConfirmDeleteDialogOpen] = useState(false); // Nuevo estado para el popup de confirmación

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

    const handleViewRanking = () => {
        setRankingDialogOpen(true);
    };

    const handleEdit = (proveedor) => {
        setSelectedData(proveedor);
        setEditDialogOpen(true);
    };

    // Función para abrir el popup de confirmación de eliminación
    const handleDelete = (proveedor) => {
        setSelectedData(proveedor);
        setConfirmDeleteDialogOpen(true); // Abre el popup de confirmación
    };

    // Función para confirmar la eliminación
    const confirmDelete = async () => {
        setConfirmDeleteDialogOpen(false); // Cierra el popup
        if (selectedData) {
            await updateSupplier(selectedData.id, {borrado:true}); // Llama a la API para marcar como borrado:
            setData(data.filter(prov => prov.id !== selectedData.id)); // Actualiza el estado con el proveedor borrado
        }
    };

    const cancelDelete = () => {
        setConfirmDeleteDialogOpen(false); // Cierra el popup sin realizar la acción
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
            onClick: (row) => handleDelete(row),
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
                <Grid item xs={2}>
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={handleViewRanking}
                    >
                        <EmojiEvents />
                        {`Ver calificaciones`}
                    </Button>
                </Grid>
            </Grid>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <GenericTable columns={columns} data={data} actions={actions} entityType="Proveedores" />
                </Grid>
            </Grid>

            {/* EDIT PROVEEDOR */}
            <Dialog open={editDialogOpen} onClose={() => setEditDialogOpen(false)}>
                <EditSupplierPopup
                    onClose={() => setEditDialogOpen(false)}
                    supplierId={selectedData?.id}
                    onUpdated={handleUpdated}
                />
            </Dialog>

            {/* ADD PROVEEDOR */}
            <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
                <CreateSupplierPopup
                    onClose={() => setCreateDialogOpen(false)}
                    onCreated={handleCreated}
                />
            </Dialog>

            {/* RANKING */}
            <Dialog open={rankingDialogOpen} onClose={() => setRankingDialogOpen(false)}>
                <SuppliersRankingPopUp
                    onClose={() => setRankingDialogOpen(false)}
                />
            </Dialog>

            {/* Confirmación de Borrado */}
            <Dialog open={confirmDeleteDialogOpen} onClose={cancelDelete}>
                <DialogTitle>¿Estás seguro de que deseas eliminar este proveedor?</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Esta acción marcará al proveedor como eliminado (borrado) en el sistema. ¿Estás seguro?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={cancelDelete} color="primary">
                        Cancelar
                    </Button>
                    <Button onClick={confirmDelete} color="secondary">
                        Confirmar
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};

export default SuppliersPage;
