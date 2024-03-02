package ncnk.make.backendroadmap.domain.aop.time.callback;

import ncnk.make.backendroadmap.domain.aop.time.TraceStatus;
import ncnk.make.backendroadmap.domain.aop.time.logtrace.LogTrace;
import org.springframework.stereotype.Component;

/**
 * 템플릿 콜백 패턴: "템플릿" 역할
 */
@Component
public class TraceTemplate {
    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //로직 호출
            T result = callback.call();

            trace.end(status);
            return result;

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
