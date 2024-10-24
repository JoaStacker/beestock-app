import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { Box } from '@mui/material';
import Sidebar from './components/Sidebar';
import TopBar from './components/TopBar';
import SuppliersPage from './pages/SuppliersPage';
import SellsPage from './pages/SellsPage';
import InvoicesPage from './pages/InvoicesPage';
import IncidentsPage from './pages/IncidentsPage';
import EmployeesPage from './pages/EmployeesPage';
import ClientsPage from './pages/ClientsPage';
import LoginPage from './pages/LoginPage';

const App = () => {
  return (
    <Router>
      <TopBar />
      <Box sx={{ display: 'flex' }}>
        <Sidebar />
        <Box sx={{ flexGrow: 1, padding: 2 }}>
          <Routes>
            <Route path="/proveedores" element={<SuppliersPage />} />
            <Route path="/ventas" element={<SellsPage />} />
            <Route path="/facturas" element={<InvoicesPage />} />
            <Route path="/incidentes" element={<IncidentsPage />} />
            <Route path="/empleados" element={<EmployeesPage />} />
            <Route path="/clientes" element={<ClientsPage />} />
            <Route path="/login" element={<LoginPage />} /> {/* Default route */}
            <Route path="/" element={<ClientsPage />} /> {/* Default route */}
          </Routes>
        </Box>
      </Box>
    </Router>
  );
};

export default App;
