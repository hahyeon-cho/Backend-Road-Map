package ncnk.make.backendroadmap.domain.exception;

/**
 * API 호출 실패 시 예외
 */
public class FailToCallApiException extends RuntimeException {
    public FailToCallApiException(String message) {
        super(message);
    }

    public FailToCallApiException() {
        super();
    }
}
