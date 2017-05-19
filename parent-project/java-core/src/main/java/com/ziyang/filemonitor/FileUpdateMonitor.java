package com.ziyang.filemonitor;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUpdateMonitor {
	private static final Logger logger = LoggerFactory.getLogger(FileUpdateMonitor.class);
	private static FileUpdateMonitor instance;
	private Timer timer;
	private Hashtable<String, FileUpdateTimerTask> timerEntries;

	public static synchronized FileUpdateMonitor getInstance() {
		if (instance == null) {
			instance = new FileUpdateMonitor();
		}
		return instance;
	}

	private FileUpdateMonitor() {
		timer = new Timer(true);
		timerEntries = new Hashtable<String, FileUpdateTimerTask>();
	}

	public void addFileUpdateListener(FileUpdateListener fileUpdateListener, String fileName, long period)
			throws FileNotFoundException {
		logger.info("Adding to FileUpdateListener, adding file = " + fileName);
		removeFileUpdateListener(fileUpdateListener, fileName);
		FileUpdateTimerTask task = new FileUpdateTimerTask(fileUpdateListener, fileName);
		timerEntries.put(fileName + fileUpdateListener.hashCode(), task);
		timer.schedule(task, period, period);
	}

	public void removeFileUpdateListener(FileUpdateListener fileUpdateListener, String fileName) {
		logger.info("Removing from FileUpdateListener, removing file, " + fileName);
		FileUpdateTimerTask task = (FileUpdateTimerTask) timerEntries.remove(fileName + fileUpdateListener.hashCode());
		if (task != null) {
			task.cancel();
		}
	}
}
