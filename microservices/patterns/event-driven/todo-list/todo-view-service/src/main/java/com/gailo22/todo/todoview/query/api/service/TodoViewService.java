package com.gailo22.todo.todoview.query.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gailo22.todo.todoview.query.api.data.TodoView;
import com.gailo22.todo.todoview.query.api.data.TodoViewRepository;
import com.gailo22.todo.todoview.query.api.dto.TodoViewDto;
import org.jboss.jandex.IndexView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@Service
public class TodoViewService {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    private TodoViewRepository todoViewRepository;

    public List<TodoViewDto> search(String value) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(matchQuery("title", value))
                .build();
        SearchHits<TodoView> result = elasticsearchTemplate.search(searchQuery, TodoView.class, IndexCoordinates.of("todos"));

        List<SearchHit<TodoView>> searchHits = result.getSearchHits();
        return searchHits.stream()
                .map(it -> {
                    TodoView content = it.getContent();
                    return new TodoViewDto(content.getTodoId(), content.getTitle(), content.isCompleted(), content.getExecutionOrder());
                }).collect(Collectors.toList());
    }

    public void index(TodoView todoView) {
//        elasticsearchTemplate.indexOps(TodoView.class).create();
        todoViewRepository.save(todoView);
    }
}
