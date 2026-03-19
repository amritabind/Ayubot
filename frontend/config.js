// Dynamic API Configuration
// This file determines the API URL based on the environment

const API_CONFIG = (() => {
    let apiBaseUrl;

    // Check if running locally (localhost or 127.0.0.1)
    const isLocalhost = window.location.hostname === 'localhost' || 
                        window.location.hostname === '127.0.0.1';

    if (isLocalhost) {
        // Local development
        apiBaseUrl = 'http://localhost:8080/api';
    } else if (window.location.hostname.includes('vercel.app')) {
        // Vercel deployment - use Render backend
        apiBaseUrl = 'https://ayubot.onrender.com/api';
    } else if (window.location.hostname.includes('render.com')) {
        // If both frontend and backend on Render
        apiBaseUrl = `${window.location.protocol}//${window.location.hostname}/api`;
    } else {
        // Other production deployments
        apiBaseUrl = `${window.location.protocol}//${window.location.hostname}/api`;
    }

    return {
        getApiUrl: () => apiBaseUrl,
        setApiUrl: (url) => { apiBaseUrl = url; }
    };
})();

// Log the API URL for debugging
console.log('API Base URL:', API_CONFIG.getApiUrl());

// Export for use in other files if using modules
if (typeof module !== 'undefined' && module.exports) {
    module.exports = API_CONFIG;
}
