const API_URL = 'http://localhost:8888/api/sunBase';

let currentPage = 0;

document.addEventListener('DOMContentLoaded', () => {
    getCustomers();
});

async function getCustomers(searchTerm = '', city = '', state = '', sortOrder = 'asc', page = 0) {
    console.log(page);
    const token = localStorage.getItem('jwtToken');
  
    if (!token) {
        window.location.href = 'index.html';
        return;
    }
  
    const url = new URL(`${API_URL}/search`);
    url.searchParams.append('searchTerm', searchTerm);
    if (city) url.searchParams.append('city', city);
    if (state) url.searchParams.append('state', state);
    url.searchParams.append('sort', 'first_name');
    url.searchParams.append('dir', sortOrder);
    url.searchParams.append('page', page);
    url.searchParams.append('size', 4);

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const customersPage = await response.json();
            const customers = customersPage.content;
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

            document.getElementById('prevPageButton').disabled = customersPage.first;
            document.getElementById('nextPageButton').disabled = customersPage.last;
        } else {
            alert('Failed to fetch customers!');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

function applyFilters() {
    const searchTerm = document.getElementById('searchTerm').value;
    const city = document.getElementById('cityFilter').value;
    const state = document.getElementById('stateFilter').value;
    const sortOrder = document.getElementById('sortOrder').value;

    
    getCustomers(searchTerm, city, state, sortOrder, currentPage);
}

function nextPage() {
    currentPage++;
    applyFilters();
}

function prevPage() {
    if (currentPage > 0) {
        currentPage--;
        applyFilters();
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
            alert('Customers synced successfully!');
            getCustomers();
        } else {
            const text = await response.text();
            console.log('Error:', text);
            alert('Customers synced successfully');
            getCustomers();
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
