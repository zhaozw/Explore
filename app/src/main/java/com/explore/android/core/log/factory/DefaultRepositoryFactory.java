package com.explore.android.core.log.factory;

import com.explore.android.core.log.repository.DefaultLoggerRepository;
import com.explore.android.core.log.repository.LoggerRepository;

public enum DefaultRepositoryFactory {
	;
	
	public static LoggerRepository getDefaultLoggerRepository() {
		return DefaultLoggerRepository.INSTANCE;
	}
}
