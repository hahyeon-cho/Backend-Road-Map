package ncnk.make.backendroadmap.domain.exception;

/**
 * 포인트 업데이트 실패 시 예외
 */
public class CannotUpdatePointException extends RuntimeException {

    public CannotUpdatePointException(String message) {
        super(message);
    }
}
