package com.example.demo.service;

import java.util.List;

import com.example.demo.model.TodoItem;

public interface TodoService {

	List<TodoItem> getAllTodos();

	TodoItem createTodo(TodoItem todoItem);

	TodoItem updateTodo(Long id, TodoItem updatedTodo);

	void deleteTodo(Long id);
}
