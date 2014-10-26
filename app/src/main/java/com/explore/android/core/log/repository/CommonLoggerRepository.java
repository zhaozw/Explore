package com.explore.android.core.log.repository;

import com.explore.android.core.log.Level;




public interface CommonLoggerRepository {
	/**
	 * Get the effective level for the specified logger. Note that the level of
	 * the current logger is not checked, since this level could be get from the
	 * logger itself.
	 * 
	 * @return the effective <code>Level</code>
	 */
	public Level getEffectiveLevel(String loggerName);
}
