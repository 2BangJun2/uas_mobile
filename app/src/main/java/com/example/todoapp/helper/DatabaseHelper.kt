package com.example.todoapp.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_TASKS = "tasks"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESC = "description"
        const val COLUMN_DUE_TIME = "due_time"
        const val COLUMN_COMPLETED_DATE = "completed_date"

        private const val TABLE_CREATE = """
            CREATE TABLE $TABLE_TASKS (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT,
                $COLUMN_DESC TEXT,
                $COLUMN_DUE_TIME TEXT,
                $COLUMN_COMPLETED_DATE TEXT
            );
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }
}
