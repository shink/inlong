/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.agent.plugin.channel;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.inlong.agent.conf.JobProfile;
import org.apache.inlong.agent.constants.AgentConstants;
import org.apache.inlong.agent.plugin.Channel;
import org.apache.inlong.agent.plugin.Message;
import org.apache.inlong.agent.plugin.metrics.PluginJmxMetric;
import org.apache.inlong.agent.plugin.metrics.PluginMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryChannel implements Channel {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryChannel.class);

    private LinkedBlockingQueue<Message> queue;

    private final PluginMetric pluginMetricNew = new PluginJmxMetric("AgentMemoryPlugin");

    /**
     * {@inheritDoc}
     */
    @Override
    public void push(Message message) {
        try {
            if (message != null) {
                pluginMetricNew.incReadNum();
                queue.put(message);
                pluginMetricNew.incReadSuccessNum();
            }
        } catch (InterruptedException ex) {
            pluginMetricNew.incReadFailedNum();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean push(Message message, long timeout, TimeUnit unit) {
        try {
            if (message != null) {
                pluginMetricNew.incReadNum();
                boolean result = queue.offer(message, timeout, unit);
                if (result) {
                    pluginMetricNew.incReadSuccessNum();
                } else {
                    pluginMetricNew.incReadFailedNum();
                }
                return result;
            }
        } catch (InterruptedException ex) {
            pluginMetricNew.incReadFailedNum();
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message pull(long timeout, TimeUnit unit) {
        try {
            Message message = queue.poll(timeout, unit);
            if (message != null) {
                pluginMetricNew.incSendSuccessNum();
            }
            return message;
        } catch (InterruptedException ex) {
            pluginMetricNew.incSendFailedNum();
            Thread.currentThread().interrupt();
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void init(JobProfile jobConf) {
        queue = new LinkedBlockingQueue<>(
                jobConf.getInt(AgentConstants.CHANNEL_MEMORY_CAPACITY,
                    AgentConstants.DEFAULT_CHANNEL_MEMORY_CAPACITY));
    }

    @Override
    public void destroy() {
        if (queue != null) {
            queue.clear();
        }
        LOGGER.info("destroy channel, memory channel metric, readNum: {}, readSuccessNum: {}, "
            + "readFailedNum: {}, sendSuccessNum: {}, sendFailedNum: {}",
            pluginMetricNew.getReadNum(), pluginMetricNew.getReadSuccessNum(),
            pluginMetricNew.getReadFailedNum(), pluginMetricNew.getSendSuccessNum(),
            pluginMetricNew.getSendFailedNum());
    }
}
