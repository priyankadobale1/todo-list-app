package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoItem;
import com.example.demo.repository.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public List<TodoItem> getAllTodos() {
		log.info("Fetching all todo items");
		List<TodoItem> todos = todoRepository.findAll();
		log.debug("Fetched {} todo items", todos.size());
		return todos;
	}

	@Override
	public TodoItem createTodo(TodoItem todo) {
		log.info("Creating a new todo: {}", todo.getTitle());
		TodoItem savedTodo = todoRepository.save(todo);
		log.debug("Created todo with ID: {}", savedTodo.getId());
		return savedTodo;
	}

	@Override
	public void deleteTodo(Long id) {
		log.info("Deleting todo with ID: {}", id);
		todoRepository.deleteById(id);
		log.debug("Deleted todo with ID: {}", id);
	}

	@Override
	public TodoItem updateTodo(Long id, TodoItem updatedTodo) {
		log.info("Updating todo with ID: {}", id);
		TodoItem existingTodo = todoRepository.findById(id)
				.orElseThrow(() -> {
					log.error("Todo not found with ID: {}", id);
					return new RuntimeException("Todo not found with id: " + id);
				});

		existingTodo.setTitle(updatedTodo.getTitle());
		existingTodo.setCompleted(updatedTodo.isCompleted());

		TodoItem savedTodo = todoRepository.save(existingTodo);
		log.debug("Updated todo with ID: {}", savedTodo.getId());
		return savedTodo;
	}
}
