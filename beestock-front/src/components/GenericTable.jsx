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
} from '@mui/material';

const GenericTable = ({ columns, data, actions, onAddClient }) => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [searchQuery, setSearchQuery] = useState('');

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

  const filteredData = data.filter((row) =>
    columns.some((column) =>
      row[column.id].toString().toLowerCase().includes(searchQuery.toLowerCase())
    )
  );

  return (
    <Paper>
      <Box sx={{ padding: 2 }}>
        <TextField
          fullWidth
          label="Search"
          variant="outlined"
          value={searchQuery}
          onChange={handleSearch}
          sx={{ mb: 2 }}
        />
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
                    {actions.map((action, idx) => (
                      <Button
                        key={idx}
                        variant="contained"
                        color={action.color || 'primary'}
                        onClick={() => action.onClick(row)}
                      >
                        {action.label}
                      </Button>
                    ))}
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
      <Box sx={{ padding: 2, textAlign: 'right' }}>
        <Button
          variant="contained"
          color="primary"
          onClick={onAddClient}
        >
          Add Client
        </Button>
      </Box>
    </Paper>
  );
};

export default GenericTable;
