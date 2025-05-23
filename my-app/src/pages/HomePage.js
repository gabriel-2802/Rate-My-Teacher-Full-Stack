import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const [search, setSearch] = useState('');
  const navigate = useNavigate();

  const handleSearch = () => {
    if (search.trim() !== '') {
      navigate(`/teachers?search=${encodeURIComponent(search)}`);
    }
  };

  return (
    <div style={{ fontFamily: 'Inter, sans-serif' }}>
      {/* Hero Section */}
      <div
        style={{
          backgroundImage: 'url("/assets/homepage.png")',
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          minHeight: '100vh',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          padding: '2rem',
        }}
      >
        <div style={{
          backgroundColor: 'black',
          color: 'white',
          padding: '0.5rem 2rem',
          borderRadius: '5px',
          fontSize: '2rem',
          fontWeight: 'bold',
          marginBottom: '1rem'
        }}>
          RATE MY <span style={{ color: '#61dafb' }}>PROFESSORS</span>
        </div>

        <h2 style={{
          color: 'white',
          fontSize: '1.5rem',
          fontWeight: 500,
          textAlign: 'center',
          marginBottom: '2rem'
        }}>
          Enter your school to get started
        </h2>

        <div style={{ display: 'flex', alignItems: 'center' }}>
          <input
            type="text"
            placeholder="Your school"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            style={{
              padding: '0.75rem 1rem',
              fontSize: '1rem',
              borderRadius: '25px',
              border: 'none',
              width: '300px',
              marginRight: '1rem'
            }}
          />
          <button
            onClick={handleSearch}
            style={{
              padding: '0.75rem 1.5rem',
              fontSize: '1rem',
              borderRadius: '25px',
              backgroundColor: '#000',
              color: 'white',
              border: 'none',
              cursor: 'pointer'
            }}
          >
            Search
          </button>


        </div>

        <p style={{
          color: 'white',
          marginTop: '1rem',
          fontSize: '0.95rem',
          textDecoration: 'underline',
          cursor: 'pointer'
        }}>
          I’d like to look up a professor by name
        </p>

        <div style={{ marginTop: '2rem' }}>
          <button
            onClick={() => navigate('/teachers')}
            style={{
              margin: '0.5rem',
              padding: '0.75rem 1.5rem',
              fontSize: '1rem',
              borderRadius: '8px',
              backgroundColor: '#4caf50',
              color: 'white',
              border: 'none',
              cursor: 'pointer'
            }}
          >
            View Teachers
          </button>

          <button
            onClick={() => navigate('/rate')}
            style={{
              margin: '0.5rem',
              padding: '0.75rem 1.5rem',
              fontSize: '1rem',
              borderRadius: '8px',
              backgroundColor: '#2196f3',
              color: 'white',
              border: 'none',
              cursor: 'pointer'
            }}
          >
            Rate a Teacher
          </button>
        </div>
      </div>

      {/* Info Section - "Join the RMP Family" */}
      <div style={{
        backgroundColor: '#f6fbff',
        textAlign: 'center',
        padding: '4rem 2rem'
      }}>
        <h2 style={{ fontSize: '2rem', fontWeight: 700 }}>
          Speak Up, Stand Out
        </h2>
        <p style={{ fontSize: '1.25rem', marginBottom: '3rem' }}>
          Your voice matters. Rate your professors today.
        </p>

        <div style={{
          display: 'flex',
          justifyContent: 'center',
          gap: '2rem',
          flexWrap: 'wrap'
        }}>
          {[
            { text: '', img: '/assets/pencil.png' },
            { text: '', img: '/assets/anonymous.png' },
            { text: '', img: '/assets/thumbs.png' }
          ].map((item, idx) => (
            <div key={idx} style={{ maxWidth: 220 }}>
              <img src={item.img} alt="" style={{ width: '100%', marginBottom: '1rem' }} />
              <p style={{ fontWeight: 600 }}>{item.text}</p>
            </div>
          ))}
        </div>

        <button
        onClick={() => navigate('/signup')}
        style={{
          marginTop: '3rem',
          padding: '0.75rem 2rem',
          borderRadius: '20px',
          backgroundColor: '#000',
          color: 'white',
          fontWeight: 'bold',
          fontSize: '1rem',
          border: 'none',
          cursor: 'pointer'
        }}
      >
        Sign up now!
      </button>

      </div>
    </div>
  );
};

export default HomePage;
