package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.TodoItem;
import com.example.demo.repository.TodoRepository;

public class TodoServiceImplTest {

	@Mock
	private TodoRepository todoRepository;

	@InjectMocks
	private TodoServiceImpl todoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllTodos() {
		TodoItem item1 = new TodoItem();
		item1.setTitle("Task 1");
		item1.setCompleted(false);

		TodoItem item2 = new TodoItem();
		item2.setTitle("Task 2");
		item2.setCompleted(true);

		when(todoRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

		List<TodoItem> todos = todoService.getAllTodos();

		assertEquals(2, todos.size());
		assertEquals("Task 1", todos.get(0).getTitle());
	}

	@Test
	void testCreateTodo() {
		TodoItem item = new TodoItem();
		item.setTitle("New Task");
		item.setCompleted(false);

		when(todoRepository.save(item)).thenReturn(item);

		TodoItem result = todoService.createTodo(item);

		assertNotNull(result);
		assertEquals("New Task", result.getTitle());
	}

	@Test
	void testDeleteTodo() {
		Long id = 1L;
		doNothing().when(todoRepository).deleteById(id);

		todoService.deleteTodo(id);

		verify(todoRepository, times(1)).deleteById(id);
	}

	@Test
	void testUpdateTodo() {
		Long id = 1L;
		TodoItem existing = new TodoItem();
		existing.setId(id);
		existing.setTitle("Old Title");
		existing.setCompleted(false);

		TodoItem updated = new TodoItem();
		updated.setTitle("Updated Title");
		updated.setCompleted(true);

		when(todoRepository.findById(id)).thenReturn(Optional.of(existing));
		when(todoRepository.save(existing)).thenReturn(existing);

		TodoItem result = todoService.updateTodo(id, updated);

		assertEquals("Updated Title", result.getTitle());
		assertTrue(result.isCompleted());
	}
}
