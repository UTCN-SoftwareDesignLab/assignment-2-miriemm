package com.assignment2.report;

public interface ReportService {
    byte[] export();

    ReportType getType();
}
