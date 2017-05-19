package com.ziyang.producerconsumer;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface TaskObjConsumerIntf<T extends TaskObj> extends Runnable { //<T extends Serializable> {
	enum TaskObjType {
		CONSUMER_STRING(1, "CONSUMER_STRING"), 
		CONSUMER_BATCH_OBJ(2, "CONSUMER_BATCH_OBJ"),
		UNDEFINED(-1, "UNDEFINED");
		
		private int value;
		private String typeName;
		
		private TaskObjType(int value, String typeName) {
			this.value = value;
			this.typeName = typeName;
		}
		
		private static final Map<Integer, TaskObjType> lookup = new HashMap<Integer, TaskObjType>();
		static {
			for (TaskObjType r : EnumSet.allOf(TaskObjType.class))
				lookup.put(r.getValue(), r);
		}

		public static TaskObjType getEnumByValue(int value) {
			TaskObjType result = lookup.get(value);
			return result == null ? UNDEFINED : result;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		
	};
	
	void consume(T data);
	public T getData();
	public void setData(T data);
}
