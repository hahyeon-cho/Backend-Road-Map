document.addEventListener('DOMContentLoaded', function () {
    var header = document.getElementById('main-header');
    var content = document.getElementById('main-content');

    // 헤더를 클릭하면 본문 내용으로 스크롤
    header.addEventListener('click', function () {
        content.scrollIntoView({ behavior: 'smooth' });
    });
});

$(function (){
   $("#footer").load("footer.html");
});



