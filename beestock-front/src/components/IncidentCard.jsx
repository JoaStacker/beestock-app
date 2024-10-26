import React from 'react';
import { Card, CardContent, CardActions, Typography, Button } from '@mui/material';

const IncidentCard = ({ incident, onAction }) => {
  return (
    <Card sx={{ margin: 2 }}>
      <CardContent>
        <Typography variant="h5" component="div">
          {incident.title}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {incident.description}
        </Typography>
      </CardContent>
      <CardActions>
        {incident.actions.map((action, index) => (
          <Button
            key={index}
            size="small"
            color="primary"
            onClick={() => onAction(incident, action)}
          >
            {action.label}
          </Button>
        ))}
      </CardActions>
    </Card>
  );
};

export default IncidentCard;
