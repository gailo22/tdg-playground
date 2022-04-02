package com.gailo22.todo.todoview.query.api.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="todos")
@Data
public class TodoView {
    @Id
    private String todoId;
    private String title;
    private boolean completed;
    private int executionOrder;
}
