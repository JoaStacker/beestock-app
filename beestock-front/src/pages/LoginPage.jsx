import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useGlobalContext } from "../context/GlobalContext";
import { loginUser } from "../services/authService";

const LoginPage = () => {
  const { globalState, updateGlobalState } = useGlobalContext();

  const [email, setEmail] = useState('pedro.lopez@gmail.com');
  const [password, setPassword] = useState('123456');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const navigate = useNavigate();

  // Regex for email validation
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

  const handleSubmit = (e) => {
    e.preventDefault();
    setEmailError('');
    setPasswordError('');

    if (!emailRegex.test(email)) {
      setEmailError('Por favor, ingresa un correo electrónico válido.');
      return;
    }

    if (password.length < 6) {
      setPasswordError('La contraseña debe tener al menos 6 caracteres.');
      return;
    }

    updateGlobalState({ loadingPage: true });

    let payload = {
      email: email,
      password: password
    };

    loginUser(payload, updateGlobalState)
      .then((res) => {
        updateGlobalState({ loadingPage: false });

        if (res.error) {
          updateGlobalState({
            openSnackbar: true,
            snackbarSeverity: "error",
            snackbarMessage: res.message
          });
          return;
        }

        updateGlobalState({
          openSnackbar: true,
          snackbarSeverity: "success",
          snackbarMessage: res.message
        });

        const userData = res.data;

        updateGlobalState({
          isAuthenticated: true,
          user: userData,
        });

        localStorage.setItem("user", JSON.stringify(userData));
        navigate('/home');
      })
      .catch((error) => {
        updateGlobalState({ loadingPage: false });
        console.error(error);
      });
  };

  return (
    <div style={styles.container}>
      <div style={styles.logoContainer}>
        {/* <img src="../../public/logo_empresa.png" alt="Logo" style={styles.logo} /> */}
      </div>
      <h2 style={styles.title}>Bienvenido de nuevo</h2>
      <p style={styles.subtitle}>Inicia sesión para continuar</p>
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.formGroup}>
          <label htmlFor="email" style={styles.label}>Correo Electrónico</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={styles.input}
            placeholder="Ingrese su correo"
          />
          {emailError && <div style={styles.error}>{emailError}</div>}
        </div>
        <div style={styles.formGroup}>
          <label htmlFor="password" style={styles.label}>Contraseña</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={styles.input}
            placeholder="Ingrese su contraseña"
          />
          {passwordError && <div style={styles.error}>{passwordError}</div>}
        </div>
        <button type="submit" style={styles.button}>Iniciar Sesión</button>
      </form>
    </div>
  );
};

const styles = {
  container: {
    maxWidth: '420px',
    margin: '50px auto',
    padding: '30px',
    borderRadius: '8px',
    boxShadow: '0 4px 20px rgba(0, 0, 0, 0.1)',
    backgroundColor: '#fff7e6', // Color base: un amarillo suave
  },
  logoContainer: {
    textAlign: 'center',
    marginBottom: '20px',
  },
  logo: {
    width: '120px', // Ajusta el tamaño del logo
    height: 'auto',
  },
  title: {
    textAlign: 'center',
    color: '#ff8c00', // Naranja vibrante
    fontSize: '28px',
    marginBottom: '10px',
  },
  subtitle: {
    textAlign: 'center',
    color: '#333',
    fontSize: '14px',
    marginBottom: '30px',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '20px',
  },
  formGroup: {
    display: 'flex',
    flexDirection: 'column',
  },
  label: {
    fontSize: '14px',
    fontWeight: '600',
    color: '#ff8c00',
    marginBottom: '8px',
  },
  input: {
    padding: '12px',
    borderRadius: '6px',
    border: '1px solid #ffd699', // Amarillo suave
    fontSize: '14px',
    color: '#333',
    marginBottom: '8px',
    outline: 'none',
    transition: 'border-color 0.3s ease',
  },
  inputFocus: {
    borderColor: '#ff8c00', // Naranja brillante al enfocar el input
  },
  button: {
    padding: '12px',
    borderRadius: '6px',
    border: 'none',
    backgroundColor: '#ff8c00',
    color: 'white',
    fontSize: '16px',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease',
  },
  buttonHover: {
    backgroundColor: '#ff5722', // Naranja más oscuro al pasar el ratón
  },
  error: {
    color: '#d9534f',
    fontSize: '12px',
    marginTop: '5px',
  },
};

export default LoginPage;
