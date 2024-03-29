/*
 * Copyright 2008 The Microlog project @sourceforge.net
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.explore.android.core.log.format.command;

import com.explore.android.core.log.Level;

/**
 * A converter that is used for printing the current thread name.
 * 
 * Minimum requirements: CLDC 1.1
 * 
 * @author Johan Karlsson (johan.karlsson@jayway.se)
 */
public class ThreadFormatCommand implements FormatCommandInterface {

	/**
	 * @see com.google.code.microlog4android.format.command.FormatCommandInterface#init(String)
	 */
	public void init(String preFormatString) {
		// Do nothing.
	}

	/**
	 * Execute the <code>ThreadFormatCommand</code>.
	 */
	public String execute(String clientID, String name, long time, Level level,
			Object message, Throwable throwable) {
		return Thread.currentThread().getName();
	}

}
