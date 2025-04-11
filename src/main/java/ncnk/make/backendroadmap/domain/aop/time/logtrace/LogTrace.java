package ncnk.make.backendroadmap.domain.aop.time.logtrace;

import ncnk.make.backendroadmap.domain.aop.time.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
