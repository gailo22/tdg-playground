package com.gailo22.todo.todoview.query.api.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gailo22.todo.todoview.query.api.data.TodoView;
import com.gailo22.todo.todoview.query.api.service.TodoViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TodoEventConsumer {

    @Autowired
    private TodoViewService todoViewService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "todo-topic", groupId = "todo")
    public void listenGroupTodo(String message) {
        System.out.println("Received Message in group todo: " + message);
        try {
            TodoView todoView = objectMapper.readValue(message, TodoView.class);
            todoViewService.index(todoView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
