package com.gailo22.todo.todoview.query.api.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoViewRepository extends ElasticsearchRepository<TodoView, String> {
    Page<TodoView> findByTitle(String title, Pageable pageable);

//    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
//    Page<TodoView> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);
}
