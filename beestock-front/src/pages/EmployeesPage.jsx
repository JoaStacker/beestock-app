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
import {Add, Delete, Edit, ViewAgenda, Work} from "@mui/icons-material";
import CreateClientPopup from "../components/PopUps/CreateClientPopup";
import TopBar from "../components/TopBar";
import Sidebar from "../components/Sidebar";
import InteractionsPopup from "../components/PopUps/InteractionsPopup";
import { deleteEmployee, getEmployees } from "../services/employeesService";
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

    const handleDeleteEmployee = async (id) => {
        await deleteEmployee(id);
        setEmployees(employees.filter(el => el.id !== id));
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
          label: 'Ver informacion de puesto',
          onClick: (row) => handleEmployeePositionInfo(row),
          icon: <Work />
        },
        {
          label: 'Borrar',
          onClick: (row) => handleDeleteEmployee(row.id),
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
                        {`Nuevo cliente`}
                    </Button>
                </Grid>
            </Grid>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <GenericTable columns={columns} data={employees} actions={actions} entityType="Empleado"/>
                </Grid>
            </Grid>

            {/*EDIT CLIENT*/}
            <Dialog open={editDialogOpen} onClose={() => setEditDialogOpen(false)}>
                <EditEmployeePopup
                    onClose={() => setEditDialogOpen(false)}
                    id={selectedEmployee?.id}
                    onUpdated={handleUpdated}
                />
            </Dialog>

            {/*ADD CLIENT*/}
            <Dialog open={createDialogOpen} onClose={() => setCreateDialogOpen(false)}>
                <CreateEmployeePopup
                    onClose={() => setCreateDialogOpen(false)}
                    onCreated={handleCreated}
                />
            </Dialog>

            {/*PUESTO*/}
            <Dialog open={positionInfoDialogOpen} onClose={() => setPositionInfoDialogOpen(false)}>
                <EmployeePositionsInfoPopup
                    id={selectedEmployee?.id}
                    nombre={selectedEmployee?.nombre + " " + selectedEmployee?.apellido}
                    onClose={() => setPositionInfoDialogOpen(false)}
                />
            </Dialog>
        </Box>
    );
};

export default EmployeesPage;
