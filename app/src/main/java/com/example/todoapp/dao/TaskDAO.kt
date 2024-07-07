package com.example.todoapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.todoapp.TaskItem
import com.example.todoapp.helper.DatabaseHelper
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun addTask(task: TaskItem) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_ID, task.id.toString())
            put(DatabaseHelper.COLUMN_NAME, task.name)
            put(DatabaseHelper.COLUMN_DESC, task.desc)
            put(DatabaseHelper.COLUMN_DUE_TIME, task.dueTime?.toString())
            put(DatabaseHelper.COLUMN_COMPLETED_DATE, task.completedDate?.toString())
        }
        database.insert(DatabaseHelper.TABLE_TASKS, null, values)
    }

    fun updateTask(task: TaskItem) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, task.name)
            put(DatabaseHelper.COLUMN_DESC, task.desc)
            put(DatabaseHelper.COLUMN_DUE_TIME, task.dueTime?.toString())
            put(DatabaseHelper.COLUMN_COMPLETED_DATE, task.completedDate?.toString())
        }
        database.update(DatabaseHelper.TABLE_TASKS, values, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(task.id.toString()))
    }

    fun deleteTask(task: TaskItem) {
        database.delete(DatabaseHelper.TABLE_TASKS, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(task.id.toString()))
    }

    fun getAllTasks(): List<TaskItem> {
        val tasks = mutableListOf<TaskItem>()
        val cursor = database.query(
            DatabaseHelper.TABLE_TASKS,
            arrayOf(
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_DESC,
                DatabaseHelper.COLUMN_DUE_TIME,
                DatabaseHelper.COLUMN_COMPLETED_DATE
            ),
            null, null, null, null, null
        )

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val task = cursorToTask(cursor)
            tasks.add(task)
            cursor.moveToNext()
        }
        cursor.close()
        return tasks
    }

    private fun cursorToTask(cursor: Cursor): TaskItem {
        return TaskItem(
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESC)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DUE_TIME))?.let { LocalTime.parse(it) },
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COMPLETED_DATE))?.let { LocalDate.parse(it) },
            UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)))
        )
    }
}
