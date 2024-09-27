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

const App = () => {
  return (
    <Router>
      <TopBar />
      <Box sx={{ display: 'flex' }}>
        <Sidebar />
        <Box sx={{ flexGrow: 1, padding: 2 }}>
          <Routes>
            <Route path="/suppliers" element={<SuppliersPage />} />
            <Route path="/sells" element={<SellsPage />} />
            <Route path="/invoices" element={<InvoicesPage />} />
            <Route path="/incidents" element={<IncidentsPage />} />
            <Route path="/employees" element={<EmployeesPage />} />
            <Route path="/clients" element={<ClientsPage />} />
            <Route path="/" element={<ClientsPage />} /> {/* Default route */}
          </Routes>
        </Box>
      </Box>
    </Router>
  );
};

export default App;
