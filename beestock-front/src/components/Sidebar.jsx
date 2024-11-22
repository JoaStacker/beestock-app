import { Box, List, ListItem, ListItemText, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import { useGlobalContext } from "../context/GlobalContext";

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
        <Box sx={{ width: 250, backgroundColor: '#ff8c00', padding: 2, height: '100vh' }}>
            <List sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                {menus.map((menu) => (
                    menu.show && (
                        <ListItem key={menu.label} disablePadding>
                            <Button
                                component={Link}
                                to={menu.to}
                                fullWidth
                                sx={{
                                    backgroundColor: '#ffd699',
                                    color: '#333',
                                    borderRadius: 2,
                                    padding: '10px 20px',
                                    textAlign: 'left',
                                    fontWeight: 600,
                                    '&:hover': {
                                        backgroundColor: '#ffb84d',
                                    },
                                    '&.active': {
                                        backgroundColor: '#ff8c00',
                                        color: 'white',
                                    },
                                }}
                            >
                                {menu.label}
                            </Button>
                        </ListItem>
                    )
                ))}
            </List>
        </Box>
    );
};

export default Sidebar;
