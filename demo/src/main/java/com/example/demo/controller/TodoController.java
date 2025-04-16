package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TodoItem;
import com.example.demo.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/todos")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping
	public List<TodoItem> getAllTodos() {
		log.info("GET request received: Fetching all todo items");
		return todoService.getAllTodos();
	}

	@PostMapping
	public TodoItem createTodo(@RequestBody TodoItem todoItem) {
		log.info("POST request received: Creating todo - {}", todoItem);
		return todoService.createTodo(todoItem);
	}

	@PutMapping("/{id}")
	public TodoItem updateTodo(@PathVariable Long id, @RequestBody TodoItem todoItem) {
		log.info("PUT request received: Updating todo with id {} - {}", id, todoItem);
		return todoService.updateTodo(id, todoItem);
	}

	@DeleteMapping("/{id}")
	public void deleteTodo(@PathVariable Long id) {
		log.info("DELETE request received: Deleting todo with id {}", id);
		todoService.deleteTodo(id);
	}
}
