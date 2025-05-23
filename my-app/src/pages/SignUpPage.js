import React, { useState } from 'react';
import { register } from '../services/authService';
import { useNavigate, Link } from 'react-router-dom';

const SignUpPage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: '',
    email: '',
    password: ''
  });
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      await register(form.username, form.password, form.email);
      setSuccess(true);
      setTimeout(() => navigate('/login'), 2000);
    } catch (err) {
      setError(err.toString());
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h1 style={styles.title}><span style={{ fontWeight: 700 }}></span> Sign Up</h1>


        <div style={styles.dividerContainer}>
          <hr style={styles.hr} />
          <span style={styles.dividerText}>Or sign up with email</span>
          <hr style={styles.hr} />
        </div>

        {success && <p style={styles.success}>Registration successful! Redirecting to login...</p>}
        {error && <p style={styles.error}>{error}</p>}

        <form onSubmit={handleSubmit} style={styles.form}>
          <input
            type="text"
            name="username"
            placeholder="Username"
            value={form.username}
            onChange={handleChange}
            required
            style={styles.input}
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={form.email}
            onChange={handleChange}
            required
            style={styles.input}
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={form.password}
            onChange={handleChange}
            required
            style={styles.input}
          />
          <button type="submit" style={styles.loginBtn}>Continue</button>
        </form>

        <p style={styles.disclaimer}>
          Rate My Teachers is designed for students in Romania and follows Romanian educational standards and data protection regulations.
        </p>

        <p style={styles.signupPrompt}>
          Already have an account? <Link to="/login" style={styles.link}>Login</Link>
        </p>
      </div>
    </div>
  );
};

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
    marginBottom: '0.5rem',
  },
  subtitle: {
    marginBottom: '1.5rem',
    fontSize: '0.9rem',
    color: '#555'
  },
  link: {
    color: '#007bff',
    fontWeight: 'bold',
    cursor: 'pointer'
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
  loginBtn: {
    padding: '0.75rem',
    borderRadius: '24px',
    backgroundColor: '#000',
    color: '#fff',
    fontWeight: 'bold',
    border: 'none',
    cursor: 'pointer'
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
  error: {
    color: 'red',
    fontSize: '0.9rem'
  },
  success: {
    color: 'green',
    fontSize: '0.9rem'
  }
};

export default SignUpPage;
