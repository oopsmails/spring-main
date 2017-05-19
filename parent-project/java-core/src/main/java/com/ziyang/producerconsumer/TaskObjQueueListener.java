package com.ziyang.producerconsumer;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ziyang.producerconsumer.TaskObjConsumerIntf.TaskObjType;

public class TaskObjQueueListener implements Callable<TaskObj> {
	private static final Logger logger = LoggerFactory.getLogger(TaskObjQueueListener.class);
	// @Inject
	private TaskObjQueueManager taskObjQueueManager;
	// @Inject
	private TaskObjConsumerManager taskObjConsumerManager;
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Override
	public TaskObj call() {
		while (true) {
			TaskObj taskObj = null;
			logger.info("call() ....");
			try {
				taskObj = taskObjQueueManager.takeTaskObj();
				System.out.println("--> taskObj:" + taskObj);
				if (taskObj == null) {
					logger.info("No taskObj in TaskObjQueue: sleep 3 secs!");
					Thread.sleep(3000);
					continue;
				}
				logger.debug("taskObj:" + taskObj);

				TaskObjType taskObjType = taskObj.getTaskObjType();
				if (taskObjType == TaskObjType.UNDEFINED) {
					logger.error("taskObjType is UNDEFINED!");
					continue;
				}
				logger.debug("taskObjType = " + taskObjType);
				TaskObjConsumerIntf<TaskObj> taskObjConsumer = taskObjConsumerManager.getTaskObjConsumer(taskObjType);

				if (taskObjConsumer == null) {
					logger.error("No appropiate taskObjConsumer can be created!");
					return null;
				} else {
					logger.debug("taskObjConsumer: " + taskObjConsumer);
					taskObjConsumer.setData(taskObj);
					threadPoolTaskExecutor.submit(taskObjConsumer);
				}

			} catch (InterruptedException e) {
				logger.error("Get InterruptedException when taking object from Queue!", e);
				e.printStackTrace();
			}
		}
	}

	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}

	public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	public TaskObjQueueManager getTaskObjQueueManager() {
		return taskObjQueueManager;
	}

	public void setTaskObjQueueManager(TaskObjQueueManager taskObjQueueManager) {
		this.taskObjQueueManager = taskObjQueueManager;
	}

	public TaskObjConsumerManager getTaskObjConsumerManager() {
		return taskObjConsumerManager;
	}

	public void setTaskObjConsumerManager(TaskObjConsumerManager taskObjConsumerManager) {
		this.taskObjConsumerManager = taskObjConsumerManager;
	}
}
