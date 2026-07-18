package com.example.todoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import com.example.todoapp.service.TodoService;

@Controller
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service){
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("tasks", service.getAllTasks());
        model.addAttribute("total", service.totalTasks());
        model.addAttribute("completed", service.completedTasks());
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam String task,
                          @RequestParam String category,
                          @RequestParam String priority,
                          @RequestParam(required = false) String dueDate) {

        LocalDate date = null;

        if (dueDate != null && !dueDate.isBlank()) {
            date = LocalDate.parse(dueDate);
        }

        service.addTask(task, category, priority, date);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){
        service.deleteTask(id);
        return "redirect:/";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTask(@PathVariable Long id){
        service.toggleTask(id);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id,
                             @RequestParam String task){
        service.updateTask(id,task);
        return "redirect:/";
    }
}