// Main JavaScript - Shared functionality across all pages

// Enhanced form validation for all forms
document.addEventListener('DOMContentLoaded', function() {
    // Apply to all form inputs
    const inputs = document.querySelectorAll('.form-input');
    
    inputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value.trim() === '') {
                this.style.borderColor = 'var(--error)';
            } else {
                this.style.borderColor = 'var(--success)';
            }
        });
        
        input.addEventListener('focus', function() {
            this.style.borderColor = 'var(--border-focus)';
        });
    });
    
    // Add loading state to all forms on submit
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function() {
            const submitBtn = this.querySelector('[type="submit"]');
            if (submitBtn) {
                submitBtn.classList.add('loading');
            }
        });
    });
});