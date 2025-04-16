package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.TodoItem;
import com.example.demo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService todoService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetAllTodos() throws Exception {
		TodoItem todo = new TodoItem();
		todo.setId(1L);
		todo.setTitle("Buy milk");
		todo.setCompleted(false);

		when(todoService.getAllTodos()).thenReturn(Arrays.asList(todo));

		mockMvc.perform(get("/api/todos")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Buy milk"));
	}

	@Test
	void testCreateTodo() throws Exception {
		TodoItem todo = new TodoItem();
		todo.setId(1L);
		todo.setTitle("New Task");
		todo.setCompleted(false);

		when(todoService.createTodo(any(TodoItem.class))).thenReturn(todo);

		mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todo))).andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("New Task"));
	}

	@Test
	void testUpdateTodo() throws Exception {
		Long id = 1L;
		TodoItem updated = new TodoItem();
		updated.setId(id);
		updated.setTitle("Updated Task");
		updated.setCompleted(true);

		when(todoService.updateTodo(eq(id), any(TodoItem.class))).thenReturn(updated);

		mockMvc.perform(put("/api/todos/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updated))).andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Updated Task")).andExpect(jsonPath("$.completed").value(true));
	}

	@Test
	void testDeleteTodo() throws Exception {
		Long id = 1L;
		doNothing().when(todoService).deleteTodo(id);

		mockMvc.perform(delete("/api/todos/{id}", id)).andExpect(status().isOk());

		verify(todoService, times(1)).deleteTodo(id);
	}
}
