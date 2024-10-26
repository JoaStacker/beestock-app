import React from 'react';
import { Box } from '@mui/material';
import IncidentCard from './IncidentCard';

const IncidentList = ({ incidents, onAction }) => {
  return (
    <Box sx={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center' }}>
      {incidents.map((incident, index) => (
        <IncidentCard key={index} incident={incident} onAction={onAction} />
      ))}
    </Box>
  );
};

export default IncidentList;
