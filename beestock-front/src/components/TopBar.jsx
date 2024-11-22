import {AppBar, Box, Button, Checkbox, FormControlLabel, IconButton, Stack, Toolbar, Typography} from '@mui/material';
import {useGlobalContext} from "../context/GlobalContext";
import {useNavigate} from "react-router-dom";
import {Logout} from "@mui/icons-material";

const TopBar = () => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const navigate = useNavigate(); // Hook for navigation

    const handleCloseSession = () => {
        updateGlobalState({
            user: null,
            isAuthenticated: false,
        })
        localStorage.clear()
        navigate('/login');
    }

  return (
      <AppBar position="static">
          <Toolbar>
              {/* Left side - Beestock title */}
              <Typography variant="h6" sx={{ flexGrow: 1 }}>
                  Beestock
              </Typography>

              {/* Right side - User info and Logout button */}
              <Box display="flex" alignItems="center">
                  <Stack>
                      <Typography  variant="subtitle2">
                          Permisos
                      </Typography>
                      <Stack direction="row" spacing={1} sx={{ marginLeft: 2 }}>
                          <FormControlLabel
                              control={<Checkbox checked={!!globalState.user.adminVentas} disabled />}
                              label={"Ventas"}
                          />
                          <FormControlLabel
                              control={<Checkbox checked={!!globalState.user.adminRRHH} disabled />}
                              label={"RRHH"}
                          />
                          <FormControlLabel
                              control={<Checkbox checked={!!globalState.user.adminProveedores} disabled />}
                              label={"Proveedores"}
                          />
                          <FormControlLabel
                              control={<Checkbox checked={!!globalState.user.adminClientes} disabled />}
                              label={"Clientes"}
                          />
                      </Stack>
                  </Stack>
                  <Stack spacing={0}>
                      <Typography variant="subtitle1">
                          Usuario: {globalState.user.empleado.nombre} {globalState.user.empleado.apellido}
                      </Typography>
                      <Typography variant="subtitle1">
                          Puesto: {globalState.user.empleado.puesto}
                      </Typography>
                  </Stack>
                  <IconButton color="inherit" onClick={handleCloseSession} aria-label="Cerrar sesión" sx={{ marginLeft: 2 }}>
                      <Logout />
                  </IconButton>
              </Box>
          </Toolbar>
      </AppBar>
  );
};

export default TopBar;
