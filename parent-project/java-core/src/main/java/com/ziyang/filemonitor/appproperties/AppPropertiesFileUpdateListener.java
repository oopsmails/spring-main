package com.ziyang.filemonitor.appproperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziyang.filemonitor.FileUpdateListener;
import com.ziyang.filemonitor.FileUpdateMonitor;

public class AppPropertiesFileUpdateListener implements FileUpdateListener {
	// private Logger logger = Logger.getLogger(this.getClass());
	private static final Logger logger = LoggerFactory.getLogger(AppPropertiesFileUpdateListener.class);
	private static AppPropertiesFileUpdateListener instance;

	private String appPropertiesFile;
	private long period;
	private FileUpdateMonitor fileUpdateMonitor;
	private Properties props;

	public static synchronized AppPropertiesFileUpdateListener getInstance(long period, String fileName,
			FileUpdateMonitor fileUpdateMonitor) {
		if (instance == null) {
			instance = new AppPropertiesFileUpdateListener(period, fileName, fileUpdateMonitor);
		}
		return instance;
	}

	private AppPropertiesFileUpdateListener(long period, String fileName, FileUpdateMonitor fileUpdateMonitor) {
		this.period = period;
		this.appPropertiesFile = fileName;
		this.fileUpdateMonitor = fileUpdateMonitor;
		this.props = new Properties();

		reloadAppProps();
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		logger.info("setting iecode configuration file checking period = , " + period);
		this.period = period;
	}

	public FileUpdateMonitor getFileUpdateMonitor() {
		return fileUpdateMonitor;
	}

	public void setFileUpdateMonitor(FileUpdateMonitor fileUpdateMonitor) {
		this.fileUpdateMonitor = fileUpdateMonitor;
	}

	public String getAppPropertiesFile() {
		return appPropertiesFile;
	}

	public void setAppPropertiesFile(String appPropertiesFile) {
		this.appPropertiesFile = appPropertiesFile;
	}

	public Properties getProps() {
		return props;
	}

	public synchronized void setProps(Properties props) {
		logger.info("Setting app properties = " + props);
		this.props = props;
		try {
			fileUpdateMonitor.addFileUpdateListener(this, appPropertiesFile, period);
		} catch (FileNotFoundException e) {
			logger.error("File Not Found: " + appPropertiesFile + e.getMessage());
		}
	}

	private void reloadAppProps() {
		InputStream in;

		try {
			logger.info("Reloading app properties ...");
			in = new FileInputStream(appPropertiesFile);
			props.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			logger.error("File Not Found: " + appPropertiesFile + e.getMessage());
		} catch (IOException e) {
			logger.error("Cannot load file: " + appPropertiesFile + e.getMessage());
		}
		setProps(props);
	}

	@Override
	public void fileUpdated(String fileName) {
		reloadAppProps();
	}
}
