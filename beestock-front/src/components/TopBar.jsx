import { AppBar, Box, Checkbox, FormControlLabel, IconButton, Stack, Toolbar, Typography } from '@mui/material';
import { useGlobalContext } from "../context/GlobalContext";
import { useNavigate } from "react-router-dom";
import { Logout } from "@mui/icons-material";

const TopBar = () => {
  const { globalState, updateGlobalState } = useGlobalContext();
  const navigate = useNavigate(); // Hook for navigation

  const handleCloseSession = () => {
    updateGlobalState({
      user: null,
      isAuthenticated: false,
    });
    localStorage.clear();
    navigate('/login');
  };

  return (
    <AppBar position="static" sx={{ backgroundColor: '#ff8c00', boxShadow: '0 4px 10px rgba(0, 0, 0, 0.1)' }}>
      <Toolbar>
        <div >
            <img src="./logo_empresa.png" alt="Logo" style={{width: '100px'}} />
        </div>

        {/* Left side - Beestock title */}
        <Typography variant="h6" sx={{ marginLeft: 2, flexGrow: 1, color: 'white' }}>
          Sistema de Gestion 
        </Typography>

        {/* Right side - User info and Logout button */}
        <Box display="flex" alignItems="center">
          <Stack>
            <Typography variant="subtitle2" sx={{ color: 'white' }}>
              Permisos
            </Typography>
            <Stack direction="row" spacing={1} sx={{ marginLeft: 2 }}>
              <FormControlLabel
                control={<Checkbox checked={!!globalState.user.adminVentas} disabled />}
                label={"Ventas"}
                sx={{
                  color: 'white',
                  '& .MuiCheckbox-root': { color: 'white' }, // Change checkbox color
                  '& .MuiFormControlLabel-label': { color: 'white' }, // Change label color
                }}
              />
              <FormControlLabel
                control={<Checkbox checked={!!globalState.user.adminRRHH} disabled />}
                label={"RRHH"}
                sx={{
                  color: 'white',
                  '& .MuiCheckbox-root': { color: 'white' },
                  '& .MuiFormControlLabel-label': { color: 'white' },
                }}
              />
              <FormControlLabel
                control={<Checkbox checked={!!globalState.user.adminProveedores} disabled />}
                label={"Proveedores"}
                sx={{
                  color: 'white',
                  '& .MuiCheckbox-root': { color: 'white' },
                  '& .MuiFormControlLabel-label': { color: 'white' },
                }}
              />
              <FormControlLabel
                control={<Checkbox checked={!!globalState.user.adminClientes} disabled />}
                label={"Clientes"}
                sx={{
                  color: 'white',
                  '& .MuiCheckbox-root': { color: 'white' },
                  '& .MuiFormControlLabel-label': { color: 'white' },
                }}
              />
            </Stack>
          </Stack>

          <Stack spacing={0} sx={{ marginLeft: 2 }}>
            <Typography variant="subtitle1" sx={{ color: 'white' }}>
              Usuario: {globalState.user.empleado.nombre} {globalState.user.empleado.apellido}
            </Typography>
            <Typography variant="subtitle1" sx={{ color: 'white' }}>
              Puesto: {globalState.user.empleado.puesto}
            </Typography>
          </Stack>

          <IconButton color="inherit" onClick={handleCloseSession} aria-label="Cerrar sesión" sx={{ marginLeft: 2 }}>
            <Logout sx={{ color: 'white' }} />
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
  );
};


export default TopBar;
