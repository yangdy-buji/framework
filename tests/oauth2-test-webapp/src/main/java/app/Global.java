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
package app;

import leap.core.annotation.Inject;
import leap.oauth2.webapp.OAuth2Configurator;
import leap.web.App;
import leap.web.config.WebConfigurator;

/**
 * App with full oauth2 features.
 */
public class Global extends App {
    
    protected @Inject OAuth2Configurator oc;

    @Override
    protected void configure(WebConfigurator c) {
        oc.ignorePath("/ignore_access_token_resolved");
        super.configure(c);
    }
}