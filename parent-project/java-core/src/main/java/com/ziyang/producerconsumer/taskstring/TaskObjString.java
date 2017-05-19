package com.ziyang.producerconsumer.taskstring;

import com.ziyang.producerconsumer.TaskObj;
import com.ziyang.producerconsumer.TaskObjConsumerIntf;

public class TaskObjString extends TaskObj {
	public TaskObjString() {
		this(null);
	}
	
	public TaskObjString(String data) {
		this.setTaskObjType(TaskObjConsumerIntf.TaskObjType.CONSUMER_STRING);
		this.setData(data);
	}
	
}
