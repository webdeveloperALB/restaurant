const API_BASE = '/api';
let currentUser = null;

document.addEventListener('DOMContentLoaded', () => {
    checkAuth();
});

function checkAuth() {
    const token = localStorage.getItem('authToken');
    const authContainer = document.getElementById('auth-container');
    const appContainer = document.getElementById('app-container');

    if (token) {
        validateToken(token).then(isValid => {
            if (isValid) {
                authContainer.style.display = 'none';
                appContainer.style.display = 'flex';
                initApp();
            } else {
                localStorage.removeItem('authToken');
                localStorage.removeItem('user');
                authContainer.style.display = 'flex';
                appContainer.style.display = 'none';
            }
        });
    } else {
        authContainer.style.display = 'flex';
        appContainer.style.display = 'none';
    }
}

async function validateToken(token) {
    try {
        const response = await fetch(`${API_BASE}/auth/validate`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const data = await response.json();
            currentUser = data;
            localStorage.setItem('user', JSON.stringify(data));
            return true;
        }
        return false;
    } catch (error) {
        console.error('Token validation error:', error);
        return false;
    }
}

function showRegisterForm(event) {
    event.preventDefault();
    document.getElementById('login-form').classList.remove('active');
    document.getElementById('register-form').classList.add('active');
}

function showLoginForm(event) {
    event.preventDefault();
    document.getElementById('register-form').classList.remove('active');
    document.getElementById('login-form').classList.add('active');
}

async function handleLogin(event) {
    event.preventDefault();

    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                fjalekalimi: password
            })
        });

        const data = await response.json();

        if (response.ok && data.token) {
            localStorage.setItem('authToken', data.token);
            localStorage.setItem('user', JSON.stringify(data));
            currentUser = data;

            showToast('Kyçja u krye me sukses!', 'success');

            setTimeout(() => {
                document.getElementById('auth-container').style.display = 'none';
                document.getElementById('app-container').style.display = 'flex';
                initApp();
            }, 1000);
        } else {
            showToast(data.mesazh || 'Email ose fjalëkalim i gabuar', 'error');
        }
    } catch (error) {
        console.error('Login error:', error);
        showToast('Gabim në lidhje me serverin', 'error');
    }
}

async function handleRegister(event) {
    event.preventDefault();

    const formData = {
        emri: document.getElementById('register-emri').value,
        mbiemri: document.getElementById('register-mbiemri').value,
        email: document.getElementById('register-email').value,
        fjalekalimi: document.getElementById('register-password').value,
        telefoni: document.getElementById('register-telefoni').value,
        dataLindjes: document.getElementById('register-data-lindjes').value || null,
        adresa: document.getElementById('register-adresa').value,
        roli: document.getElementById('register-roli').value
    };

    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        const data = await response.json();

        if (response.ok && data.token) {
            localStorage.setItem('authToken', data.token);
            localStorage.setItem('user', JSON.stringify(data));
            currentUser = data;

            showToast('Regjistrimi u krye me sukses!', 'success');

            setTimeout(() => {
                document.getElementById('auth-container').style.display = 'none';
                document.getElementById('app-container').style.display = 'flex';
                initApp();
            }, 1000);
        } else {
            showToast(data.mesazh || 'Regjistrimi dështoi', 'error');
        }
    } catch (error) {
        console.error('Register error:', error);
        showToast('Gabim në lidhje me serverin', 'error');
    }
}

function handleLogout() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    currentUser = null;

    showToast('Jeni larguar me sukses', 'success');

    setTimeout(() => {
        document.getElementById('auth-container').style.display = 'flex';
        document.getElementById('app-container').style.display = 'none';

        document.getElementById('login-form').reset();
        document.getElementById('register-form').reset();
    }, 1000);
}

function initApp() {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user) {
        const initials = (user.emri.charAt(0) + user.mbiemri.charAt(0)).toUpperCase();
        document.getElementById('user-initials').textContent = initials;
        document.getElementById('user-display-name').textContent = `${user.emri} ${user.mbiemri}`;
        document.getElementById('user-display-role').textContent = user.roli;
        document.getElementById('welcome-name').textContent = user.emri;
    }

    initNavigation();
    loadDashboard();
}

function initNavigation() {
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const page = item.getAttribute('data-page');
            switchPage(page);
        });
    });
}

function switchPage(pageName) {
    document.querySelectorAll('.page').forEach(page => {
        page.style.display = 'none';
        page.classList.remove('active');
    });

    document.querySelectorAll('.nav-item').forEach(item => {
        item.classList.remove('active');
    });

    const targetPage = document.getElementById(`${pageName}-page`);
    if (targetPage) {
        targetPage.style.display = 'block';
        targetPage.classList.add('active');
    }

    const targetNav = document.querySelector(`[data-page="${pageName}"]`);
    if (targetNav) {
        targetNav.classList.add('active');
    }

    const pageTitles = {
        'dashboard': 'Paneli Kryesor',
        'students': 'Studentët',
        'courses': 'Kurset',
        'attendance': 'Prania',
        'assignments': 'Detyrat',
        'grades': 'Notat',
        'messages': 'Mesazhet',
        'announcements': 'Njoftimet',
        'settings': 'Cilësimet'
    };

    document.getElementById('page-title').textContent = pageTitles[pageName] || 'Paneli Kryesor';

    switch(pageName) {
        case 'dashboard':
            loadDashboard();
            break;
        case 'students':
            loadStudents();
            break;
        case 'courses':
            loadCourses();
            break;
    }
}

async function loadDashboard() {
    document.getElementById('stat-courses').textContent = '0';
    document.getElementById('stat-assignments').textContent = '0';
    document.getElementById('stat-attendance').textContent = '--';
    document.getElementById('stat-gpa').textContent = '--';
}

async function loadStudents() {
}

async function loadCourses() {
}

async function apiRequest(endpoint, options = {}) {
    const token = localStorage.getItem('authToken');

    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    };

    const mergedOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...options.headers
        }
    };

    try {
        const response = await fetch(`${API_BASE}${endpoint}`, mergedOptions);

        if (response.status === 401) {
            handleLogout();
            return null;
        }

        return await response.json();
    } catch (error) {
        console.error('API Request error:', error);
        throw error;
    }
}

function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast show ${type}`;

    setTimeout(() => {
        toast.className = 'toast';
    }, 3000);
}
