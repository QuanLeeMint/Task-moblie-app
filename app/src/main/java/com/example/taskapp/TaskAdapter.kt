package com.example.taskapp
import android.widget.CheckBox
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.textViewTaskName)
        val taskDate: TextView = view.findViewById(R.id.textViewTaskDate)
        val checkBoxDone: CheckBox = view.findViewById(R.id.checkBoxDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.taskDate.text = formatter.format(task.dueDate)
        holder.checkBoxDone.isChecked = task.isDone

        holder.checkBoxDone.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = tasks.size
}

