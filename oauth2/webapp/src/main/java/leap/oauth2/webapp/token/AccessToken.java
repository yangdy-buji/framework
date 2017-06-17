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

package leap.oauth2.webapp.token;

import leap.core.security.token.TokenCredentials;

public interface AccessToken extends TokenCredentials {

    /**
     * Optional. The token type.
     */
    String getType();

    /**
     * Returns <code>true</code> if the token is <code>'Bearer'</code> type.
     */
    default boolean isBearer() {
        return true;
    }
}