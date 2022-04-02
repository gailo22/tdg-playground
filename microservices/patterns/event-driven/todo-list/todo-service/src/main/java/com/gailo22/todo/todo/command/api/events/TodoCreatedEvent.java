package com.gailo22.todo.todo.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoCreatedEvent {
    private String todoId;
    private String title;
    private boolean completed;
    private int order;
}
