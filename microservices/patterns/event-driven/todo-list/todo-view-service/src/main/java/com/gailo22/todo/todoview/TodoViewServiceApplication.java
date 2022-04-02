package com.gailo22.todo.todoview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ElasticSearchConfiguration.class, KafkaTopicConfiguration.class, KafkaConsumerConfiguration.class})
public class TodoViewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoViewServiceApplication.class, args);
	}

}
