package com.ziyang.producerconsumer.taskstring;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziyang.producerconsumer.TaskObjQueueManager;

public class TaskStringProducerTimerTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(TaskStringProducerTimerTask.class);

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
	public void run() {
		logger.info("############### start putting Strings #################");
		try {
			for (int i = 0; i < 10; i++) {
				TaskObjString taskObjString = new TaskObjString("" + i);
				boolean sent = taskObjQueueManager.addItem(taskObjString);
				if (!sent) {
					logger.error("Error when sending TaskObj to Queue, taskObjString = " + taskObjString);
				}
			}
		} catch (InterruptedException e) {
			logger.error("Error when sending TaskObj to Queue!", e);
			e.printStackTrace();
		}
		logger.info("############### finish putting Strings #################");
	}
}
