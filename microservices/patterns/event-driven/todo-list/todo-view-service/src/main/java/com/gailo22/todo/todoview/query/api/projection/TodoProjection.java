package com.gailo22.todo.todoview.query.api.projection;

import com.gailo22.todo.todoview.query.api.data.Todo;
import com.gailo22.todo.todoview.query.api.data.TodoRepository;
import com.gailo22.todo.todoview.query.api.dto.TodoViewDto;
import com.gailo22.todo.todoview.query.api.queries.GetTodosQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoProjection {

    private TodoRepository todoRepository;

    public TodoProjection(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @QueryHandler
    public List<TodoViewDto> handle(GetTodosQuery getTodosQuery) {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(it -> new TodoViewDto(it.getTodoId(), it.getTitle(), it.isCompleted(), it.getExecutionOrder()))
                .collect(Collectors.toList());
    }
}
