package com.online.edu.eduservice.bean.query;

/**
 * 分类节点对象
 */
public class SubjectSelect_2 {


    private String value;
    private String label;

    private String videoSourceId;

    public String getVideoSourceId() {
        return videoSourceId;
    }

    public void setVideoSourceId(String videoSourceId) {
        this.videoSourceId = videoSourceId;
    }

    public SubjectSelect_2(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public SubjectSelect_2() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
