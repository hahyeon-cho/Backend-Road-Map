function readLocalFile(sourceEditor) {
    // 파일 선택
    var input = document.createElement("input");
    input.type = "file";

    // import 로컬 파일로
    document.body.appendChild(input);

    input.click();

    // 파일 선택 이벤트 핸들러
    input.addEventListener("change", function (event) {
        var file = event.target.files[0];

        //파일 열려있을때
        if (file) {
            var reader = new FileReader();

            // 파일 읽기 완료 후의 이벤트 핸들러 등록
            reader.onload = function (e) {
                // 읽은 파일 내용 e.target.result에 넣음
                var fileContent = e.target.result;
                console.log("파일 내용 : ", fileContent);

                // sourceEditor에 코드 넣어둠
                sourceEditor.setValue(fileContent);
            };

            // 파일 읽기
            reader.readAsText(file);
        }
    });
}
