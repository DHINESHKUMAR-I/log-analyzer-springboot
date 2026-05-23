package com.example.LogAnalyzer.service;

import com.example.LogAnalyzer.Log;
import com.example.LogAnalyzer.dto.LogReport;
import com.example.LogAnalyzer.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public Log saveLog(Log log) {

        // Detect log type
        if (log.getMessage().contains("ERROR")) {
            log.setType("ERROR");
        } else {
            log.setType("INFO");
        }

        // Assign priority
        if (log.getMessage().contains("Database")
                || log.getMessage().contains("Server")) {

            log.setPriority("CRITICAL");

        } else {
            log.setPriority("NORMAL");
        }

        // Save into database
        return logRepository.save(log);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public List<Log> getErrorLogs() {
        return logRepository.findByType("ERROR");
    }

    public List<Log> getCriticalLogs() {
        return logRepository.findByPriority("CRITICAL");
    }

    public long getLogCount() {
        return logRepository.count();
    }

    public String checkAlerts() {

        List<Log> errorLogs = logRepository.findByType("ERROR");

        if (errorLogs.size() >= 5) {
            return "ALERT: Too many ERROR logs detected!";
        }

        return "System is stable.";
    }

    public String getPeakErrorTime() {

        List<Log> errorLogs = logRepository.findByType("ERROR");

        Map<String, Integer> timeCount = new HashMap<>();

        for (Log log : errorLogs) {

            String timestamp = log.getTimestamp();

            // Extract hour part
            String hour = timestamp.substring(11, 13);

            timeCount.put(hour,
                    timeCount.getOrDefault(hour, 0) + 1);
        }

        String peakHour = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : timeCount.entrySet()) {

            if (entry.getValue() > maxCount) {

                peakHour = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        if (peakHour.isEmpty()) {
            return "No ERROR logs available.";
        }

        return "Peak error time: " + peakHour + ":00 with "
                + maxCount + " errors.";
    }

    public void processLogFile(MultipartFile file) throws IOException {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(file.getInputStream()));

        String line;

        while ((line = reader.readLine()) != null) {

            Log log = new Log();

            log.setMessage(line);

            // Extract timestamp
            String timestamp = line.substring(0, 16);
            log.setTimestamp(timestamp);

            // Detect type
            if (line.contains("ERROR")) {
                log.setType("ERROR");
            } else {
                log.setType("INFO");
            }

            // Assign priority
            if (line.contains("Database")
                    || line.contains("Server")) {

                log.setPriority("CRITICAL");

            } else {
                log.setPriority("NORMAL");
            }

            logRepository.save(log);
        }
    }

    public LogReport generateReport() {

        LogReport report = new LogReport();

        report.setTotalLogs(logRepository.count());

        report.setTotalErrors(
                logRepository.findByType("ERROR").size());

        report.setCriticalLogs(
                logRepository.findByPriority("CRITICAL").size());

        report.setAlertStatus(checkAlerts());

        report.setPeakErrorTime(getPeakErrorTime());

        return report;
    }
}