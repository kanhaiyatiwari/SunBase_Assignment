let url="http://localhost:8888";
document.addEventListener('DOMContentLoaded', () => {
    // Initially show the login form
    showLogin();
});

function showLogin() {
    document.getElementById('login-form').style.display = 'block';
    document.getElementById('signup-form').style.display = 'none';
    document.querySelector('.tab-button.active').classList.remove('active');
    document.querySelector('.tab-button:nth-child(1)').classList.add('active');
}

function showSignup() {
    document.getElementById('signup-form').style.display = 'block';
    document.getElementById('login-form').style.display = 'none';
    document.querySelector('.tab-button.active').classList.remove('active');
    document.querySelector('.tab-button:nth-child(2)').classList.add('active');
}

async function login(event) {
    event.preventDefault();
    const loginId = document.getElementById('loginId').value;
    const loginPassword = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${url}/api/sunBase/auth/login`, {
            method: 'GET',
            headers: {
                'Authorization': 'Basic ' + btoa(loginId + ':' + loginPassword)
            }
        });

        if (response.ok) {
            const authToken = response.headers.get('Authorization');
            console.log(authToken);
            if (authToken) {
                localStorage.setItem('authToken', authToken);
                document.getElementById('login-message').innerText = 'Login successful! Token stored.';
                alert("Login successful! Token stored.")
                window.location.href = '../customers.html';
            } else {
                document.getElementById('login-message').innerText = 'Login successful, but no token received!';
            }
        } else {
            document.getElementById('login-message').innerText = 'Invalid credentials';
        }
    } catch (error) {
        document.getElementById('login-message').innerText = 'An error occurred';
    }
}

async function signup(event) {
    event.preventDefault();
    const signupId = document.getElementById('signupId').value;
    const signupPassword = document.getElementById('signupPassword').value;

    try {
        const response = await fetch(`${url}/api/sunBase/auth/singup`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ loginId: signupId, password: signupPassword })
        });

        if (response.ok) {
            document.getElementById('signup-message').innerText = 'Signup successful!';
            alert('Signup successful!');
            showLogin();
        } else {
            document.getElementById('signup-message').innerText = 'Signup failed';
        }
    } catch (error) {
        document.getElementById('signup-message').innerText = 'An error occurred';
    }
}
