# Customer Management Application

This application is designed for managing customer information through a comprehensive CRUD interface. It leverages MySQL for database management, Spring Boot for backend services, and HTML/CSS/JavaScript for the frontend. The application also includes JWT authentication and a synchronization feature to fetch and update customer data from an external API using RestTemplate.

## Features
- **admin**: Admin can reggister and login 
- **View Customers**: Display a list of all customers. 
- **Add Customer**: a admin Create new customer records.
- **Edit Customer**: Update existing customer information.
- **Delete Customer**: Remove customer records.
- **Search**: Filter customers by various criteria.
- **Data Synchronization**: Sync customer data with an external API.

## Technologies Used

- **Database**: MySQL
- **Backend**: Spring Boot
- **Frontend**: HTML, CSS, JavaScript
- **Authentication**: JSON Web Tokens (JWT),Spring security

## Getting Started

Follow these steps to set up and run the project locally.

### Prerequisites

- JDK
- MySQL
- STS or any Java-supported IDE

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/kanhaiyatiwari/SunBase_Assignment.git
    ```

2. Create a MySQL database:

    ```sql
    CREATE DATABASE sunbase;
    ```

3. Import the project into your IDE.

4. Update the `application.properties` file with your MySQL database credentials:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/sunbase
    spring.datasource.username=root
    spring.datasource.password=yourPassword
    ```

5. Run the project.

## Endpoints

- **POST :-Admin** `/api/sunBase/auth/signup` - rigister as admin.
  - **Body**:
    ```json
   {
  "loginId": "admin123",
  "password": "Admin@123"
}

    ```
- **GET:- Admin Login** `Endpoint: GET /auth/login
  - 
    Description: Authenticate an admin using JWT./using basic auth
    ```
- **GET** `/api/sunBase/getCustomers` - Retrieve all customers, requiring JWT authentication. 
- **GET** `/api/sunBase/getCustomer/{id}` - Retrieve a customer by ID, requiring JWT authentication.
- **PUT** `/api/sunBase/updateCustomer/{id}` - Update a customer by ID, requiring JWT authentication.
- **DELETE** `/api/sunBase/deleteCustomer/{id}` - Delete a customer by ID, requiring JWT authentication.
- **POST** `/api/sunBase/sync-customers` - Synchronize customer data from a remote API, requiring JWT authentication.

## Screenshots

### Customer List

![Customer List](screenshots/customer_list.png)

### Add Customer

![Add Customer](screenshots/add_customer.png)

### Edit Customer

![Edit Customer](screenshots/edit_customer.png)

### Signup

![Signup](screenshots/signup.png)

## Contact

For any inquiries or issues, please contact:

- **Name**: Kanhaiya Tiwari
- **Email**: [tiwarikanhaiya149@gmail.com.com](mailto:tiwarikanhaiya149@gmail.com)

---

Feel free to contribute by submitting issues or pull requests!
