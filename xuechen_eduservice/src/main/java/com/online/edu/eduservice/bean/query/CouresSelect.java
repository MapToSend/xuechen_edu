package com.online.edu.eduservice.bean.query;

/**
 * 下拉框对象
 */
public class CouresSelect {

    private String name;
    private String teacher;

    private String teacherId;
    public String getName() {
        return name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public CouresSelect() {
    }

    public CouresSelect(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
