/*
 * Copyright 2021 Sergio Belda Galbis
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
 */

package com.sergiobelda.androidtodometer.data.localdatasource

import com.sergiobelda.androidtodometer.data.database.dao.TaskDao
import com.sergiobelda.androidtodometer.data.database.entity.TaskEntity
import com.sergiobelda.androidtodometer.data.database.mapper.TaskMapper.toDomain
import com.sergiobelda.androidtodometer.domain.model.Tag
import com.sergiobelda.androidtodometer.domain.model.TaskState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskLocalDataSourceTest {

    @MockK
    private val taskDao = mockk<TaskDao>()

    private val taskLocalDataSource = TaskLocalDataSource(taskDao)

    @Test
    fun testGetTask() = runBlocking {
        val taskEntity = TaskEntity(1, "", "", TaskState.DOING, 1, Tag.GRAY)

        coEvery { taskDao.getTask(1) } returns flow {
            emit(taskEntity)
        }

        assertEquals(taskEntity.toDomain(), taskLocalDataSource.getTask(1).firstOrNull())
    }
}
