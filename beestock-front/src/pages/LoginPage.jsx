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
  const navigate = useNavigate(); // Hook for navigation

  // Regex for email validation
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

  const handleSubmit = (e) => {
    e.preventDefault();
    setEmailError('');
    setPasswordError('');

    // Validate email
    if (!emailRegex.test(email)) {
      setEmailError('Please enter a valid email address.');
      return;
    }

    // Validate password (at least 6 characters)
    if (password.length < 6) {
      setPasswordError('Password must be at least 6 characters long.');
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
      <h2>Login</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.formGroup}>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={styles.input}
          />
          {emailError && <div style={styles.error}>{emailError}</div>}
        </div>
        <div style={styles.formGroup}>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={styles.input}
          />
          {passwordError && <div style={styles.error}>{passwordError}</div>}
        </div>
        <button type="submit" style={styles.button}>Login</button>
      </form>
    </div>
  );
};

const styles = {
  container: {
    maxWidth: '400px',
    margin: 'auto',
    padding: '20px',
    border: '1px solid #ccc',
    borderRadius: '5px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
  },
  formGroup: {
    marginBottom: '15px',
  },
  input: {
    padding: '10px',
    borderRadius: '4px',
    border: '1px solid #ccc',
  },
  button: {
    padding: '10px',
    borderRadius: '4px',
    border: 'none',
    backgroundColor: '#007BFF',
    color: 'white',
    cursor: 'pointer',
  },
  error: {
    color: 'red',
    fontSize: '12px',
    marginTop: '5px',
  },
};

export default LoginPage;
