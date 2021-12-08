/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.inlong.agent.plugin.metrics;

import org.apache.inlong.agent.metrics.Metric;
import org.apache.inlong.commons.config.metrics.MetricItem;
import org.apache.inlong.commons.config.metrics.MetricRegister;

import java.util.concurrent.atomic.AtomicLong;

public class SourceJmxMetric extends MetricItem implements SourceMetrics {

    @Metric
    private final AtomicLong sourceSuccessCounter = new AtomicLong(0);

    @Metric
    private final AtomicLong sourceFailCounter = new AtomicLong(0);

    public SourceJmxMetric() {
        MetricRegister.register(this);
    }

    @Override
    public void incSourceSuccessCount() {
        sourceSuccessCounter.incrementAndGet();
    }

    @Override
    public long getSourceSuccessCount() {
        return sourceSuccessCounter.get();
    }

    @Override
    public void incSourceFailCount() {
        sourceFailCounter.incrementAndGet();
    }

    @Override
    public long getSourceFailCount() {
        return sourceFailCounter.get();
    }
}
