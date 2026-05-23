package com.example.LogAnalyzer.dto;

public class LogReport {

    private long totalLogs;
    private long totalErrors;
    private long criticalLogs;

    private String alertStatus;
    private String peakErrorTime;

    public long getTotalLogs() {
        return totalLogs;
    }

    public void setTotalLogs(long totalLogs) {
        this.totalLogs = totalLogs;
    }

    public long getTotalErrors() {
        return totalErrors;
    }

    public void setTotalErrors(long totalErrors) {
        this.totalErrors = totalErrors;
    }

    public long getCriticalLogs() {
        return criticalLogs;
    }

    public void setCriticalLogs(long criticalLogs) {
        this.criticalLogs = criticalLogs;
    }

    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }

    public String getPeakErrorTime() {
        return peakErrorTime;
    }

    public void setPeakErrorTime(String peakErrorTime) {
        this.peakErrorTime = peakErrorTime;
    }
}