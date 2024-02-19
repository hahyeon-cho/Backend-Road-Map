package ncnk.make.backendroadmap.domain.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("[ERROR] Cannot Find Resource");
    }
}
