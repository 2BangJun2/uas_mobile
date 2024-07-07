package com.example.todoapp

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name
        binding.desc.text = taskItem.desc

        if (taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.desc.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.name.paintFlags = 0
            binding.dueTime.paintFlags = 0
            binding.desc.paintFlags = 0
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener {
            if (taskItem.isCompleted()) {
                clickListener.uncompleteTaskItem(taskItem)
            } else {
                clickListener.completeTaskItem(taskItem)
            }
        }

        binding.deleteButton.setOnClickListener {
            clickListener.deleteTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        if (taskItem.dueTime != null)
            binding.dueTime.text = timeFormat.format(taskItem.dueTime)
        else
            binding.dueTime.text = ""
    }
}
