package com.example.attandanceapp;

public class ClassItem {
    private String className;
    private String subjeetName;

    public ClassItem(String className, String subjeetName) {
        this.className = className;
        this.subjeetName = subjeetName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjeetName() {
        return subjeetName;
    }

    public void setSubjeetName(String subjeetName) {
        this.subjeetName = subjeetName;
    }
}
