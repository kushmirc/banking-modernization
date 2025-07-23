// Login Page Specific JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Role selection functionality
    const roleOptions = document.querySelectorAll('.role-option');
    const roleInput = document.getElementById('selectedRole');
    
    roleOptions.forEach(option => {
        option.addEventListener('click', function() {
            // Remove active class from all options
            roleOptions.forEach(opt => opt.classList.remove('active'));
            
            // Add active class to clicked option
            this.classList.add('active');
            
            // Update hidden input
            if (roleInput) {
                roleInput.value = this.dataset.role;
            }
        });
    });
    
    // Form submission with loading state
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function() {
            const button = document.getElementById('loginButton');
            if (button) {
                const spinner = button.querySelector('.btn-spinner');
                const text = button.querySelector('.btn-text');
                
                button.classList.add('loading');
                if (text) text.style.display = 'none';
                if (spinner) spinner.style.display = 'block';
            }
        });
    }
});