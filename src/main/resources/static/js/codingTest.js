document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('tr[data-href]').forEach(elem => {
        elem.addEventListener('click', () => {
            window.location.href = elem.dataset.href;
        });
    });
});


$(document).ready(function() {
    $('#problems-list-table').DataTable({
        "lengthChange": false,
        "pageLength": 10
    });
});