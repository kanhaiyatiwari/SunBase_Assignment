const API_URL = 'http://localhost:8888/api/sunBase';

document.addEventListener('DOMContentLoaded', getCustomers);

async function getCustomers() {
    const token = localStorage.getItem('jwtToken');
  
    if (!token) {
      window.location.href = 'index.html';
      return;
    }
  
    try {
      const response = await fetch(`${API_URL}/getCustomers`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
  
      if (response.ok) {
        const customers = await response.json();
        const customerTableBody = document.getElementById('customers');
        customerTableBody.innerHTML = '';
  
        customers.forEach(customer => {
          const tr = document.createElement('tr');
          tr.innerHTML = `
            <td>${customer.first_name}</td>
            <td>${customer.last_name}</td>
            <td>${customer.street}</td>
            <td>${customer.address}</td>
            <td>${customer.city}</td>
            <td>${customer.state}</td>
            <td>${customer.email}</td>
            <td>${customer.phone}</td>
            <td><button class="edit-button" onclick="editCustomer('${customer.uuid}')">Edit</button></td>
          `;
          customerTableBody.appendChild(tr);
        });
      } else {
        alert('Failed to fetch customers!');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  }
  





  async function syncCustomers() {
    const token = localStorage.getItem('jwtToken');
  
    if (!token) {
      window.location.href = 'index.html';
      return;
    }
  
    try {
      const response = await fetch(`${API_URL}/sync-customers`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
  
      const contentType = response.headers.get('content-type');
      if (contentType && contentType.includes('application/json')) {
        const customers = await response.json();
        // updateCustomerTable(customers);
        alert('Customers synced successfully!');
        location.reload();
      } else {
        const text = await response.text();
        console.log('Error:', text);
        alert('Customers synced successfully');
        location.reload();
      }
    } catch (error) {
      console.error('Error:', error);
    }
  }



  
    function editCustomer(uuid) {
        window.location.href = `edit-customer.html?id=${uuid}`;
      }


function logout() {
  localStorage.removeItem('jwtToken');
  window.location.href = 'index.html';
}



