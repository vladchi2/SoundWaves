const authProvider = {
    login: ({ username, password }) => {
        const token = 'Basic ' + btoa(`${username}:${password}`);
        const request = new Request('http://localhost:8080/login', {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': token
            }),
        });
        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
            })
            .then(auth => {
                localStorage.setItem('auth', token);
                localStorage.setItem('role', username);
            })

    },
    checkError: error => Promise.resolve(),
    checkAuth: params => localStorage.getItem('auth') ?
        Promise.resolve() : Promise.reject(),
    logout: () => {
        localStorage.removeItem('auth');
        return Promise.resolve();
    },
    getIdentity: () => Promise.resolve(),
    getPermissions: () => {
        const role = localStorage.getItem('role');
        console.log(role);
        return Promise.resolve(role);
    },
};

export default authProvider;
