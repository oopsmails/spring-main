package com.ziyang.producerconsumer.taskstring;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziyang.producerconsumer.TaskObjProducerIntf;
import com.ziyang.producerconsumer.TaskObjQueueManager;

public class TaskStringProducer extends TimerTask implements TaskObjProducerIntf<TaskObjString> {
	private static final Logger logger = LoggerFactory.getLogger(TaskStringProducer.class);

	// @Autowired
	// @Inject
	private TaskObjQueueManager taskObjQueueManager;

	public TaskObjQueueManager getTaskObjQueueManager() {
		return taskObjQueueManager;
	}

	public void setTaskObjQueueManager(TaskObjQueueManager taskObjQueueManager) {
		this.taskObjQueueManager = taskObjQueueManager;
	}

	@Override
	public boolean sendToTaskObjQueue(TaskObjString taskObj) {
		boolean result = false;

		try {
			logger.info("@sendToTaskObjQueue, taskObj = " + taskObj);
			result = taskObjQueueManager.addItem(taskObj);
		} catch (InterruptedException e) {
			logger.error("Error when sending TaskObj to Queue, taskObj = " + taskObj, e);
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void run() {
		logger.info("############### start putting Strings #################");
		for (int i = 0; i < 10; i++) {
			TaskObjString taskObjString = new TaskObjString("" + i);
			boolean sent = sendToTaskObjQueue(taskObjString);
			if (!sent) {
				logger.error("Error when sending TaskObj to Queue, taskObjString = " + taskObjString);
			}
		}
		logger.info("############### finish putting Strings #################");
	}
}
