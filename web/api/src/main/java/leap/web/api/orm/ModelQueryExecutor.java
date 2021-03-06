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

package leap.web.api.orm;

import leap.orm.query.CriteriaQuery;
import leap.web.api.mvc.params.CountOptions;
import leap.web.api.mvc.params.QueryOptions;
import leap.web.api.mvc.params.QueryOptionsBase;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Not thread-safe, creates a new instance for a query.
 */
public interface ModelQueryExecutor {

    /**
     * Excludes the field names in the result record.
     */
    ModelQueryExecutor selectExclude(String... names);

    /**
     * Finds the record by the given id.
     */
    QueryOneResult queryOne(Object id, QueryOptionsBase options);

    /**
     * Query the records of model.
     */
    default QueryListResult queryList(QueryOptions options) {
        return queryList(options, null);
    }

    /**
     * Query the records of model with the given filter fields.
     */
    default QueryListResult queryList(QueryOptions options, Map<String, Object> filters) {
        return queryList(options, filters, null);
    }

    /**
     * Query the records of model with the given filter fields.
     *
     * <p/>
     * The callback will be invoked before executing the query.
     */
    QueryListResult queryList(QueryOptions options, Map<String, Object> filters, Consumer<CriteriaQuery> callback);

    /**
     * Query the total count of records.
     */
    default QueryListResult count(CountOptions options) {
        return count(options, null);
    }

    /**
     * Query the total count of records.
     *
     * <p/>
     * The callback will be invoked before executing the query.
     */
    QueryListResult count(CountOptions options, Consumer<CriteriaQuery> callback);
}
