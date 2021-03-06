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
package leap.orm;

import leap.core.AppConfig;
import leap.core.BeanFactory;
import leap.core.BeanFactoryAware;
import leap.core.annotation.ConfigProperty;
import leap.core.annotation.Configurable;
import leap.core.el.EL;
import leap.core.el.ExpressionLanguage;
import leap.core.ioc.PostConfigureBean;
import leap.lang.New;
import leap.lang.exception.ObjectNotFoundException;
import leap.lang.expression.Expression;
import leap.lang.naming.NamingStyles;
import leap.orm.config.OrmConfigExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configurable(prefix=OrmConfig.KEY_PREFIX)
public class DefaultOrmConfig implements OrmConfig,PostConfigureBean,BeanFactoryAware {

    protected boolean     autoCreateTables           = false;
    protected boolean     autoMappingTables          = false;
    protected boolean     readDbSchema               = true;
	protected boolean     autoGenerateColumns        = false;
	protected boolean     autoGenerateOptimisticLock = false;
	protected boolean     modelCrossContext          = false;
    protected boolean     mappingClassSimpleName     = false;
    protected boolean     mappingFieldExplicitly     = false;
    protected long        defaultMaxResults          = -1;
    protected String      optimisticLockFieldName    = OrmConstants.LOCK_VERSION;
    protected Set<String> autoGeneratedFieldNames    = New.linkedHashSet(OrmConstants.CREATED_AT,OrmConstants.UPDATED_AT);
	protected String      tableNamingStyle           = NamingStyles.NAME_LOWER_UNDERSCORE;
	protected String      columnNamingStyle          = NamingStyles.NAME_LOWER_UNDERSCORE;
    protected String      defaultSerializer          = "json";

    protected SerializeConfig              defaultSerializeConfig;
    protected Map<String, SerializeConfig> serializeConfigs = new HashMap<>();
    protected Map<String, SerializeConfig> serializeConfigsImv = Collections.unmodifiableMap(serializeConfigs);

    protected FilterColumnConfig filterColumnConfig = new DefaultFilterColumnConfig();
    protected QueryFilterConfig  queryFilterConfig  = new DefaultQueryFilterConfig();

    protected BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public boolean isAutoCreateTables() {
        return autoCreateTables;
    }

    @ConfigProperty
    public void setAutoCreateTables(boolean autoCreateTables) {
        this.autoCreateTables = autoCreateTables;
    }

    @Override
    public boolean isReadDbSchema() {
        return readDbSchema;
    }

    @ConfigProperty
    public void setReadDbSchema(boolean readDbSchema) {
        this.readDbSchema = readDbSchema;
    }

    @Override
    public boolean isAutoMappingTables() {
        return autoMappingTables;
    }

    @ConfigProperty
    public void setAutoMappingTables(boolean autoMappingTables) {
        this.autoMappingTables = autoMappingTables;
    }

    @Override
    public long getDefaultMaxResults() {
	    return defaultMaxResults;
    }
	
	@Override
    public String getOptimisticLockFieldName() {
	    return optimisticLockFieldName;
    }

	public boolean isAutoGenerateColumns() {
	    return autoGenerateColumns;
    }
	
	public boolean isAutoGenerateOptimisticLock() {
		return autoGenerateOptimisticLock;
	}

    @Override
    public boolean isModelCrossContext() {
        return modelCrossContext;
    }

    @Override
    public boolean isMappingClassSimpleName() {
        return mappingClassSimpleName;
    }

    @ConfigProperty
    public void setMappingClassSimpleName(boolean mappingClassSimpleName) {
        this.mappingClassSimpleName = mappingClassSimpleName;
    }

    @Override
    public boolean isMappingFieldExplicitly() {
        return mappingFieldExplicitly;
    }

    @ConfigProperty
    public void setMappingFieldExplicitly(boolean mappingFieldExplicitly) {
        this.mappingFieldExplicitly = mappingFieldExplicitly;
    }

    @Override
    @Configurable.Nested
    public FilterColumnConfig getFilterColumnConfig() {
        return filterColumnConfig;
    }

    @Override
    @Configurable.Nested
    public QueryFilterConfig getQueryFilterConfig() {
        return queryFilterConfig;
    }

    @ConfigProperty
	public void setAutoGenerateOptimisticLock(boolean autoGenerateOptimisticLockField) {
		this.autoGenerateOptimisticLock = autoGenerateOptimisticLockField;
	}

	public Set<String> getAutoGeneratedFieldNames() {
	    return autoGeneratedFieldNames;
    }
	
	@ConfigProperty
	public void setOptimisticLockFieldName(String optimisticLockFieldName) {
		this.optimisticLockFieldName = optimisticLockFieldName;
	}

	@ConfigProperty
	public void setDefaultMaxResults(long maxResults) {
		this.defaultMaxResults = maxResults;
	}

	@ConfigProperty
	public void setAutoGenerateColumns(boolean autoGenerateFieldsForModel) {
		this.autoGenerateColumns = autoGenerateFieldsForModel;
	}

	@ConfigProperty
	public void setAutoGeneratedFieldNames(Set<String> autoGeneratedFieldsForModel) {
		this.autoGeneratedFieldNames = autoGeneratedFieldsForModel;
	}

    @ConfigProperty
    public void setModelCrossContext(boolean modelCrossContext) {
        this.modelCrossContext = modelCrossContext;
    }

    @Override
    public String getTableNamingStyle() {
	    return tableNamingStyle;
    }
	
	@ConfigProperty
	public void setTableNamingStyle(String tableNamingStyle) {
		this.tableNamingStyle = tableNamingStyle;
	}

	@Override
    public String getColumnNamingStyle() {
	    return columnNamingStyle;
    }
	
	@ConfigProperty
	public void setColumnNamingStyle(String columnNamingStyle) {
		this.columnNamingStyle = columnNamingStyle;
	}

    @Override
    public String getDefaultSerializer() {
        return defaultSerializer;
    }

    @ConfigProperty
    public void setDefaultSerializer(String defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public SerializeConfig getDefaultSerializeConfig() {
        return defaultSerializeConfig;
    }

    @Override
    public SerializeConfig getSerializeConfig(String name) throws ObjectNotFoundException {
        SerializeConfig sc = serializeConfigs.get(name.toLowerCase());
        if(null == sc) {
            throw new ObjectNotFoundException("Serializer config of format'" + name + "' not found");
        }
        return sc;
    }

    @Override
    public Map<String, SerializeConfig> getSerializeConfigs() {
        return serializeConfigsImv;
    }

    @Override
    public void postConfigure(BeanFactory factory, AppConfig config) throws Throwable {
        OrmConfigExtension extension = config.getExtension(OrmConfigExtension.class);

        extension.getSerializeConfigs().forEach((format, c) -> {
            serializeConfigs.put(format, c);
        });

        defaultSerializeConfig = serializeConfigs.get(defaultSerializer.toLowerCase());
    }

    protected class DefaultFilterColumnConfig implements FilterColumnConfig {

        protected boolean    enabled = true;
        protected Expression filteredIf;

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        @ConfigProperty
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public Expression getFilteredIf() {
            return filteredIf;
        }

        @ConfigProperty
        public void setFilteredIf(Expression filteredIf) {
            this.filteredIf = filteredIf;
        }

        public void setFilteredIfByString(String expr) {
            expr = EL.removePrefixAndSuffix(expr);
            this.filteredIf = beanFactory.getBean(ExpressionLanguage.class).createExpression(expr);
        }
    }

    protected class DefaultQueryFilterConfig implements QueryFilterConfig {

        protected boolean enabled;
        protected String  tagName = "filter";
        protected String  alias   = "t";

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        @ConfigProperty
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String getTagName() {
            return tagName;
        }

        @ConfigProperty
        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public String getAlias() {
            return alias;
        }

        @ConfigProperty
        public void setAlias(String alias) {
            this.alias = alias;
        }
    }
}