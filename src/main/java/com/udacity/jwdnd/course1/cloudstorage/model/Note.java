package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    public Integer noteid;
    public String notetitle;
    public String notedescription;
    public Integer userId;

    public Note(Integer noteid, String notetitle, String notedescription, Integer userId) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userId = userId;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public Integer getUserId() {
        return userId;
    }
}
