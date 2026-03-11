package com.example.listatarefas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onTaskCompleted: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textTaskName)
        val textDescription: TextView = itemView.findViewById(R.id.textTaskDescription)
        val textStatus: TextView = itemView.findViewById(R.id.textTaskStatus)
        val btnComplete: Button = itemView.findViewById(R.id.btnComplete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.textName.text = task.name
        holder.textDescription.text = task.description
        holder.textStatus.text = if (task.isCompleted) "Status: Concluída" else "Status: Pendente"

        if (task.isCompleted) {
            holder.textName.setTextColor(android.graphics.Color.parseColor("#388E3C"))
            holder.textStatus.setTextColor(android.graphics.Color.parseColor("#388E3C"))
            holder.btnComplete.isEnabled = false
            holder.btnComplete.text = "Concluída"
        } else {
            holder.textName.setTextColor(android.graphics.Color.BLACK)
            holder.textStatus.setTextColor(android.graphics.Color.parseColor("#D32F2F"))
            holder.btnComplete.isEnabled = true
            holder.btnComplete.text = "Concluir"
        }

        holder.btnComplete.setOnClickListener {
            if (!task.isCompleted) {
                task.isCompleted = true
                onTaskCompleted(task)
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: MutableList<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}