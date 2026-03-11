package com.example.listatarefas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var editTaskName: EditText
    private lateinit var editTaskDescription: EditText
    private lateinit var btnAddTask: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    private val tasks = mutableListOf<Task>()
    private var nextId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun initializeViews() {
        editTaskName = findViewById(R.id.editTaskName)
        editTaskDescription = findViewById(R.id.editTaskDescription)
        btnAddTask = findViewById(R.id.btnAddTask)
        recyclerView = findViewById(R.id.recyclerViewTasks)
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(tasks) { completedTask ->
            Toast.makeText(
                this,
                "Tarefa '${completedTask.name}' concluída!",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
    }

    private fun setupListeners() {
        btnAddTask.setOnClickListener {
            addNewTask()
        }
    }

    private fun addNewTask() {
        val taskName = editTaskName.text.toString().trim()
        val taskDescription = editTaskDescription.text.toString().trim()

        when {
            taskName.isEmpty() -> {
                editTaskName.error = "Digite o nome da tarefa"
                return
            }
            taskDescription.isEmpty() -> {
                editTaskDescription.error = "Digite a descrição da tarefa"
                return
            }
        }

        val newTask = Task(
            id = nextId++,
            name = taskName,
            description = taskDescription,
            isCompleted = false
        )

        tasks.add(newTask)
        taskAdapter.updateTasks(tasks)

        editTaskName.text.clear()
        editTaskDescription.text.clear()

        recyclerView.smoothScrollToPosition(tasks.size - 1)

        Toast.makeText(this, "Tarefa adicionada!", Toast.LENGTH_SHORT).show()
    }
}