const API_BASE = 'http://localhost:8080/api';

// Utility helper to wrap fetch requests with JWT tokens
async function fetchAPI(endpoint, options = {}) {
    const token = localStorage.getItem('token');
    
    // Set headers
    if (!options.headers) {
        options.headers = {};
    }
    
    if (token) {
        options.headers['Authorization'] = `Bearer ${token}`;
    }
    
    if (!(options.body instanceof FormData) && !options.headers['Content-Type']) {
        options.headers['Content-Type'] = 'application/json';
    }

    try {
        const response = await fetch(`${API_BASE}${endpoint}`, options);
        
        if (response.status === 401 || response.status === 403) {
            // Unauthorized or Forbidden - clear token and send to login
            console.warn('Authentication error. Redirecting to login...');
            localStorage.clear();
            if (!window.location.pathname.endsWith('login.html') && !window.location.pathname.endsWith('register.html')) {
                window.location.href = 'login.html';
            }
            throw new Error('Unauthorized or Session Expired');
        }

        // Return parsed json if possible
        const text = await response.text();
        if (text) {
            try {
                return JSON.parse(text);
            } catch (e) {
                return text; // Return plain text if not JSON
            }
        }
        return null;
    } catch (error) {
        console.error(`API Call failed [${endpoint}]:`, error);
        throw error;
    }
}

// Check if user is logged in and matches the page's required role
function checkAuth(allowedRoles = []) {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role'); // e.g. ROLE_ADMIN, ROLE_OPERATOR, ROLE_COMMUTER
    const username = localStorage.getItem('username');

    if (!token || !role) {
        localStorage.clear();
        window.location.href = 'login.html';
        return null;
    }

    if (allowedRoles.length > 0 && !allowedRoles.includes(role)) {
        // Role mismatch - redirect to their proper dashboard
        alert('Access denied. Redirecting to your dashboard...');
        redirectDashboard(role);
        return null;
    }

    // Populate user profile info in UI if elements exist
    document.addEventListener('DOMContentLoaded', () => {
        const nameEl = document.getElementById('sidebar-username');
        const roleEl = document.getElementById('sidebar-role');
        const avatarEl = document.getElementById('sidebar-avatar');

        if (nameEl) nameEl.textContent = username || 'User';
        if (roleEl) roleEl.textContent = role.replace('ROLE_', '');
        if (avatarEl && username) avatarEl.textContent = username.charAt(0).toUpperCase();
    });

    return { token, role, username };
}

function redirectDashboard(role) {
    if (role === 'ROLE_ADMIN') {
        window.location.href = 'admin.html';
    } else if (role === 'ROLE_OPERATOR') {
        window.location.href = 'operator.html';
    } else {
        window.location.href = 'commuter.html';
    }
}

function logout() {
    localStorage.clear();
    window.location.href = 'login.html';
}

// Formatting utilities
function formatDateTime(isoString) {
    if (!isoString) return 'N/A';
    const date = new Date(isoString);
    return date.toLocaleString([], { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' });
}
