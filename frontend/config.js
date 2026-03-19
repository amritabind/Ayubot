// Dynamic API Configuration
// This file determines the API URL based on the environment

const API_CONFIG = (() => {
    let apiBaseUrl;

    // Check if running locally (localhost or 127.0.0.1)
    const hostname = window.location.hostname;
    const protocol = window.location.protocol;
    
    console.log('Frontend URL:', window.location.href);
    console.log('Hostname:', hostname);

    if (hostname === 'localhost' || hostname === '127.0.0.1') {
        // Local development
        apiBaseUrl = 'http://localhost:8080/api';
        console.log('Using local development API URL');
    } else if (hostname.includes('vercel.app')) {
        // Production: Vercel frontend with Render backend
        apiBaseUrl = 'https://ayubot.onrender.com/api';
        console.log('Using Render backend from Vercel frontend');
    } else if (hostname.includes('render.com')) {
        // Both frontend and backend on Render
        apiBaseUrl = `${protocol}//${hostname}/api`;
        console.log('Using Render backend');
    } else {
        // Other production deployments
        apiBaseUrl = `${protocol}//${hostname}/api`;
        console.log('Using same-domain backend');
    }

    return {
        getApiUrl: () => {
            console.log('API URL:', apiBaseUrl);
            return apiBaseUrl;
        },
        setApiUrl: (url) => { 
            apiBaseUrl = url;
            console.log('API URL updated to:', apiBaseUrl);
        }
    };
})();

// Log the API URL immediately
console.log('=== AyuBot Config Initialized ===');
console.log('API Base URL:', API_CONFIG.getApiUrl());
console.log('================================');

// Export for use in other files if using modules
if (typeof module !== 'undefined' && module.exports) {
    module.exports = API_CONFIG;
}
