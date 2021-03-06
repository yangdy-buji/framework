/*
 * Copyright 2013 the original author or authors.
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
package leap.web.action;

import leap.web.Renderable;
import leap.web.Result;

public final class RenderableResultProcessor extends AbstractResultProcessor implements ResultProcessor {
	
	public static final RenderableResultProcessor INSTANCE = new RenderableResultProcessor();
	
	@Override
    public void processReturnValue(ActionContext context, Object returnValue, Result result) throws Throwable {
        result.render((Renderable)returnValue);
    }
}