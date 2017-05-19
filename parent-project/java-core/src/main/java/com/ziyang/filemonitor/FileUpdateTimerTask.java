package com.ziyang.filemonitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUpdateTimerTask extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(FileUpdateTimerTask.class);
	
	FileUpdateListener fileUpdateListener;
	String fileName;
	File file;
	long lastModified;

	public FileUpdateTimerTask(FileUpdateListener fileUpdateListener, String fileName) throws FileNotFoundException {
		this.fileUpdateListener = fileUpdateListener;
		this.fileName = fileName;
		this.lastModified = 0;

		file = new File(fileName);
		logger.info("fileName=" + fileName);
		if (!file.exists()) {
			if (fileName != null) {
				file = new File(fileName);
			} else {
				logger.error("File Not Found: " + fileName);
				throw new FileNotFoundException("File Not Found: " + fileName);
			}
		}
		
		this.lastModified = file.lastModified();
		logger.info("set lastModified=" + (new Date(this.lastModified).toString()));
	}

	public void run() {
		long lastModified = file.lastModified();
		logger.info("The file '" + file + "' is under monitoring, lastModified = " + (new Date(lastModified).toString()));
		if (lastModified != this.lastModified) {
			logger.info("The file '" + file + "', previousModified = " + (new Date(this.lastModified).toString()));
			logger.info("The file '" + file + "', lastModified = " + (new Date(lastModified).toString()));
			logger.info("The file '" + file + "', reloading ...");
			this.lastModified = lastModified;
			fileUpdateListener.fileUpdated(fileName);
		}
	}
}
