package com.gailo22.todo.todoview.query.api.controller;

import com.gailo22.todo.todoview.query.api.dto.TodoViewDto;
import com.gailo22.todo.todoview.query.api.queries.GetTodosQuery;
import com.gailo22.todo.todoview.query.api.service.TodoViewService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoQueryController {

    private TodoViewService todoViewService;

    private QueryGateway queryGateway;

    public TodoQueryController(TodoViewService todoViewService, QueryGateway queryGateway) {
        this.todoViewService = todoViewService;
        this.queryGateway = queryGateway;
    }

    @GetMapping("/search")
    public List<TodoViewDto> search(@RequestParam String q) {
        return todoViewService.search(q);
    }

    @GetMapping("")
    public List<TodoViewDto> getAll() {
        GetTodosQuery getTodosQuery = new GetTodosQuery();
        List<TodoViewDto> result = queryGateway.query(getTodosQuery, ResponseTypes.multipleInstancesOf(TodoViewDto.class)).join();
        return result;
    }
}
