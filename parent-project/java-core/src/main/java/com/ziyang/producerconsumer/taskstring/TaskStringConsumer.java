package com.ziyang.producerconsumer.taskstring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziyang.producerconsumer.TaskObjConsumerIntf;

public class TaskStringConsumer implements TaskObjConsumerIntf<TaskObjString> {
	private static final Logger logger = LoggerFactory.getLogger(TaskStringConsumer.class);
	private TaskObjString data;

	public TaskObjString getData() {
		return data;
	}

	public void setData(TaskObjString data) {
		this.data = data;
	}

	@Override
	public void consume(TaskObjString data) {
		logger.debug("############### start consuming Strings #################");
		logger.info("Start consuming data = " + data);

		String str = (String) data.getData();
		logger.info("TaskConsumer: " + this + ": consumed " + str);
		// try {
		// Thread.sleep(3000L);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		logger.debug("############### finish consuming Strings #################");
	}

	@Override
	public void run() {
		if (TaskObjConsumerIntf.TaskObjType.CONSUMER_STRING != data.getTaskObjType()) {
			logger.error("Unknown TaskItemType in TaskConsumer: " + data.getTaskObjType());
			return;
		}

		consume(data);
	}
}
