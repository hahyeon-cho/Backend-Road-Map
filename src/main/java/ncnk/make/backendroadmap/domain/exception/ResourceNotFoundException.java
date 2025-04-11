package ncnk.make.backendroadmap.domain.exception;

/**
 * 데이터 조회 실패 시 예외
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("[ERROR] Cannot Find Resource");
    }
}
