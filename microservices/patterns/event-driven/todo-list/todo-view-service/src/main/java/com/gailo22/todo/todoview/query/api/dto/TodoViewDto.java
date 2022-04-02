package com.gailo22.todo.todoview.query.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoViewDto {
    private String todoId;
    private String title;
    private boolean completed;
    private int executionOrder;
}
