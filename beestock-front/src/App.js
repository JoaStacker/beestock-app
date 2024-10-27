import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import SuppliersPage from './pages/SuppliersPage';
import SellsPage from './pages/SellsPage';
import IncidentsPage from './pages/IncidentsPage';
import EmployeesPage from './pages/EmployeesPage';
import ClientsPage from './pages/ClientsPage';
import LoginPage from './pages/LoginPage';
import {useGlobalContext} from "./context/GlobalContext";
import NotFoundPage from "./pages/NotFoundPage";
import BasePage from "./pages/BasePage";
import HomePage from "./pages/HomePage";

const LoginRoute = ({children, isAuthenticated}) => {
  return isAuthenticated ? <Navigate to="/home" /> : children;
}

const PrivateRoute = ({ children, isAuthenticated }) => {
  return isAuthenticated ? children : <Navigate to="/login" />;
};

const App = () => {
  const { globalState, updateGlobalState } = useGlobalContext();

  return (
      <Router>
          <Routes>
            <Route path="/notfound" element={<NotFoundPage />} />
            <Route path="/incidentes" element={
              <PrivateRoute
                  isAuthenticated={globalState.isAuthenticated}
                  permission={globalState.user?.adminProveedores}
              >
                <BasePage>
                  <IncidentsPage />
                </BasePage>
              </PrivateRoute>
            } />
            <Route path="/proveedores" element={
              <PrivateRoute
                  isAuthenticated={globalState.isAuthenticated}
                  permission={globalState.user?.adminProveedores}
              >
                <BasePage>
                  <SuppliersPage />
                </BasePage>
              </PrivateRoute>
            } />
            <Route path="/empleados" element={
              <PrivateRoute
                  isAuthenticated={globalState.isAuthenticated}
                  permission={globalState.user?.adminRRHH}
              >
                <BasePage>
                  <EmployeesPage />
                </BasePage>
              </PrivateRoute>
            } />
            <Route path="/ventas" element={
              <PrivateRoute
                  isAuthenticated={globalState.isAuthenticated}
                  permission={globalState.user?.adminVentas}
              >
                <BasePage>
                  <SellsPage />
                </BasePage>
              </PrivateRoute>
            } />
            <Route path="/clientes" element={
              <PrivateRoute
                  isAuthenticated={globalState.isAuthenticated}
                  permission={globalState.user?.adminClientes}
              >
                <BasePage>
                  <ClientsPage />
                </BasePage>
              </PrivateRoute>
            } />
            <Route path="/login" element={
              <LoginRoute
                  isAuthenticated={globalState.isAuthenticated}
              >
                <LoginPage />
              </LoginRoute>
            } />
            <Route path="/home" element={
              <PrivateRoute
                  isAuthenticated={globalState.isAuthenticated}
              >
                <BasePage>
                  <HomePage />
                </BasePage>
              </PrivateRoute>
            } />
            <Route path="/*" element={<Navigate to="/login" />} />
          </Routes>
      </Router>
  );
};

export default App;
