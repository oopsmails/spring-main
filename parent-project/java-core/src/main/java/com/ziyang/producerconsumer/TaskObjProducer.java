package com.ziyang.producerconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public abstract class TaskObjProducer implements TaskObjProducerIntf<TaskObj> {
	private static final Logger logger = LoggerFactory.getLogger(TaskObjProducer.class);
	private TaskObjQueueManager taskObjQueueManager;

	public TaskObjQueueManager getTaskObjQueueManager() {
		return taskObjQueueManager;
	}

	public void setTaskObjQueueManager(TaskObjQueueManager taskObjQueueManager) {
		this.taskObjQueueManager = taskObjQueueManager;
	}
	
	@Override
	public boolean sendToTaskObjQueue(TaskObj taskObj) {
		boolean result = false;
		
		try {
			result = taskObjQueueManager.addItem(taskObj);
		} catch (InterruptedException e) {
			logger.error("Error when sending TaskObj to Queue, taskObj = " + taskObj, e);
			e.printStackTrace();
		}
		
		return result;
	}
}
