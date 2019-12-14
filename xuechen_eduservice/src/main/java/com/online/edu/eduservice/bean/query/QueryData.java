package com.online.edu.eduservice.bean.query;

import lombok.Data;

@Data
public class QueryData {


    private String name;
    private String level;
    private String begin;
    private String end;

    @Override
    public String toString() {
        return "QueryData{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
