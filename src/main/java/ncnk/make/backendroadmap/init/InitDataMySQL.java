package ncnk.make.backendroadmap.init;

import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.api.Book.BookApi;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDataMySQL {
    private final BookApi bookApi;

    //    @PostConstruct
    public void init() {
        bookApi.callAladinApi();
    }
}