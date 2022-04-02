package com.gailo22.todo.todo.command.api.controller;

import com.gailo22.todo.todo.command.api.commands.CreateTodoCommand;
import com.gailo22.todo.todo.command.api.dto.TodoRequestDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/todos")
public class TodoCommandController {

    private CommandGateway commandGateway;

    public TodoCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createToto(@RequestBody TodoRequestDto dto) {
        CreateTodoCommand createTodoCommand = new CreateTodoCommand(
                UUID.randomUUID().toString(),
                dto.getTitle(),
                dto.isCompleted(),
                dto.getOrder());

        String result = commandGateway.sendAndWait(createTodoCommand);
        return result;
    }
}
