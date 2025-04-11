package ncnk.make.backendroadmap.domain.exception;

/**
 * 중복된 데이터가 존재해 트랜잭션 실패 시 예외
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
