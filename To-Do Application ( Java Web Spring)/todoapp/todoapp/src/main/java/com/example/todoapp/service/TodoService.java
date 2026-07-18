package com.example.todoapp.service;

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo){
        this.repo = repo;
    }

    // Get all tasks
    public List<Todo> getAllTasks(){
        return repo.findAll();
    }

    // Add new task
    public void addTask(String task, String category, String priority, LocalDate dueDate){

        Todo todo = new Todo();
        todo.setTask(task);
        todo.setCategory(category);
        todo.setPriority(priority);
        todo.setDueDate(dueDate);
        todo.setCompleted(false);

        repo.save(todo);
    }

    // Delete task
    public void deleteTask(Long id){
        repo.deleteById(id);
    }

    // Toggle complete / incomplete
    public void toggleTask(Long id){
        Todo todo = repo.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        repo.save(todo);
    }

    // Update task text
    public void updateTask(Long id, String task){
        Todo todo = repo.findById(id).orElseThrow();
        todo.setTask(task);
        repo.save(todo);
    }

    // Total tasks count
    public long totalTasks(){
        return repo.count();
    }

    // Completed tasks count
    public long completedTasks(){
        return repo.findAll()
                .stream()
                .filter(Todo::isCompleted)
                .count();
    }
}

