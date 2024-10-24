import GenericTable from "../components/GenericTable";

const ClientsPage = () => {
    const columns = [
        { id: 'name', label: 'Name' },
        { id: 'direccion', label: 'Direccion' },
        { id: 'email', label: 'Email' },
      ];
    
      const data = [
        { name: 'NORTOE S.A.', direccion: "Lavalle 2413", email: 'john@example.com' },
        { name: 'TECHIN', direccion: "Lavalle 2413", email: 'jane@example.com' },
        { name: 'MERCADO LIBRE', direccion: "Lavalle 2413", email: 'alice@example.com' },
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
    
      return (
        <div>
          <h2>Clientes</h2>
          <GenericTable columns={columns} data={data} actions={actions} entityType="Cliente"/>
        </div>
      );
};

export default ClientsPage;