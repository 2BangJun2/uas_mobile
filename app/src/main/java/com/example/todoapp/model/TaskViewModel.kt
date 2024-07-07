package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.api.taskrepo
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    var taskItems = MutableLiveData<MutableList<TaskItem>?>()
    private val taskRepository = taskrepo(application.applicationContext)

    init {
        fetchTasks()
    }

    override fun onCleared() {
        super.onCleared()
        taskRepository.close()
    }

    private fun fetchTasks() {
        taskItems.value = taskRepository.getTasks().toMutableList()
    }

    fun addTaskItem(newTask: TaskItem) {
        taskRepository.addTask(newTask)
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }

    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?) {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.desc = desc
        task.dueTime = dueTime
        taskRepository.updateTask(task)
        taskItems.postValue(list)
    }

    fun setCompleted(taskItem: TaskItem) {
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!
        if (task.completedDate == null)
            task.completedDate = LocalDate.now()
        taskRepository.updateTask(task)
        taskItems.postValue(list)
    }

    fun unsetCompleted(taskItem: TaskItem) {
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!
        task.completedDate = null
        taskRepository.updateTask(task)
        taskItems.postValue(list)
    }

    fun deleteTaskItem(taskItem: TaskItem) {
        taskRepository.deleteTask(taskItem)
        val list = taskItems.value
        list!!.remove(taskItem)
        taskItems.postValue(list)
    }
}
