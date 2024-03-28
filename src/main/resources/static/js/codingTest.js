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
        "pageLength": 10,
        "columnDefs": [
            { "orderable": false, "targets": 0 }
        ]
    });
});


$(document).ready(function() {
    $('#submit-btn').click(function() {
        var output = stdoutEditor.getValue();

        // AJAX 요청을 통해 서버로 데이터 전송
        $.ajax({
            url: '서버의 엔드포인트 URL', // 서버의 엔드포인트 URL
            type: 'POST', // 요청 방식
            contentType: 'application/json', // 전송할 데이터의 MIME 타입
            data: JSON.stringify({
                output: output // 서버로 전송할 데이터
            }),
            success: function(response) {
                // 요청 성공 시 수행할 작업
                console.log('Submission successful:', response);
            },
            error: function(xhr, status, error) {
                // 요청 실패 시 수행할 작업
                console.error('Submission failed:', error);
            }
        });
    });
});