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


        $('#problems-list-table').DataTable({
            "processing": true,
            "serverSide": false,
            "ajax": {
                "url": "/api/member/test/{id}",
                "type": "GET",
                "dataType": "json",
                "dataSrc": "data"
            },
            "columns": [
                { "data": "id" }, // 문제 ID 열
                { "data": "problemName" }, // 문제 이름 열
                { "data": "difficulty" }, // 난이도 열
                {
                    // 풀었는지 여부에 따라 다른 내용을 표시하는 열
                    "data": "solved",
                    "render": function(data, type, row, meta) {
                        if (data) {
                            return '<span class="badge badge-success">Solved</span>';
                        } else {
                            return '<span class="badge badge-danger">Unsolved</span>';
                        }
                    }
                }
            ]
    });



});

