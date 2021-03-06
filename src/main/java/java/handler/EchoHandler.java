/*
 * Copyright 2012 astamuse company,Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package java.handler;

import org.apache.commons.lang3.StringUtils;

import com.astamuse.asta4d.web.dispatch.request.RequestHandler;

public class EchoHandler {

    @RequestHandler
    public void echo(String value) {
        if (StringUtils.isEmpty(value)) {
            value = "hello!";
        }
        System.out.println("[EchoHandler:echo]" + value);
    }
}
