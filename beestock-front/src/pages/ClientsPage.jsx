import { useEffect, useState } from "react";
import EditClientPopup from "../components/PopUps/EditClientPopup";
import { getClients, deleteClient } from '../services/clientService';

const ClientsPage = () => {
    const [clients, setClients] = useState([]);
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [selectedClient, setSelectedClient] = useState(null);

    useEffect(() => {
          getClients().then((data) => {
            console.log(data)
            const clientsData = data.data.clientes;
            setClients(data);
          }).catch((error) => console.log(error));
    }, []);

    const handleAddClient = () => {
        setSelectedClient(null);
        setIsPopupOpen(true);
    };

    const handleEditClient = (client) => {
        setSelectedClient(client);
        setIsPopupOpen(true);
    };

    const handleDeleteClient = async (clientId) => {
        await deleteClient(clientId);
        setClients(clients.filter(client => client.id !== clientId));
    };

    const handleClientUpdated = async () => {
        const data = await getClients(); // Refresh clients list
        setClients(data);
    };
      
      return (
        <div>
          <h2>Clientes</h2>
          <button onClick={handleAddClient}>Añadir cliente</button>
          <ul>
              {clients.map(client => (
                  <li key={client.id}>
                      {client.name} ({client.email})
                      <button onClick={() => handleEditClient(client)}>Edit</button>
                      <button onClick={() => handleDeleteClient(client.id)}>Delete</button>
                  </li>
              ))}
          </ul>
          <EditClientPopup
                isOpen={isPopupOpen}
                onClose={() => setIsPopupOpen(false)}
                client={selectedClient}
                onClientUpdated={handleClientUpdated}
            />
        </div>
      );
};

export default ClientsPage;