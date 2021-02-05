package com.gailo22.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.gailo22.todoapp.data.TodoDao
import com.gailo22.todoapp.data.models.TodoData

class TodoRepository(private val todoDao: TodoDao) {

    fun getAllData(): LiveData<List<TodoData>> = todoDao.getAllData()

    suspend fun insertData(todoData: TodoData) {
        todoDao.insertData(todoData)
    }

    suspend fun updateData(todoData: TodoData) {
        todoDao.updateData(todoData)
    }

    suspend fun deleteItem(todoData: TodoData) {
        todoDao.deleteItem(todoData)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

}