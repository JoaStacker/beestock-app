import { Box, List, ListItem, ListItemText } from '@mui/material';
import { Link } from 'react-router-dom';

const Sidebar = () => {
  return (
    <Box sx={{ width: 250 }}>
      <List>
        {['Clientes', 'Proveedores', 'Ventas', 'Incidentes', 'Facturas', 'Empleados'].map((text) => (
          <ListItem button key={text} component={Link} to={`/${text.replace(' ', '').toLowerCase()}`}>
            <ListItemText primary={text} />
          </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default Sidebar;
