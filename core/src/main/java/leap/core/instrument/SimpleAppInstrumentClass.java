/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package leap.core.instrument;

import java.util.LinkedHashSet;
import java.util.Set;

public class SimpleAppInstrumentClass implements AppInstrumentClass {

    private final String  className;
    private final String  internalClassName;
    private byte[]        classData;
    private boolean       ensure;
    private Set<Class<?>> instrumentedBySet = new LinkedHashSet<>(2);

    public SimpleAppInstrumentClass(String internalClassName, byte[] classData, boolean ensure) {
        this.internalClassName = internalClassName;
        this.className         = internalClassName.replace('/', '.');
        this.classData = classData;
        this.ensure    = ensure;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getInternalClassName() {
        return internalClassName;
    }

    @Override
    public byte[] getClassData() {
        return classData;
    }

    @Override
    public boolean isEnsure() {
        return ensure;
    }

    @Override
    public void makeEnsure() {
        this.ensure = true;
    }

    @Override
    public void updateClassData(byte[] data) {
        classData = data;
    }

    @Override
    public Set<Class<?>> getAllInstrumentedBy() {
        return instrumentedBySet;
    }

    @Override
    public void addInstrumentedBy(Class<?> cls) {
        instrumentedBySet.add(cls);
    }
}