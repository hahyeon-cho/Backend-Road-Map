package ncnk.make.backendroadmap.domain.exception;

/**
 * 세션이 없을 경우 예외
 */
public class SessionNullPointException extends RuntimeException {

    public SessionNullPointException(String message) {
        super(message);
    }
}
