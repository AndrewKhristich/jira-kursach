package com.jira.demo.repository;

import com.jira.demo.model.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    List<Task> findAll();
}
