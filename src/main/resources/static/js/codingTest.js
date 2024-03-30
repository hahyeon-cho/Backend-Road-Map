document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('tr[data-href]').forEach(elem => {
        elem.addEventListener('click', () => {
            window.location.href = elem.dataset.href;
        });
    });
});
