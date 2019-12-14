package com.online.edu.eduservice.bean.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节对象
 */

public class Chapter  implements Comparable {

    private String id;
    private String title;
    private String sort;
    private String videoId;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private List<Chapter> chapters=new ArrayList<>();

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", sort='" + sort + '\'' +
                ", chapters=" + chapters +
                '}';
    }

    public Chapter(String title, String sort) {
        this.title = title;
        this.sort = sort;
    }

    public Chapter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }





    @Override
    public int compareTo(Object o) {
          Chapter chapter= (Chapter) o;


        return Integer.parseInt(this.getSort())-Integer.parseInt(chapter.getSort());
    }
}
