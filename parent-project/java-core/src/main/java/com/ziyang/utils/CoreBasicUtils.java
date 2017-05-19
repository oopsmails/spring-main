package com.ziyang.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziyang.common.CoreConstants;

public class CoreBasicUtils {
	protected static Logger logger = LoggerFactory.getLogger(CoreBasicUtils.class);
	
	public static String buildDelimiteredStr(List<String> pStrs, String pDelimiter) {
		if(pStrs == null || pStrs.size() == 0 || pDelimiter == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		Iterator<String> iter = pStrs.iterator();
		while(iter.hasNext()) {
			sb.append(iter.next());
			if(iter.hasNext()) {
				sb.append(pDelimiter);
			}
		}
		
		return sb.toString();
	}
	
	public static Date dateOnly(Date pDate) {
		Calendar cal = Calendar.getInstance(CoreConstants.EST_TIMEZONE);
    	cal.setTime(pDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static boolean isReallyEmpty(String pStr) {
		if(pStr == null || "".equals(pStr.trim())) {
			return true;
		}
		return false;
	}
}
