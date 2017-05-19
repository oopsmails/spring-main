package com.ziyang.producerconsumer;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TaskObjConsumerManager implements ApplicationContextAware {
	
	/****
	 * This Map is only for testing/documenting purpose of Spring Map injection.
	 */
//	@Resource
	private Map<TaskObjConsumerIntf.TaskObjType, TaskObjConsumerIntf<TaskObj>> taskObjConsumers;

	public Map<TaskObjConsumerIntf.TaskObjType, TaskObjConsumerIntf<TaskObj>> getTaskObjConsumers() {
		return taskObjConsumers;
	}

	public void setTaskObjConsumers(Map<TaskObjConsumerIntf.TaskObjType, TaskObjConsumerIntf<TaskObj>> taskObjConsumers) {
		this.taskObjConsumers = taskObjConsumers;
	}
	
	/****
	 * This is actually used.
	 */
	private Map<TaskObjConsumerIntf.TaskObjType, String> taskObjConsumerBeanRef;

	public Map<TaskObjConsumerIntf.TaskObjType, String> getTaskObjConsumerBeanRef() {
		return taskObjConsumerBeanRef;
	}

	public void setTaskObjConsumerBeanRef(Map<TaskObjConsumerIntf.TaskObjType, String> taskObjConsumerBeanRef) {
		this.taskObjConsumerBeanRef = taskObjConsumerBeanRef;
	}
	
	private ApplicationContext ctx;

	public TaskObjConsumerIntf<TaskObj> getTaskObjConsumer(TaskObjConsumerIntf.TaskObjType taskObjType) {
		@SuppressWarnings("unchecked")
		TaskObjConsumerIntf<TaskObj> result = (TaskObjConsumerIntf<TaskObj>)ctx.getBean(taskObjConsumerBeanRef.get(taskObjType));
		
		return result;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
}
