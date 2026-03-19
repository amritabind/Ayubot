// AyuBot Theme Switcher
// This file handles light/dark theme switching across all pages

class ThemeManager {
  constructor() {
    this.currentTheme = localStorage.getItem('ayubot-theme') || 'light';
    this.init();
  }

  init() {
    // Apply saved theme on page load
    this.applyTheme(this.currentTheme);
    
    // Create theme toggle button if it doesn't exist
    this.createThemeToggle();
  }

  applyTheme(theme) {
    const root = document.documentElement;
    
    if (theme === 'dark') {
      root.style.setProperty('--bg-primary', '#1a1a2e');
      root.style.setProperty('--bg-secondary', '#16213e');
      root.style.setProperty('--bg-tertiary', '#0f3460');
      root.style.setProperty('--text-primary', '#ffffff');
      root.style.setProperty('--text-secondary', '#e0e0e0');
      root.style.setProperty('--text-muted', '#a0a0a0');
      root.style.setProperty('--card-bg', '#16213e');
      root.style.setProperty('--card-border', '#2d3561');
      root.style.setProperty('--input-bg', '#0f3460');
      root.style.setProperty('--input-border', '#2d3561');
      root.style.setProperty('--shadow', 'rgba(0, 0, 0, 0.5)');
      root.style.setProperty('--gradient-start', '#1a1a2e');
      root.style.setProperty('--gradient-end', '#16213e');
      root.style.setProperty('--card-shadow', '0 10px 30px rgba(0, 0, 0, 0.5)');
      document.body.classList.add('dark-theme');
      document.body.classList.remove('light-theme');
    } else {
      root.style.setProperty('--bg-primary', '#ffffff');
      root.style.setProperty('--bg-secondary', '#f8f9fa');
      root.style.setProperty('--bg-tertiary', '#e9ecef');
      root.style.setProperty('--text-primary', '#333333');
      root.style.setProperty('--text-secondary', '#555555');
      root.style.setProperty('--text-muted', '#999999');
      root.style.setProperty('--card-bg', '#ffffff');
      root.style.setProperty('--card-border', '#e0e0e0');
      root.style.setProperty('--input-bg', '#ffffff');
      root.style.setProperty('--input-border', '#dddddd');
      root.style.setProperty('--shadow', 'rgba(0, 0, 0, 0.1)');
      root.style.setProperty('--gradient-start', '#667eea');
      root.style.setProperty('--gradient-end', '#764ba2');
      root.style.setProperty('--card-shadow', '0 10px 30px rgba(0, 0, 0, 0.1)');
      document.body.classList.add('light-theme');
      document.body.classList.remove('dark-theme');
    }
    
    this.currentTheme = theme;
    localStorage.setItem('ayubot-theme', theme);
  }

  toggleTheme() {
    const newTheme = this.currentTheme === 'light' ? 'dark' : 'light';
    this.applyTheme(newTheme);
    this.updateToggleButton();
  }

  createThemeToggle() {
    // Check if toggle already exists
    if (document.getElementById('theme-toggle-btn')) return;

    const toggleBtn = document.createElement('button');
    toggleBtn.id = 'theme-toggle-btn';
    toggleBtn.className = 'theme-toggle';
    toggleBtn.innerHTML = this.currentTheme === 'light' 
      ? '<i class="fas fa-moon"></i>' 
      : '<i class="fas fa-sun"></i>';
    toggleBtn.onclick = () => this.toggleTheme();
    toggleBtn.title = 'Toggle Theme';

    // Add styles
    const style = document.createElement('style');
    style.textContent = `
      .theme-toggle {
        position: fixed;
        bottom: 30px;
        right: 30px;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background: linear-gradient(45deg, #667eea, #764ba2);
        border: none;
        color: white;
        font-size: 20px;
        cursor: pointer;
        box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        transition: all 0.3s ease;
        z-index: 9999;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      
      .theme-toggle:hover {
        transform: scale(1.1);
        box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
      }
      
      .theme-toggle:active {
        transform: scale(0.95);
      }

      /* Dark theme specific adjustments */
      body.dark-theme {
        background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%) !important;
      }

      .dark-theme .card,
      .dark-theme .login-card,
      .dark-theme .register-card,
      .dark-theme .upload-card,
      .dark-theme .chat-container {
        background: var(--card-bg) !important;
        color: var(--text-primary) !important;
        border-color: var(--card-border) !important;
      }

      .dark-theme .card-body,
      .dark-theme .card-header:not([style*="gradient"]) {
        background: var(--card-bg) !important;
        color: var(--text-primary) !important;
      }

      .dark-theme .form-control,
      .dark-theme .form-select {
        background: var(--input-bg) !important;
        color: var(--text-primary) !important;
        border-color: var(--input-border) !important;
      }

      .dark-theme .form-control::placeholder {
        color: var(--text-muted) !important;
      }

      .dark-theme .text-muted,
      .dark-theme .subtitle {
        color: var(--text-muted) !important;
      }

      .dark-theme .text-dark,
      .dark-theme h1, .dark-theme h2, .dark-theme h3, 
      .dark-theme h4, .dark-theme h5, .dark-theme h6,
      .dark-theme p:not(.text-white):not([style*="color"]) {
        color: var(--text-primary) !important;
      }

      .dark-theme .navbar {
        background: var(--bg-secondary) !important;
      }

      .dark-theme .sidebar {
        background: var(--bg-tertiary) !important;
      }

      .dark-theme .alert {
        background: var(--bg-tertiary) !important;
        color: var(--text-primary) !important;
        border-color: var(--card-border) !important;
      }

      .dark-theme .modal-content {
        background: var(--card-bg) !important;
        color: var(--text-primary) !important;
      }

      .dark-theme table {
        color: var(--text-primary) !important;
      }

      .dark-theme .bg-white:not(.btn):not(.badge) {
        background: var(--card-bg) !important;
      }

      .dark-theme .bg-light {
        background: var(--bg-tertiary) !important;
      }

      .dark-theme #control-panel,
      .dark-theme #response-panel {
        background: rgba(22, 33, 62, 0.97) !important;
        backdrop-filter: blur(20px);
      }

      .dark-theme .selected-display {
        background: var(--bg-tertiary) !important;
        color: var(--text-primary) !important;
      }

      .dark-theme .messages {
        background: var(--bg-tertiary) !important;
      }

      .dark-theme .message.ai {
        background: var(--card-bg) !important;
        color: var(--text-primary) !important;
        border-color: var(--card-border) !important;
      }

      .dark-theme .input-area {
        background: var(--card-bg) !important;
        border-top-color: var(--card-border) !important;
      }

      .dark-theme footer {
        background: rgba(22, 33, 62, 0.9) !important;
        color: rgba(255,255,255,0.95) !important;
      }

      .dark-theme .stat-card {
        background: linear-gradient(45deg, #2d3561, #1a1a2e) !important;
      }

      .dark-theme .report-card,
      .dark-theme .upload-section,
      .dark-theme .stat-box {
        background: var(--card-bg) !important;
        color: var(--text-primary) !important;
        border-color: var(--card-border) !important;
      }

      .dark-theme .filter-tab {
        background: var(--card-bg) !important;
        color: var(--text-primary) !important;
        border-color: var(--card-border) !important;
      }

      .dark-theme .filter-tab:hover {
        border-color: #667eea !important;
      }

      /* Ensure feature cards use dark card background + readable text in dark mode */
      .dark-theme .feature-card {
        background: var(--card-bg) !important;
        color: var(--text-primary) !important;
        border-color: var(--card-border) !important;
        box-shadow: var(--card-shadow) !important;
      }      
      .dark-theme .feature-card .feature-title,
      .dark-theme .feature-card .feature-text,
      .dark-theme .feature-card .feature-icon {
        color: var(--text-primary) !important;
      }

      /* Smooth transitions */
      body, .card, .form-control, .form-select, .navbar, .sidebar,
      #control-panel, #response-panel, .selected-display, .message {
        transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease !important;
      }
      /* Ensure preformatted/extracted text uses theme variables */
      pre, .extracted-text pre, .modal-body pre {
        color: var(--text-primary) !important;
        background: var(--bg-secondary) !important;
      }
    `;
    
    document.head.appendChild(style);
    document.body.appendChild(toggleBtn);
    // Also create support widget
    this.createSupportWidget();
  }

  createSupportWidget() {
    if (document.getElementById('support-btn')) return;

    // Styles for support widget
    const supportStyle = document.createElement('style');
    supportStyle.textContent = `
      /* language button removed (site uses static labels) */
      #support-btn {
        position: fixed;
        bottom: 100px;
        right: 30px;
        width: 56px;
        height: 56px;
        border-radius: 50%;
        background: linear-gradient(45deg, #00C9A7, #00B894);
        color: white;
        border: none;
        cursor: pointer;
        font-size: 20px;
        display: flex;
        align-items: center;
        justify-content: center;
        box-shadow: 0 6px 18px rgba(0,0,0,0.15);
        z-index: 10000;
      }
      #support-modal .modal-content { border-radius: 12px; }
    `;
    document.head.appendChild(supportStyle);

    const supportBtn = document.createElement('button');
    supportBtn.id = 'support-btn';
    supportBtn.title = 'Contact Support';
    supportBtn.innerHTML = '<i class="fas fa-life-ring"></i>';
    document.body.appendChild(supportBtn);

    // Modal HTML
    const modalHtml = `
      <div class="modal fade" id="support-modal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-md modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header" style="background:linear-gradient(135deg,#00C9A7,#00B894);color:white;border-radius:12px 12px 0 0;">
              <h5 class="modal-title">Support</h5>
              <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
              <form id="supportForm">
                <div class="mb-3">
                  <label class="form-label">Your Name</label>
                  <input id="supportName" class="form-control" placeholder="Optional" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Your Email</label>
                  <input id="supportEmail" type="email" class="form-control" placeholder="Optional - for replies" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Message</label>
                  <textarea id="supportMessage" class="form-control" rows="5" required placeholder="Describe your issue or question"></textarea>
                </div>
                <div class="d-flex justify-content-end">
                  <button type="button" class="btn btn-secondary me-2" data-bs-dismiss="modal">Cancel</button>
                  <button type="submit" class="btn btn-primary">Send</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    `;
    const wrapper = document.createElement('div');
    wrapper.innerHTML = modalHtml;
    document.body.appendChild(wrapper);

    // Modal instance
    let supportModal;
    try {
      supportModal = new bootstrap.Modal(document.getElementById('support-modal'));
    } catch (e) {
      console.warn('Bootstrap modal not available yet');
    }

    supportBtn.addEventListener('click', () => {
      if (supportModal) supportModal.show();
      else document.getElementById('support-modal').classList.add('show');
    });

    // language toggle removed; site uses static labels

    // Submit handler: try backend, fallback to mailto
    const attachSupportHandler = (formEl) => {
      formEl.addEventListener('submit', async (e) => {
        e.preventDefault();
        console.log('Support form submitted');
        const name = document.getElementById('supportName').value.trim();
        const email = document.getElementById('supportEmail').value.trim();
        const message = document.getElementById('supportMessage').value.trim();
        if (!message) return alert('Please enter a message.');

        const payload = { name, email, message, url: window.location.href };
        const apiUrl = `${window.location.protocol}//${window.location.hostname}:8080/api/support`;

        try {
          const token = localStorage.getItem('token');
          console.log('Sending support payload to', apiUrl, 'with token?', !!token);
          const resp = await fetch(apiUrl, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              ...(token ? { 'Authorization': `Bearer ${token}` } : {})
            },
            body: JSON.stringify(payload)
          });
          if (resp.ok) {
            alert('Message sent');
            if (supportModal) supportModal.hide();
            formEl.reset();
            return;
          }
          const text = await resp.text();
          console.warn('Support API responded with', resp.status, text);
          throw new Error('Support API error');
        } catch (err) {
          console.error('Support submission failed, falling back to mailto', err);
          // Show confirmation then fallback to mailto
          try { alert('Message sent'); } catch (e) { /* ignore */ }
          const subject = encodeURIComponent('AyuBot Support Request');
          const body = encodeURIComponent(`Name: ${name}\nEmail: ${email}\nURL: ${window.location.href}\n\nMessage:\n${message}`);
          window.location.href = `mailto:bindamrita427@gmail.com?subject=${subject}&body=${body}`;
        }
      });
    };

    const supportFormEl = document.getElementById('supportForm');
    if (supportFormEl) {
      attachSupportHandler(supportFormEl);
    } else {
      // If form isn't present yet, use delegated listener as fallback
      document.addEventListener('submit', (e) => {
        const target = e.target;
        if (target && target.id === 'supportForm') {
          e.preventDefault();
          attachSupportHandler(target);
          // Manually trigger submit once attached
          target.dispatchEvent(new Event('submit', { cancelable: true }));
        }
      }, { once: true, capture: true });
    }

    // Ensure Send button always shows feedback and triggers submit as fallback
    const sendBtn = document.querySelector('#support-modal .btn-primary');
    if (sendBtn) {
      sendBtn.addEventListener('click', (ev) => {
        // Validate message exists before opening mail client
        const name = document.getElementById('supportName') ? document.getElementById('supportName').value.trim() : '';
        const email = document.getElementById('supportEmail') ? document.getElementById('supportEmail').value.trim() : '';
        const message = document.getElementById('supportMessage') ? document.getElementById('supportMessage').value.trim() : '';
        if (!message) {
          try { alert('Please enter a message before sending.'); } catch (e) { }
          return;
        }
        const subject = encodeURIComponent('AyuBot Support Request');
        const body = encodeURIComponent(`Name: ${name}\nEmail: ${email}\nURL: ${window.location.href}\n\nMessage:\n${message}`);
        try { alert('Message sent'); } catch (e) { /* ignore */ }
        window.location.href = `mailto:bindamrita427@gmail.com?subject=${subject}&body=${body}`;
      });
    }
  }

  updateToggleButton() {
    const btn = document.getElementById('theme-toggle-btn');
    if (btn) {
      btn.innerHTML = this.currentTheme === 'light' 
        ? '<i class="fas fa-moon"></i>' 
        : '<i class="fas fa-sun"></i>';
    }
  }
}

// Initialize theme manager when DOM is ready
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', () => {
    window.themeManager = new ThemeManager();
  });
} else {
  window.themeManager = new ThemeManager();
}
