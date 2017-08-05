package com.oopsmails.legcybean;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum LogLevelType {
	TRACE(1, "Trace Level"),
    DEBUG(2, "Debug Level"),
    INFO(3, "Info Level"),
    WARN(4, "Warn Level"),
    ERROR(5, "Error Level"),
    FATAL(6, "Fatal Level"),
    UNDEFINED(-1, "Invalid Level");

    private static final Map<Integer, LogLevelType> lookup = new HashMap<Integer, LogLevelType>();
    static {
        for (LogLevelType r: EnumSet.allOf(LogLevelType.class))
          lookup.put(r.getLevel(), r);
    }
    private int level;
    private String desc;

    private LogLevelType(int level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    public static LogLevelType getEnum(int level) {
        return lookup.get(level);
    }

	public int getLevel() {
		return level;
	}

	public String getDesc() {
		return desc;
	}
}
