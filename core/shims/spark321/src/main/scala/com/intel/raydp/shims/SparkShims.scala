/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.raydp.shims.spark321

import org.apache.spark.{SparkEnv, TaskContext}
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.executor.RayDPExecutorBackendFactory
import org.apache.spark.executor.spark321._
import org.apache.spark.spark321.TaskContextUtils
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.spark321.SparkSqlUtils

import com.intel.raydp.shims.{ShimDescriptor, SparkShims}

class Spark321Shims extends SparkShims {
  override def getShimDescriptor: ShimDescriptor = SparkShimProvider.DESCRIPTOR

  override def toDataFrame(
      rdd: JavaRDD[Array[Byte]],
      schema: String,
      session: SparkSession): DataFrame = {
    SparkSqlUtils.toDataFrame(rdd, schema, session)
  }

  override def getExecutorBackendFactory(): RayDPExecutorBackendFactory = {
    new RayDPSpark321ExecutorBackendFactory()
  }

  override def getDummyTaskContext(partitionId: Int, env: SparkEnv): TaskContext = {
    TaskContextUtils.getDummyTaskContext(partitionId, env)
  }
}
