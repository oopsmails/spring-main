package com.ziyang.producerconsumer;

import java.io.Serializable;

import com.ziyang.producerconsumer.TaskObjConsumerIntf.TaskObjType;

public abstract class TaskObj {//implements Serializable {
//	private static final long serialVersionUID = 6078722264454892564L;
	private TaskObjType taskObjType;
	private Serializable data;
	
	public TaskObjType getTaskObjType() {
		return taskObjType;
	}
	public void setTaskObjType(TaskObjType taskObjType) {
		this.taskObjType = taskObjType;
	}
	public Serializable getData() {
		return data;
	}
	public void setData(Serializable data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "TaskObj [taskObjType=" + taskObjType + ", data=" + data + "]";
	}
}
