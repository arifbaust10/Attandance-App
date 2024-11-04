
package com.example.attandanceapp;

public class ClassItem {

    private long cid;

    public ClassItem(long cid, String className, String subjeetName) {
        this.cid = cid;
        this.className = className;
        this.subjeetName = subjeetName;
    }


    private String className;
    private String subjeetName;

    public ClassItem(String className, String subjeetName) {
        this.className = className;
        this.subjeetName = subjeetName;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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
