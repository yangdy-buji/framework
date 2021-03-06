/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package leap.web;

import leap.web.route.Route;
import leap.web.route.Routes;

import java.util.Map;

public class SimpleRouter implements Router {

    protected Routes routes;
    protected String path;

    public SimpleRouter() {
    }

    public SimpleRouter(Routes routes) {
        this.routes = routes;
    }

    public SimpleRouter(String path) {
        this.path = path;
    }

    public SimpleRouter(Routes routes, String path) {
        this.routes = routes;
        this.path = path;
    }

    public Routes getRoutes() {
        return routes;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Route match(String method, String path, Map<String, Object> inParameters, Map<String, String> outVariables) {
        return routes.match(method, null != this.path ? this.path : path, inParameters, outVariables);
    }
}