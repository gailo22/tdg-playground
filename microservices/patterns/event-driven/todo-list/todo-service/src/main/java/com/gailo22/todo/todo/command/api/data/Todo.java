package com.gailo22.todo.todo.command.api.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Todo {
    @Id
    private String todoId;
    private String title;
    private boolean completed;
    private int executionOrder;
}
