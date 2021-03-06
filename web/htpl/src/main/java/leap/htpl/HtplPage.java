/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package leap.htpl;

import java.util.Map;

import leap.lang.beans.DynaBean;

public interface HtplPage extends DynaBean {
	
	String TITLE_PROPERTY    = "title";
	String TEMPLATE_PROPERTY = "template";
	
	default String getTitle() {
		return (String)getProperty(TITLE_PROPERTY);
	}
	
	default void setTitle(String title) {
		setProperty(TITLE_PROPERTY, title);
	}
	
	default void putProperties(Map<String, ? extends Object> m) {
		getProperties().putAll(m);
	}

	HtplTemplate getTemplate();
	
}