// Dynamic API Configuration
// This file determines the API URL based on the environment

const API_CONFIG = (() => {
    let apiBaseUrl;

    // Check if running locally (localhost or 127.0.0.1)
    const isLocalhost = window.location.hostname === 'localhost' || 
                        window.location.hostname === '127.0.0.1';

    if (isLocalhost && window.location.port === '3000') {
        // Local development with frontend dev server
        apiBaseUrl = 'http://localhost:8080/api';
    } else if (isLocalhost) {
        // Local development with direct file access
        apiBaseUrl = 'http://localhost:8080/api';
    } else {
        // Production deployment - use the same domain as the frontend
        // Assumes backend and frontend are on same domain (e.g., render.com)
        const protocol = window.location.protocol;
        const hostname = window.location.hostname;
        
        // Check if this is served from main domain
        if (hostname.includes('render.com')) {
            // Backend is served from a subdomain or path
            // Adjust this based on your Render deployment structure
            apiBaseUrl = `${protocol}//${hostname}/api`;
        } else {
            apiBaseUrl = `${protocol}//${hostname}:8080/api`;
        }
    }

    return {
        getApiUrl: () => apiBaseUrl,
        setApiUrl: (url) => { apiBaseUrl = url; }
    };
})();

// Export for use in other files if using modules
if (typeof module !== 'undefined' && module.exports) {
    module.exports = API_CONFIG;
}
