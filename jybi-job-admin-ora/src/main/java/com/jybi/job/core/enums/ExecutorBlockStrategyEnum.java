package com.jybi.job.core.enums;

/**
 * Created by xuxueli on 17/5/9.
 */
public enum ExecutorBlockStrategyEnum {

    SERIAL_EXECUTION("单机串行"),
    /*CONCURRENT_EXECUTION("并行"),*/
    DISCARD_LATER("丢弃后续调度"),
    COVER_EARLY("覆盖之前调度");

    private final String title;
    private ExecutorBlockStrategyEnum (String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public static com.jybi.job.core.enums.ExecutorBlockStrategyEnum match(String name, com.jybi.job.core.enums.ExecutorBlockStrategyEnum defaultItem) {
        if (name != null) {
            for (com.jybi.job.core.enums.ExecutorBlockStrategyEnum item: values()) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
        }
        return defaultItem;
    }
}
