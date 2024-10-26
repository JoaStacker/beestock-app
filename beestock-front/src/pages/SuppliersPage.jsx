import GenericTable from "../components/GenericTable";

const SuppliersPage = () => {
    const columns = [
        { id: 'name', label: 'Name' },
        { id: 'direccion', label: 'Direccion' },
        { id: 'email', label: 'Email' },
      ];
    
      const data = [
        { name: 'Amazon Web Services', direccion: "www.aws.com", email: 'john@support.com' },
        { name: 'Microsoft Azure', direccion: "www.azure.com", email: 'jane@support.com' },
        { name: 'Vultr', direccion: "www.vultr.com", email: 'alice@support.com' },
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
          <h2>Proveedores</h2>
          <GenericTable columns={columns} data={data} actions={actions} entityType="Proveedor"/>
        </div>
      );
};

export default SuppliersPage;
