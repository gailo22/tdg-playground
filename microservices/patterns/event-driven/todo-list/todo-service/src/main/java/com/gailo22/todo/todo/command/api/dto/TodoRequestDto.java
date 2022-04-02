package com.gailo22.todo.todo.command.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequestDto {
    private String title;
    private boolean completed;
    private int order;
}
