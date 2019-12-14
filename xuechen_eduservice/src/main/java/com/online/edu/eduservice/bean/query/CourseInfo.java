package com.online.edu.eduservice.bean.query;

import java.math.BigDecimal;
import java.util.List;

public class CourseInfo  {
    private String title;
    private Integer lessonNum;//课程时长
    private BigDecimal price;//价格
    private List<String> subject;//分类
    private String teacher;//讲师
    private String description; //课程简介
    private String cover;//课程封面

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "title='" + title + '\'' +
                ", lessonNum='" + lessonNum + '\'' +
                ", price=" + price +
                ", subject=" + subject +
                ", teacher='" + teacher + '\'' +
                ", description='" + description + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
