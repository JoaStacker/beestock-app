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
} from "@mui/material";
import { useGlobalContext } from "../../context/GlobalContext";
import GenericTable from "../GenericTable";
import {Add, Delete, Edit} from "@mui/icons-material";
import CreateClientPopup from "./CreateClientPopup";
import {deleteInteraction, getInteractionsByClient} from "../../services/interactionsService";
import CreateInteractionPopup from "./CreateInteractionPopup";
import EditInteractionPopup from "./EditInteractionPopup";
import { getRankingByIncidents } from "../../services/supplierService";

const SuppliersRankingPopUp = ({ onClose }) => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const [ ranking, setRanking] = useState([]);

    const reloadData = () => {
        updateGlobalState({ loadingPage: true });
        setRanking([]);
        getRankingByIncidents()
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

                const ranking = res.data.proveedores;
                setRanking(ranking);
            })
            .catch((error) => {
                updateGlobalState({ loadingPage: false });
                console.error(error);
            });
    };

    useEffect(() => {
        reloadData()
    }, []);

    const columns = [
        { id: 'ranking', label: 'Puesto' },
        { id: 'nombre', label: 'Proveedor' },
        { id: 'cuit', label: 'CUIT' },
        { id: 'promedioTiempoSolucion', label: 'Promedio tiempo soluciones (min)' },
    ];

    return (
        <Box sx={{ flexGrow: 1, padding: 2 }}>
            <h2>Calificacion de proveedores</h2>
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
                                    {ranking.map((row, index) => (
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

export default SuppliersRankingPopUp;
