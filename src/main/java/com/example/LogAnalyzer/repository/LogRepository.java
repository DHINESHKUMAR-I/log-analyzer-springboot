package com.example.LogAnalyzer.repository;

import com.example.LogAnalyzer.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByType(String type);

    List<Log> findByPriority(String priority);
}