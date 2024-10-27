import React, { useState } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TableSortLabel,
  TablePagination,
  Paper,
  Button,
  TextField,
  Box,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle, InputLabel, Select, MenuItem, FormControl, Stack,
} from '@mui/material';
import {Add} from "@mui/icons-material";

const GenericTable = ({ columns, data=[], actions, onAddClient, entityType='' }) => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [searchQuery, setSearchQuery] = useState('');
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedClient, setSelectedClient] = useState(null);
  const [selectedMonth, setSelectedMonth] = useState('');

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleSearch = (event) => {
    setSearchQuery(event.target.value);
    setPage(0); // Reset to first page on search
  };

  const handleMonthChange = (event) => {
    setSelectedMonth(event.target.value);
    setPage(0); // Reset to first page on search
  };

  const filteredData = data.filter((row) =>
      columns.some((column) => {
        let conditionB = true
        if(entityType === 'Cliente' && selectedMonth){
            const birthMonth = new Date(row.fechaNacimiento).getMonth(); // 0-11
            conditionB = birthMonth === parseInt(selectedMonth);
            console.log(birthMonth, parseInt(selectedMonth))
        }
        return row[column.id].toString().toLowerCase().includes(searchQuery.toLowerCase()) && conditionB
      })
  );

  const handleAddClient = () => {
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setSelectedClient(null);
  };

  return (
      <Paper>
        <Box sx={{padding: 2}}>
          <h3>Filtros</h3>
          <TextField
              fullWidth
              label="Término de busqueda"
              variant="outlined"
              value={searchQuery}
              onChange={handleSearch}
              sx={{mb: 2}}
          />
          {entityType === 'Cliente' && (
              <FormControl variant="outlined" fullWidth>
                <InputLabel id="month-select-label">Mes de cumpleaños</InputLabel>
                <Select
                    labelId="month-select-label"
                    value={selectedMonth}
                    onChange={handleMonthChange}
                    label="Mes de cumpleaños"
                >
                  <MenuItem value="">
                    <em>Limpiar filtro</em>
                  </MenuItem>
                  {Array.from({length: 12}, (_, index) => (
                      <MenuItem key={index} value={index}>
                        {new Date(0, index).toLocaleString('default', {month: 'long'})}
                      </MenuItem>
                  ))}
                </Select>
              </FormControl>
          )}

        </Box>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                {columns.map((column) => (
                    <TableCell key={column.id}>
                      <TableSortLabel>{column.label}</TableSortLabel>
                    </TableCell>
                ))}
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredData
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row, index) => (
                      <TableRow key={index}>
                        {columns.map((column) => (
                            <TableCell key={column.id}>{row[column.id]}</TableCell>
                        ))}
                        <TableCell>
                          <Stack spacing={2} direction="row">
                            {actions.map((action, idx) => (
                                <Button
                                    key={idx}
                                    variant="contained"
                                    color={action.color || 'primary'}
                                    onClick={() => {
                                      action.onClick(row);
                                      // Here you can set selectedClient if needed
                                    }}
                                >
                                  {action.icon}
                                  {action.label}
                                </Button>
                            ))}
                          </Stack>
                        </TableCell>
                      </TableRow>
                  ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={filteredData.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
  );
};

export default GenericTable;
