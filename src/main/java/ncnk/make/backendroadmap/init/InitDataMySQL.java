package ncnk.make.backendroadmap.init;

import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.api.book.BookApi;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDataMySQL {

    private final BookApi bookApi;

    public void init() {
        bookApi.callAladinApi();
    }
}