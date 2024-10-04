import GenericTable from "../components/GenericTable";
import IncidentList from "../components/IncidentList";

const IncidentsPage = () => {
    const columns = [
        { id: 'name', label: 'Name' },
        { id: 'age', label: 'Age' },
        { id: 'email', label: 'Email' },
      ];
    
      const data = [
        { name: 'John Doe', age: 30, email: 'john@example.com' },
        { name: 'Jane Smith', age: 25, email: 'jane@example.com' },
        { name: 'Alice Johnson', age: 35, email: 'alice@example.com' },
      ];
    
      const actions = [
        {
          label: 'Edit',
          onClick: (row) => alert(`Editing ${row.name}`),
        },
        {
          label: 'Delete',
          onClick: (row) => alert(`Deleting ${row.name}`),
          color: 'secondary',
        },
      ];

      const incidents = [
        {
          title: 'AWS RDS Database no responde',
          description: 'Se reporta la falta de respuesta del servicio de base de datos.',
          actions: [
            { label: 'Asignar Tecnico', id: 'assign_technician' },
            { label: 'Cerrar Ticket', id: 'close_ticket' },
          ],
        },
        {
          title: 'Azure DEVOPS ssh keys caducadas',
          description: 'Se ha reportado una SSH Key obsoleta para un desarrollador.',
          actions: [
            { label: 'Asignar DevOps', id: 'assign_devops' },
            { label: 'Cerrar Ticket', id: 'close_ticket' },
          ],
        },
        {
          title: 'Problemas de Internet',
          description: 'Los usuarios reportan lentitud en la conexión a Internet.',
          actions: [
            { label: 'Reiniciar Router', id: 'restart_router' },
            { label: 'Cerrar Ticket', id: 'close_ticket' },
          ],
        },
      ];
    
      const handleAction = (incident, action) => {
        alert(`Acción '${action.label}' realizada para: ${incident.title}`);
      };
    
      return (
        <div>
          <h2>Incidentes</h2>
          <GenericTable columns={columns} data={data} actions={actions} entityType="Incidente" />
          <IncidentList incidents={incidents} onAction={handleAction}></IncidentList>
        </div>
      );
};

export default IncidentsPage;
