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

package org.apache.inlong.manager.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class ScheduleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    // inLong group id
    private String inlongGroupId;
    // schedule type, support [normal, crontab], 0 for normal and 1 for crontab
    private Integer scheduleType;
    // schedule engine type, support [Quartz, Airflow, DolphinScheduler]
    private String scheduleEngine;
    // time unit for offline task schedule interval, support [month, week, day, hour, minute, oneround]
    // Y=year, M=month, W=week, D=day, H=hour, I=minute, O=oneround
    private String scheduleUnit;
    private Integer scheduleInterval;
    // schedule start time, long type timestamp
    private Timestamp startTime;
    // schedule end time, long type timestamp
    private Timestamp endTime;
    // delay time to start task, in minutes
    private Integer delayTime;
    // if task depend on itself
    private Integer selfDepend;
    private Integer taskParallelism;
    // expression of crontab, used when scheduleType is crontab
    private String crontabExpression;

    private Integer status;
    private Integer previousStatus;
    private Integer isDeleted;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date modifyTime;
    private Integer version;

}
