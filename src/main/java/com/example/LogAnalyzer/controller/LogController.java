package com.example.LogAnalyzer.controller;

import com.example.LogAnalyzer.Log;
import com.example.LogAnalyzer.dto.LogReport;
import com.example.LogAnalyzer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping
    public Log saveLog(@RequestBody Log log) {

        return logService.saveLog(log);
    }

    @GetMapping
    public List<Log> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/errors")
    public List<Log> getErrorLogs() {
        return logService.getErrorLogs();
    }

    @GetMapping("/critical")
    public List<Log> getCriticalLogs() {
        return logService.getCriticalLogs();
    }

    @GetMapping("/count")
    public long getLogCount() {
        return logService.getLogCount();
    }

    @GetMapping("/alert")
    public String getAlerts() {
        return logService.checkAlerts();
    }

    @GetMapping("/peak-time")
    public String getPeakTime() {
        return logService.getPeakErrorTime();
    }

    @PostMapping("/upload")
    public String uploadLogFile(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        logService.processLogFile(file);

        return "Log file processed successfully!";
    }

    @GetMapping("/report")
    public LogReport getReport() {
        return logService.generateReport();
    }
}