import { AppBar, Toolbar, Typography } from '@mui/material';

const TopBar = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6">Beestock</Typography>
      </Toolbar>
    </AppBar>
  );
};

export default TopBar;
