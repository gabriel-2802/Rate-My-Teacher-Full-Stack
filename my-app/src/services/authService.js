import api from './api';

export const login = async (username, password) => {
  try {
    const response = await api.post('/api/auth/login', {
      username,
      password,
    });
    localStorage.setItem('token', response.data.access_token);
    return response.data;
  } catch (error) {
    console.error('Login failed:', error.response?.data || error.message);
    throw error;
  }
};

export const register = async (username, password, email) => {
  try {
    const response = await api.post('/api/auth/register', {
      username,
      password,
      email
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Registration failed';
  }
};

export const logout = () => {
  localStorage.removeItem('token');
};
