import React, { useEffect, useState } from 'react';
import { getAllTeachers } from '../services/teacherService';
import { useLocation } from 'react-router-dom';

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

function TeachersListPage() {
  const [teachers, setTeachers] = useState([]);
  const [loading, setLoading] = useState(true);
  const query = useQuery();
  const search = query.get("search")?.toLowerCase();

  useEffect(() => {
    const fetchTeachers = async () => {
      try {
        const allTeachers = await getAllTeachers();
        const filtered = search
          ? allTeachers.filter((t) =>
              t.universityName?.toLowerCase().includes(search)
            )
          : allTeachers;
        setTeachers(filtered);
      } catch (error) {
        console.error('Failed to load teachers', error);
      } finally {
        setLoading(false);
      }
    };

    fetchTeachers();
  }, [search]);

  if (loading) return <p>Loading...</p>;

  return (
    <div style={{ padding: '2rem', fontFamily: 'Inter, sans-serif' }}>
      <h2 style={{ textAlign: 'center', fontSize: '2rem', marginBottom: '2rem' }}>
        Teachers
      </h2>
      {teachers.length === 0 ? (
        <p style={{ textAlign: 'center' }}>No teachers found.</p>
      ) : (
        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
          gap: '1.5rem'
        }}>
          {teachers.map((teacher) => (
            <div key={teacher.id} style={{
              border: '1px solid #ccc',
              borderRadius: '12px',
              padding: '1rem',
              boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
              backgroundColor: '#fff'
            }}>
              {teacher.teacherPicture ? (
                <img
                  src={`data:image/png;base64,${teacher.teacherPicture}`}
                  alt="Teacher"
                  style={{ width: '100%', borderRadius: '8px', height: '200px', objectFit: 'cover' }}
                />
              ) : (
                <div style={{ height: '200px', backgroundColor: '#eee', borderRadius: '8px' }}></div>
              )}
              <h4 style={{ marginTop: '1rem' }}>{teacher.name}</h4>
              <p style={{ margin: '0.25rem 0' }}>Rating: {teacher.rating ?? 'N/A'}</p>
              <p style={{ margin: '0.25rem 0', color: '#555' }}>{teacher.universityName}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default TeachersListPage;
