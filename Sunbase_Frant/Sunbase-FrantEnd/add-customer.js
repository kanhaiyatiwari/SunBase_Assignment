const API_URL = 'http://localhost:8888/api/sunBase';

document.getElementById('add-customer-form').addEventListener('submit', async function(event) {
  event.preventDefault();

  const formData = new FormData(event.target);
  const data = Object.fromEntries(formData.entries());

  const token = localStorage.getItem('jwtToken');

  if (!token) {
    window.location.href = 'index.html';
    return;
  }

  try {
    const response = await fetch(`${API_URL}/addCustomer`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });

    if (response.ok) {
      alert('Customer added successfully!');
      window.location.href = 'index.html'; 
    } else {
      const errorData = await response.json();
      alert(`Failed to add customer: ${errorData.message}`);
    }
  } catch (error) {
    console.error('Error:', error);
    alert('An error occurred while adding the customer.');
  }
});

function Login() {
  // localStorage.removeItem('jwtToken');
  window.location.href = 'index.html';
}
