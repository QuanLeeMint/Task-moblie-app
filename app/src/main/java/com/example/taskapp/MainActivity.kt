package com.example.taskapp
import java.util.Date
import android.app.DatePickerDialog
import android.widget.EditText
import android.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddTask: Button
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ view
        buttonAddTask = findViewById(R.id.buttonAddTask)
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)

        // Thiết lập RecyclerView
        taskAdapter = TaskAdapter(taskList)
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter

        // Sự kiện nút thêm công việc
        buttonAddTask.setOnClickListener {
            showAddTaskDialog()

        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name -> {
                taskList.sortBy { it.name }
                taskAdapter.notifyDataSetChanged()
                true
            }
            R.id.sort_by_date -> {
                taskList.sortBy { it.dueDate }
                taskAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editTextTaskName = dialogView.findViewById<EditText>(R.id.editTextTaskName)
        val buttonPickDate = dialogView.findViewById<Button>(R.id.buttonPickDate)
        val textViewSelectedDate = dialogView.findViewById<TextView>(R.id.textViewSelectedDate)

        var selectedDate: Date? = null

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val taskName = editTextTaskName.text.toString()
                if (taskName.isNotBlank() && selectedDate != null) {
                    val newTask = Task(taskName, selectedDate!!)
                    taskList.add(newTask)
                    taskAdapter.notifyItemInserted(taskList.size - 1)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        buttonPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                textViewSelectedDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate!!)
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))

            datePicker.show()
        }

        dialog.show()
    }


}
