package com.gailo22.todo.todo.command.api.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gailo22.todo.todo.command.api.data.Todo;
import com.gailo22.todo.todo.command.api.data.TodoRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("todo")
public class TodoEventsHandler {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @EventHandler
    public void on(TodoCreatedEvent event) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(event, todo);
        todo.setExecutionOrder(event.getOrder());

        todoRepository.save(todo);

        try {
            String todoString = objectMapper.writeValueAsString(todo);
            kafkaTemplate.send("todo-topic", todoString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }

}
