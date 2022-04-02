package com.gailo22.todo.todo.command.api.aggregate;

import com.gailo22.todo.todo.command.api.commands.CreateTodoCommand;
import com.gailo22.todo.todo.command.api.events.TodoCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class TodoAggregate {

    @AggregateIdentifier
    private String todoId;
    private String title;
    private boolean completed;
    private int order;

    public TodoAggregate() {
    }

    @CommandHandler
    public TodoAggregate(CreateTodoCommand command) {
        // do validation here

        TodoCreatedEvent todoCreatedEvent = new TodoCreatedEvent(
                command.getTodoId(),
                command.getTitle(),
                command.isCompleted(),
                command.getOrder());

        AggregateLifecycle.apply(todoCreatedEvent);
    }

    @EventSourcingHandler
    public void on(TodoCreatedEvent todoCreatedEvent) {
        this.todoId = todoCreatedEvent.getTodoId();
        this.title = todoCreatedEvent.getTitle();
        this.completed = todoCreatedEvent.isCompleted();
        this.order = todoCreatedEvent.getOrder();
    }
}
