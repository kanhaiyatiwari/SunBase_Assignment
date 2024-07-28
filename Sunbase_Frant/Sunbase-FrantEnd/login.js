const API_URL = 'http://localhost:8888/api/sunBase';

async function login() {
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;

  try {
    const response = await fetch(`${API_URL}/loginCustomer`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, password })
    });

    if (response.ok) {
      const token = await response.text();
      localStorage.setItem('jwtToken', token);
      window.location.href = 'customers.html';
    } else {
      alert('Login failed!');
    }
  } catch (error) {
    console.error('Error:', error);
  }
}


function Signup() {
  // localStorage.removeItem('jwtToken');
  window.location.href = 'add-customer.html';
}
