package ncnk.make.backendroadmap.domain.aop.time.logtrace;

import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.TraceStatus;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}", traceId.getId(), message);
        return TraceStatus.createTraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.toNextId());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("Check Though Time!! [{}] {} Time={}ms", traceId.getId(), status.getMessage(), resultTimeMs);
        } else {
            log.info("Occur Exception Time!! [{}] Time={}ms, ex={}", traceId.getId(), resultTimeMs, e.toString());
        }
        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.toPreviousId());
        }
    }
}