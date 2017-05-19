package com.ziyang.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziyang.producerconsumer.TaskObjConsumerIntf.TaskObjType;

public class TaskObjQueueManager {
	private static final Logger logger = LoggerFactory.getLogger(TaskObjQueueManager.class);
	private BlockingQueue<TaskObj> sharedBlockingQueue = new LinkedBlockingQueue<TaskObj>();

	public boolean addItem(TaskObj t) throws InterruptedException {
		if (t == null) {
			logger.error("Error when sending TaskObj to Queue, taskObj is null!");
			return false;
		}
		TaskObjType taskObjType = t.getTaskObjType();
		if (taskObjType == null || TaskObjType.getEnumByValue(taskObjType.getValue()) == TaskObjType.UNDEFINED) {
			logger.error("Error when sending TaskObj to Queue, taskObjType is null or UNDEFINED!");
			return false;
		}
		sharedBlockingQueue.put(t);

		return true;
	}

	public TaskObj takeTaskObj() throws InterruptedException {
		TaskObj result = sharedBlockingQueue.take();

		return result;
	}

	public int getSize() throws InterruptedException {
		int result = sharedBlockingQueue.size();

		return result;
	}

	public BlockingQueue<TaskObj> getSharedQueue() {
		return sharedBlockingQueue;
	}

	public void setSharedQueue(BlockingQueue<TaskObj> sharedBlockingQueue) {
		this.sharedBlockingQueue = sharedBlockingQueue;
	}
}
