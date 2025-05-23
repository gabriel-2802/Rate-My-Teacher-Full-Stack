import { useState } from 'react';
import axios from 'axios';

const RateTeacherPage = () => {
  const [form, setForm] = useState({
    name: '',
    subject: '',
    rating: '',
    comment: '',
  });

  const [status, setStatus] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatus('');

    try {
      await axios.post('http://localhost:8080/api/teachers/rate', form);
      setStatus('✅ Rating submitted successfully!');
      setForm({ name: '', subject: '', rating: '', comment: '' });
    } catch (err) {
      console.error(err);
      setStatus('❌ Error submitting rating.');
    }
  };

  return (
    <div style={{
      maxWidth: '600px',
      margin: '2rem auto',
      backgroundColor: '#f9f9f9',
      padding: '2rem',
      borderRadius: '12px',
      boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
      fontFamily: 'Poppins, sans-serif'
    }}>
      <h2 style={{ textAlign: 'center', marginBottom: '1.5rem' }}>📝 Rate a Teacher</h2>

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column' }}>
        <label style={{ marginBottom: '0.5rem' }}>
          Teacher Name:
          <input
            type="text"
            name="name"
            value={form.name}
            onChange={handleChange}
            required
            style={inputStyle}
          />
        </label>

        <label style={{ marginBottom: '0.5rem' }}>
          Subject:
          <input
            type="text"
            name="subject"
            value={form.subject}
            onChange={handleChange}
            required
            style={inputStyle}
          />
        </label>

        <label style={{ marginBottom: '0.5rem' }}>
          Rating (1–5):
          <input
            type="number"
            name="rating"
            min="1"
            max="5"
            value={form.rating}
            onChange={handleChange}
            required
            style={inputStyle}
          />
        </label>

        <label style={{ marginBottom: '0.5rem' }}>
          Comment (optional):
          <textarea
            name="comment"
            value={form.comment}
            onChange={handleChange}
            rows={4}
            style={{ ...inputStyle, resize: 'none' }}
          />
        </label>

        <button type="submit" style={buttonStyle}>
          Submit Rating
        </button>
      </form>

      {status && (
        <p style={{ textAlign: 'center', marginTop: '1rem', color: 'green', fontWeight: 'bold' }}>
          {status}
        </p>
      )}
    </div>
  );
};

const inputStyle = {
  width: '100%',
  padding: '0.75rem',
  marginTop: '0.3rem',
  marginBottom: '1rem',
  borderRadius: '6px',
  border: '1px solid #ccc',
  fontSize: '1rem'
};

const buttonStyle = {
  padding: '0.75rem',
  backgroundColor: '#4caf50',
  color: 'white',
  fontSize: '1rem',
  border: 'none',
  borderRadius: '6px',
  cursor: 'pointer',
  transition: 'background-color 0.2s ease'
};

export default RateTeacherPage;
