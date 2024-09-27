import { Box, List, ListItem, ListItemText } from '@mui/material';
import { Link } from 'react-router-dom';

const Sidebar = () => {
  return (
    <Box sx={{ width: 250 }}>
      <List>
        {['Clients', 'Suppliers', 'Sells', 'Incidents', 'Invoices', 'Employees'].map((text) => (
          <ListItem button key={text} component={Link} to={`/${text.replace(' ', '').toLowerCase()}`}>
            <ListItemText primary={text} />
          </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default Sidebar;
