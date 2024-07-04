const apiUrl = 'http://localhost:8080';

function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch(`${apiUrl}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => {
        if (response.ok) {
            response.json().then(data => {
                console.log(data.jwt);
                localStorage.setItem('token', data.jwt);
                const role = data.role;
                document.getElementById('login-section').style.display = 'none';
                if (role === 'ADMIN') {
                    document.getElementById('admin-section').style.display = 'block';
                }
                document.getElementById('user-section').style.display = 'block';
            });
        } else {
            alert('Login failed!');
        }
    })
    .catch(error => console.error('Error:', error));
}

function fetchAllUsers() {
    fetch(`${apiUrl}/users/all`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => response.json())
    .then(users => {
        const userList = document.getElementById('user-list');
        userList.innerHTML = '';
        users.forEach(user => {
            const li = document.createElement('li');
            li.textContent = `${user.id}: ${user.username}`;
            userList.appendChild(li);
        });
    })
    .catch(error => console.error('Error:', error));
}

function updateUser() {
    const newUsername = document.getElementById('new-username').value;
    const newPassword = document.getElementById('new-password').value;
    const userId = 1; // Example user ID, replace with the actual user ID

    fetch(`${apiUrl}/users/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        body: JSON.stringify({ username: newUsername, password: newPassword })
    })
    .then(response => {
        if (response.ok) {
            alert('User updated successfully!');
        } else {
            alert('Failed to update user!');
        }
    })
    .catch(error => console.error('Error:', error));
}
