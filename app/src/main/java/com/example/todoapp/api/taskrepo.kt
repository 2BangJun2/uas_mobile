package com.example.todoapp.api


import android.content.Context
import com.example.todoapp.TaskItem
import com.example.todoapp.dao.TaskDAO

class taskrepo(context: Context) {
    private val taskDAO = TaskDAO(context)

    init {
        taskDAO.open()
    }

    fun getTasks(): List<TaskItem> {
        return taskDAO.getAllTasks()
    }

    fun addTask(task: TaskItem) {
        taskDAO.addTask(task)
    }

    fun updateTask(task: TaskItem) {
        taskDAO.updateTask(task)
    }

    fun deleteTask(task: TaskItem) {
        taskDAO.deleteTask(task)
    }

    fun close() {
        taskDAO.close()
    }
}
