// src/components/Footer.js
const Footer = () => {
  return (
    <footer style={{ padding: '1rem', backgroundColor: '#f5f5f5', textAlign: 'center', marginTop: '2rem' }}>
      <p>© {new Date().getFullYear()} Rate My Teacher. All rights reserved.</p>
    </footer>
  );
};

export default Footer;
