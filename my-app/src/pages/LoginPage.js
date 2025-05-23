import React, { useState } from 'react';
import { login } from '../services/authService';
import { useNavigate, Link } from 'react-router-dom';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login(username, password);
      navigate('/');
    } catch (err) {
      setError('Login failed');
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h1 style={styles.title}>Login</h1>


        <div style={styles.dividerContainer}>
          <hr style={styles.hr} />
          <span style={styles.dividerText}>Or login with email</span>
          <hr style={styles.hr} />
        </div>

        {error && <p style={styles.error}>{error}</p>}

        <form onSubmit={handleSubmit} style={styles.form}>
          <input
            type="text"
            placeholder="Email"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            style={styles.input}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={styles.input}
            required
          />
          <div style={styles.forgot}>Forgot Password?</div>
          <button type="submit" style={styles.loginBtn}>Continue</button>
        </form>

        <p style={styles.disclaimer}>
          Rate My Teachers is designed for students in Romania and is governed by Romanian educational and data protection standards.
        </p>

        <p style={styles.signupPrompt}>
          Don’t have an account? <Link to="/signup" style={styles.signupLink}>Sign Up</Link>
        </p>
      </div>
    </div>
  );
}

const styles = {
  container: {
    minHeight: '90vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    background: '#fff'
  },
  card: {
    width: '100%',
    maxWidth: '400px',
    padding: '2rem',
    borderRadius: '12px',
    boxShadow: '0 0 20px rgba(0,0,0,0.1)',
    textAlign: 'center',
    backgroundColor: '#fff',
  },
  title: {
    fontSize: '2rem',
    marginBottom: '1.5rem',
    fontWeight: 700,
  },

  dividerContainer: {
    display: 'flex',
    alignItems: 'center',
    textAlign: 'center',
    marginBottom: '1.5rem',
  },
  hr: {
    flex: 1,
    border: 'none',
    borderTop: '1px solid #ccc'
  },
  dividerText: {
    padding: '0 10px',
    fontSize: '0.9rem',
    color: '#555'
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '1rem'
  },
  input: {
    padding: '0.75rem 1rem',
    borderRadius: '8px',
    border: '1px solid #ccc',
    fontSize: '1rem',
  },
  forgot: {
    textAlign: 'right',
    fontSize: '0.9rem',
    color: '#007bff',
    cursor: 'pointer'
  },
  loginBtn: {
    padding: '0.75rem',
    borderRadius: '24px',
    backgroundColor: '#000',
    color: '#fff',
    fontWeight: 'bold',
    border: 'none',
    cursor: 'pointer'
  },
  error: {
    color: 'red',
    marginBottom: '0.5rem'
  },
  disclaimer: {
    marginTop: '1rem',
    fontSize: '0.75rem',
    color: '#555'
  },
  signupPrompt: {
    marginTop: '1rem',
    fontSize: '0.95rem'
  },
  signupLink: {
    color: '#007bff',
    fontWeight: 'bold',
    textDecoration: 'none'
  }
};

export default LoginPage;
