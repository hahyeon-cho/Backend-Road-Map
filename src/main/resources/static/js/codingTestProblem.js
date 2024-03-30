$(document).ready(function() {
    var topics = $('#topicDisplay').data('data-tooltip');

    $('#topicDisplay').popup({
        on: 'click',
        content: `${topics}`
    });


    $('.example .teal.button').popup({
        on: 'click',
    });

});

document.getElementById('submit-btn').addEventListener('click', function() {
    $('#run-btn').click();

    setTimeout(function() {
        if (!$("#site-modal").hasClass("active")) {
            if (typeof window.getStdoutEditor === 'function') {
                const stdoutEditor = window.getStdoutEditor();
                const userCodeResult = stdoutEditor.getValue();
                submitUserCode(userCodeResult);
            } else {
                console.error("output 값을 찾을 수 없습니다.");
            }
        }
    }, 5000);
});

// userCodeResult를 인자로 받는 submitUserCode 함수 정의
function submitUserCode(userCodeResult) {
    var codingTestId = $('.problem-id').data('id');

    $.ajax({
        url: `/codingtest/${codingTestId}`,
        type: "POST",
        contentType: "text/plain",
        data: userCodeResult,
        dataType: 'json',
        success: function(response) {
            var resultMessage = response.result === "correct" ? "정답입니다." : "틀렸습니다.";
            showModal(resultMessage);
        },
        error: function(xhr, status, error) {
            console.error("제출 실패:", error);
            showModal("제출 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
    });
}


function showModal(result) {
    $("#result-modal #result-modal-title").text("채점 결과");
    $("#result-modal #result-modal-body").text(result);

    $("#result-modal").modal("show");
}