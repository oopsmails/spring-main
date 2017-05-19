package com.ziyang.producerconsumer;

public interface TaskObjProducerIntf<T extends TaskObj> {
	public boolean sendToTaskObjQueue(T taskObj);
}
