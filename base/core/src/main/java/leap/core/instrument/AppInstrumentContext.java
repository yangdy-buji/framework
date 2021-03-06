/*
 * Copyright 2015 the original author or authors.
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
package leap.core.instrument;

import java.util.Collection;

public interface AppInstrumentContext {

    /**
     * Returns true class loader for instrumentation.
     */
    ClassLoader getClassLoader();

    /**
     * Returns true if the given class name was instrumented by the class.
     */
    default boolean isInstrumentedBy(String className, Class<?> instrumentedBy) {
        AppInstrumentClass ic = getInstrumentedClass(className);
        return null == ic ? false : ic.getAllInstrumentedBy().contains(instrumentedBy);
    }

    /**
     * Returns the collection contains all the instrumented classes.
     */
    Collection<AppInstrumentClass> getAllInstrumentedClasses();

    /**
     * Returns the instrumented class or null.
     */
    AppInstrumentClass getInstrumentedClass(String internalClassName);

    /**
     * Creates a new {@link AppInstrumentClass}.
     */
    AppInstrumentClass newInstrumentedClass(String internalClassName);

    /**
     * Updates the instrumented class data.
     */
    void updateInstrumented(AppInstrumentClass cls, AppInstrumentProcessor instrumentedBy, byte[] classData, boolean ensure);

}