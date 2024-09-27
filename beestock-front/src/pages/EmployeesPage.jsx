import GenericTable from "../components/GenericTable";

const EmployeesPage = () => {
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
    
      return (
        <div>
          <h2>Empleados</h2>
          <GenericTable columns={columns} data={data} actions={actions} />
        </div>
      );
};

export default EmployeesPage;
