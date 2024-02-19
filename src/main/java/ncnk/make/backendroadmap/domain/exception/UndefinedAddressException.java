package ncnk.make.backendroadmap.domain.exception;

public class UndefinedAddressException extends RuntimeException {
    public UndefinedAddressException() {
        super("[Error] Undefined Address");
    }
}
