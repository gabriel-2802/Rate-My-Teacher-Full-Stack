import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <header style={{ display: 'flex', justifyContent: 'flex-end', padding: '1rem 2rem', backgroundColor: '#fffef0' }}>
      <Link to="/login" style={{ marginRight: '1rem', textDecoration: 'none', color: '#000', fontWeight: 'bold' }}>
        Log In
      </Link>
      <Link to="/signup">
        <button style={{
          backgroundColor: '#000',
          color: '#fff',
          padding: '0.5rem 1rem',
          border: 'none',
          borderRadius: '999px',
          fontWeight: 'bold',
          cursor: 'pointer'
        }}>Sign Up</button>
      </Link>
    </header>
  );
};

export default Header;
