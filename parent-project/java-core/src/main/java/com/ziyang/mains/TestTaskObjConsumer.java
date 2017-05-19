package com.ziyang.mains;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ziyang.config.AppConfigJavaCore;
import com.ziyang.producerconsumer.TaskObjQueueListener;
import com.ziyang.producerconsumer.taskstring.TaskStringProducer;

public class TestTaskObjConsumer {
	protected static Logger log = LoggerFactory.getLogger(TestTaskObjConsumer.class);
	ApplicationContext xmlContext;
	ApplicationContext annotationContext;

	private void init() {
		xmlContext = new ClassPathXmlApplicationContext("appContext-java-core-test.xml");
		annotationContext = new AnnotationConfigApplicationContext(AppConfigJavaCore.class);
	}
	public static void main(String[] args) {
		try {
			TestTaskObjConsumer testMain = new TestTaskObjConsumer();
			testMain.init();
			
			ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor)testMain.xmlContext.getBean("threadPoolTaskExecutor");

			TaskStringProducer taskStringProducer = (TaskStringProducer)testMain.xmlContext.getBean("taskStringProducer");
			Timer timer = new Timer(true);
			timer.schedule(taskStringProducer, 2000, 10000);
			
//			threadPoolTaskExecutor.submit(taskStringProducer);
			
			TaskObjQueueListener taskObjQueueListener = (TaskObjQueueListener)testMain.xmlContext.getBean("taskObjQueueListener");
			threadPoolTaskExecutor.submit(taskObjQueueListener);
			
//			Thread.sleep(6000);
//			System.out.println("Wake up after sleeping ....... ");
//			
//			threadPoolTaskExecutor.submit(taskStringProducer);
			
			//check active thread, if zero then shut down the thread pool
//			for (;;) {
//				int count = threadPoolTaskExecutor.getActiveCount();
//				System.out.println("Active Threads : " + count);
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				if (count == 0) {
//					threadPoolTaskExecutor.shutdown();
//					break;
//				}
//			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
			Throwable cause = e.getCause();
			while (cause != null) {
				System.out.println(cause.getMessage());
				log.error(cause.getMessage(), cause);
				cause = cause.getCause();
			}
			System.exit(1);
		}
	}

}
