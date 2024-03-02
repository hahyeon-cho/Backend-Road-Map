package ncnk.make.backendroadmap.domain.aop.time;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.aop.time.logtrace.TraceId;

@Getter
public class TraceStatus {
    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    private TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public static TraceStatus createTraceStatus(TraceId traceId, Long startTimeMs, String message) {
        return new TraceStatus(traceId, startTimeMs, message);
    }
}
