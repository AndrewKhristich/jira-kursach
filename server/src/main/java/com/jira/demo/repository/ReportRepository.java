package com.jira.demo.repository;

import com.jira.demo.model.Report;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReportRepository extends PagingAndSortingRepository<Report, Long> {
}
