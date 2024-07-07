package com.example.todoapp

interface TaskItemClickListener {
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
    fun deleteTaskItem(taskItem: TaskItem)
    fun uncompleteTaskItem(taskItem: TaskItem)
}
