var requestOptions = {
    method: 'GET',
    redirect: 'follow'
};

let usersContainer = document.getElementById('users-container')
usersContainer.innerHTML = ''

fetch("http://localhost:8080/api/users", requestOptions)
    .then(response => response.json())
    .then(json => json.forEach(user => {

        let row = document.createElement('tr')

        let id = document.createElement('td')
        let username = document.createElement('td')
        let email = document.createElement('td')
        let action = document.createElement('td')
        let actionAdmin = document.createElement('td')

        id.textContent = user.id
        username.textContent = user.username
        email.textContent = user.email

        let actionHref = document.createElement('a')

        if (!user.isActive) {
            actionHref.href = '/admin/users/disable/' + user.id
            actionHref.textContent = 'Disable'
            actionHref.addEventListener('click', disable)
        }else {
            actionHref.href = '/admin/users/enable/' + user.id
            actionHref.textContent = 'Enable'
        }

        let actionAdminHref = document.createElement('a')

        let isAdmin = false

        user.roles.forEach(e => {
                if (e.name == 'ADMIN') {
                    isAdmin = true;
                }
            }
        )

        if (isAdmin) {
            actionAdminHref.href = '/admin/users/remove-admin/' + user.id
            actionAdminHref.textContent = 'Remove admin'
        }

        else {
            actionAdminHref.href = '/admin/users/make-admin/' + user.id
            actionAdminHref.textContent = 'Make admin'
        }

        action.appendChild(actionHref)
        actionAdmin.appendChild(actionAdminHref)

        row.appendChild(id)
        row.appendChild(username)
        row.appendChild(email)
        row.appendChild(action)
        row.appendChild(actionAdmin)

        usersContainer.appendChild(row)
    }))
    .catch(error => console.log('error', error));