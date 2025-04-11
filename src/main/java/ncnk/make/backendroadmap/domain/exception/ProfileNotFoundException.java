package ncnk.make.backendroadmap.domain.exception;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException() {
        super("Fail: Profile Not Found!");
    }
}
