package com.gailo22.todo.todo.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTodoCommand {

    @TargetAggregateIdentifier
    private String todoId;
    private String title;
    private boolean completed;
    private int order;
}
