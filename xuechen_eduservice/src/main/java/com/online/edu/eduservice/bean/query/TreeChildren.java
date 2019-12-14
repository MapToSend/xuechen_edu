package com.online.edu.eduservice.bean.query;

import java.util.ArrayList;

/**
 * 课程节点树对象
 */
public class TreeChildren {

    //节点名
    private String label;

    //子节点
    private ArrayList<TreeChildren> children=new ArrayList<>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "TreeChildren{" +
                "label='" + label + '\'' +
                ", children=" + children +
                '}';
    }

    public ArrayList<TreeChildren> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeChildren> children) {
        this.children = children;
    }
}
