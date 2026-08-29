package com.synechisveltiosi.apis.app365.election.dto;

import com.synechisveltiosi.apis.app365.election.ElectionType;

import java.time.LocalDate;
import java.time.LocalTime;

public class ElectionResponse {

    private String id;
    private ElectionType type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ElectionType getType() {
        return type;
    }

    public void setType(ElectionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
