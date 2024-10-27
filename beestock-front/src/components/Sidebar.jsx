import { Box, List, ListItem, ListItemText } from '@mui/material';
import { Link } from 'react-router-dom';
import {useGlobalContext} from "../context/GlobalContext";

const Sidebar = () => {
    const { globalState, updateGlobalState } = useGlobalContext();
    const menus = [
        { to: '/proveedores', label: 'Proveedores', show: globalState.user?.adminProveedores },
        { to: '/incidentes', label: 'Incidentes', show: globalState.user?.adminProveedores },
        { to: '/clientes', label: 'Clientes', show: globalState.user?.adminClientes },
        { to: '/ventas', label: 'Ventas', show: globalState.user?.adminVentas },
        { to: '/empleados', label: 'Empleados', show: globalState.user?.adminRRHH },
    ];

    return (
    <Box sx={{ width: 250 }}>
      <List>
        {menus.map((menu) => (
            menu.show &&
            <ListItem button key={menu.label} component={Link} to={menu.to}>
                <ListItemText primary={menu.label} />
            </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default Sidebar;
