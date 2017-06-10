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

package leap.orm.tested.tag;

import leap.lang.params.Params;
import leap.orm.sql.SqlContext;
import leap.orm.sql.SqlStatementBuilder;
import leap.orm.sql.SqlTag;
import leap.orm.sql.SqlTagProcessor;

import java.io.IOException;

public class SimpleQueryFilterTag implements SqlTagProcessor {

    @Override
    public void processTag(SqlContext context, SqlTag tag, SqlStatementBuilder stm, Params params) throws IOException {

        //only process FilteredModel
        if(tag.getContent().endsWith("FilteredModel")) {
            stm.append("t.num_ > ?");
            stm.addParameter(10);
        }

    }

}
