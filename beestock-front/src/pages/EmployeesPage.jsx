import React, { useEffect, useState } from "react";
import EditClientPopup from "../components/PopUps/EditClientPopup";
import { getEmployees, markEmployeeAsDeleted, updateEmployee } from "../services/employeesService"; // Nuevo servicio para marcar como borrado
import {
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Grid,
    Box,
    DialogContentText,
} from "@mui/material";
import { Add, Delete, Edit, Work } from "@mui/icons-material";
import GenericTable from "../components/GenericTable";
import { useGlobalContext } from "../context/GlobalContext";
import CreateEmployeePopup from "../components/PopUps/CreateEmployeePopup";
import EditEmployeePopup from "../components/PopUps/EditEmployeePopup";
import EmployeePositionsInfoPopup from "../components/PopUps/EmployeePositionsInfoPopup";

const EmployeesPage = () => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [employees, setEmployees] = useState([]);
    const [selectedEmployee, setSelectedEmployee] = useState(null);
    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [createDialogOpen, setCreateDialogOpen] = useState(false);
    const [positionInfoDialogOpen, setPositionInfoDialogOpen] = useState(false);
    
    // Estado para manejar el popup de confirmación
    const [confirmDeleteDialogOpen, setConfirmDeleteDialogOpen] = useState(false);

    const reloadData = () => {
        updateGlobalState({ loadingPage: true });
        setEmployees([]);
        getEmployees()
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
                const data = res.data.empleados;
                setEmployees(data);
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

    const handleEmployeePositionInfo = (employee) => {
        setSelectedEmployee(employee);
        setPositionInfoDialogOpen(true);
    };

    const handleEditEmployee = (employee) => {
        setSelectedEmployee(employee);
        setEditDialogOpen(true);
    };

    // Nueva función para abrir el popup de confirmación
    const handleDeleteEmployee = (employee) => {
        setSelectedEmployee(employee);
        setConfirmDeleteDialogOpen(true);
    };

    // Función para confirmar la eliminación
    const confirmDelete = async () => {
        setConfirmDeleteDialogOpen(false);
        if (selectedEmployee) {
            await updateEmployee(selectedEmployee.id, {borrado:true}); // Llamada a la API para marcar como borrado
            setEmployees(employees.filter(el => el.id !== selectedEmployee.id)); // Actualizamos la lista de empleados
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
        { id: 'dni', label: 'DNI' },
        { id: 'nombre', label: 'Nombre' },
        { id: 'apellido', label: 'Apellido' },
        { id: 'email', label: 'Email' },
    ];

    const actions = [
        {
          label: 'Editar',
          onClick: (row) => handleEditEmployee(row),
          color: 'secondary',
          icon: <Edit />
        },
        {
          label: 'Ver información de puesto',
          onClick: (row) => handleEmployeePositionInfo(row),
          icon: <Work />
        },
        {
          label: 'Borrar',
          onClick: (row) => handleDeleteEmployee(row),
          color: 'secondary',
          icon: <Delete />
        },
    ];

    return (
        <Box sx={{ flexGrow: 1, padding: 2 }}>
            <Grid container spacing={2} alignItems="flex-end" justifyContent="flex-start">
                <Grid item xs={2}>
                    <h2>Empleados</h2>
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
                        {`Nuevo empleado`}
                    </Button>
                </Grid>
            </Grid>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <GenericTable columns={columns} data={employees} actions={actions} entityType="Empleado"/>
                </Grid>
            </Grid>

            {/* EDIT EMPLOYEE */}
            <Dialog open={editDialogOpen} onClose={() => setEditDialogOpen(false)}>
                <EditEmployeePopup
                    onClose={() => setEditDialogOpen(false)}
                    id={selectedEmployee?.id}
                    onUpdated={handleUpdated}
                />
            </Dialog>

            {/* ADD EMPLOYEE */}
            <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
                <CreateEmployeePopup
                    onClose={() => setCreateDialogOpen(false)}
                    onCreated={handleCreated}
                />
            </Dialog>

            {/* PUESTO */}
            <Dialog open={positionInfoDialogOpen} onClose={() => setPositionInfoDialogOpen(false)}>
                <EmployeePositionsInfoPopup
                    id={selectedEmployee?.id}
                    nombre={selectedEmployee?.nombre + " " + selectedEmployee?.apellido}
                    onClose={() => setPositionInfoDialogOpen(false)}
                />
            </Dialog>

            {/* Confirmación de Borrado */}
            <Dialog open={confirmDeleteDialogOpen} onClose={cancelDelete}>
                <DialogTitle>¿Estás seguro de que deseas eliminar este empleado?</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Esta acción marcará al empleado como eliminado (borrado) en el sistema. ¿Estás seguro?
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

export default EmployeesPage;
