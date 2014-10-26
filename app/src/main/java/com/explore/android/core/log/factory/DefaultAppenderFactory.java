package com.explore.android.core.log.factory;

import com.explore.android.core.log.appender.Appender;
import com.explore.android.core.log.appender.LogCatAppender;
import com.explore.android.core.log.format.PatternFormatter;

public enum DefaultAppenderFactory {
	;
	
	public static Appender createDefaultAppender() {
		Appender appender = new LogCatAppender();
		appender.setFormatter(new PatternFormatter());
		
		return appender;
	}
}
