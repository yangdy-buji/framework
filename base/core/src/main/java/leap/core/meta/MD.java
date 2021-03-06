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
package leap.core.meta;

import leap.core.AppContext;
import leap.lang.meta.MPattern;

/**
 * MD means metadata
 */
public class MD {
	
	public static MPattern getMPattern(String name) {
		return getMPatternSource().getPattern(name);
	}
	
	public static MPattern tryGetMPattern(String name) {
		return getMPatternSource().tryGetPattern(name);
	}
	
	public static MPatternSource getMPatternSource() {
		return AppContext.factory().getBean(MPatternSource.class);
	}
	
	protected MD() {
		
	}
}
