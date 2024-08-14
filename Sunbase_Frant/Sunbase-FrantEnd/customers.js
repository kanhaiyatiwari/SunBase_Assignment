const API_URL = 'http://localhost:8888/api/sunBase';

let currentPage = 0;
const token = localStorage.getItem('authToken');

document.addEventListener('DOMContentLoaded', () => {
    if (!token) {
        window.location.href = './Authnetication/index.html';
    } else {
        getCustomers();
    }
});

async function getCustomers(searchBy = 'name', searchTerm = '', sortBy = 'first_name', sortOrder = 'asc', page = 0) {
    const url = new URL(`${API_URL}/search`);
    url.searchParams.append('searchBy', searchBy);
    url.searchParams.append('searchTerm', searchTerm);
    url.searchParams.append('sort', sortBy);
    url.searchParams.append('dir', sortOrder);
    url.searchParams.append('page', page);
    url.searchParams.append('size', 4);
console.log(url);
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
                    <td><button class="delete-button" onclick="deleteCustomer('${customer.uuid}')">Delete</button></td>
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
    const searchBy = document.getElementById('searchBy').value;
    const searchTerm = document.getElementById('searchTerm').value;
    const sortBy = document.getElementById('sortBy').value;
    
    const sortOrder = document.getElementById('sortOrder').value;

    getCustomers(searchBy, searchTerm, sortBy, sortOrder, currentPage);
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
    try {
        const response = await fetch(`${API_URL}/sync-customers`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert('Customers synced successfully!');
            getCustomers();
        } else {
            alert('Failed to sync customers!');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

function openAddCustomerPopup() {
    document.getElementById('addCustomerPopup').style.display = 'flex';
}

function closeAddCustomerPopup() {
    document.getElementById('addCustomerPopup').style.display = 'none';
}

async function addCustomer() {
    const newCustomer = {
        first_name: document.getElementById('newFirstName').value,
        last_name: document.getElementById('newLastName').value,
        street: document.getElementById('newStreet').value,
        address: document.getElementById('newAddress').value,
        city: document.getElementById('newCity').value,
        state: document.getElementById('newState').value,
        email: document.getElementById('newEmail').value,
        phone: document.getElementById('newPhone').value
    };

    try {
        const response = await fetch(`${API_URL}/addCustomer`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(newCustomer)
        });

        if (response.ok) {
            alert('Customer added successfully!');
            closeAddCustomerPopup();
            getCustomers();
        } else {
            alert('Failed to add customer!');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function deleteCustomer(uuid) {
    try {
        const response = await fetch(`${API_URL}/deleteCustomer/${uuid}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert('Customer deleted successfully!');
            getCustomers();
        } else {
            alert('Failed to delete customer!');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

function editCustomer(uuid) {
    window.location.href = `edit-customer.html?id=${uuid}`;
}

function logout() {
    localStorage.removeItem('authToken');
    window.location.href = './Authnetication/index.html';
}
