package ncnk.make.backendroadmap.domain.aop.time.logtrace;

import java.util.UUID;
import lombok.Getter;

/**
 * user 고유 ID(UUID로 표현) & level
 */
@Getter
public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public TraceId toNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId toPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }
}
