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

package leap.core.ioc;

import leap.core.AppContext;
import leap.core.CoreTestCase;
import org.junit.Test;

public class BeanIfTest extends CoreTestCase {

    @Test
    public void testIfExpression(){
        assertNotNull(beanFactory.tryGetBean("testIfBeanTrue1"));
        assertNotNull(beanFactory.tryGetBean("testIfBeanTrue2"));
        assertNotNull(beanFactory.tryGetBean("testIfBeanTrue3"));

        assertNull(beanFactory.tryGetBean("testIfBeanFalse1"));
        assertNull(beanFactory.tryGetBean("testIfBeanFalse2"));
    }

    @Test
    public void testIfProfile(){
        assertNull(AppContext.factory().tryGetBean("testProfile.shouldNotCreated"));
        assertNotNull(AppContext.factory().getBean("testProfile.shouldBeCreated"));
    }

}
