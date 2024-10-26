import GenericTable from "../components/GenericTable";

const InvoicesPage = () => {
    const columns = [
        { id: 'numero', label: 'Numero' },
        { id: 'email', label: 'Email' },
        { id: 'horas', label: 'Horas' },
        { id: 'total', label: 'Total' },

      ];
    
      const data = [
        { numero: '#2', horas: 50, email: 'john@example.com', total: "$3500" },
        { numero: '#3', horas: 75, email: 'jane@example.com' , total: "$5000"},
        { numero: '#4', horas: 120, email: 'alice@example.com', total: "$8500" },
        { numero: '#2', horas: 50, email: 'john@example.com', total: "$3500" },
        { numero: '#3', horas: 75, email: 'jane@example.com' , total: "$5000"},
        { numero: '#4', horas: 120, email: 'alice@example.com', total: "$8500" },
        { numero: '#2', horas: 50, email: 'john@example.com', total: "$3500" },
        { numero: '#3', horas: 75, email: 'jane@example.com' , total: "$5000"},
        { numero: '#4', horas: 120, email: 'alice@example.com', total: "$8500" },
        
      ];
    
      const actions = [
        {
          label: 'Edit',
          onClick: (row) => alert(`Editing ${row.numero}`),
        },
        {
          label: 'Delete',
          onClick: (row) => alert(`Deleting ${row.numero}`),
          color: 'secondary',
        },
      ];
    
      return (
        <div>
          <h2>Facturas</h2>
          <GenericTable columns={columns} data={data} actions={actions} entityType="Factura"/>
        </div>
      );
};

export default InvoicesPage;
