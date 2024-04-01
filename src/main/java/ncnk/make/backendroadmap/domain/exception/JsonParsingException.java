package ncnk.make.backendroadmap.domain.exception;

/**
 * 데이터 조회 실패 시 예외
 */
public class JsonParsingException extends RuntimeException {
    public JsonParsingException() {
        super("[ERROR] Fail To Parse JSON");
    }
}
