document.addEventListener('DOMContentLoaded', function() {
    const faqLinks = document.querySelectorAll('.faq_category > a');
    
    faqLinks.forEach(link => {
        link.addEventListener('click', function() {
            faqLinks.forEach(l => l.classList.remove('active'));
            this.classList.add('active');
        });
    });
});