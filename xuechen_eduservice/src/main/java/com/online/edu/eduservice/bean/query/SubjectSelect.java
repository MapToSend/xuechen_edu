package com.online.edu.eduservice.bean.query;

import java.util.ArrayList;

/**
 * 课程分类节点对象
 */
public class SubjectSelect {


    private String value;
    private String label;
    private ArrayList<SubjectSelect_2> children=new ArrayList<>();

    public SubjectSelect(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public SubjectSelect() {
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

    public ArrayList<SubjectSelect_2> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<SubjectSelect_2> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "SubjectSelect{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
