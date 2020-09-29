package com.udacity.jwdnd.course1.cloudstorage.model;

public class ResponseFile {
    private String url;
    private int fileid;
    private String filename;

    public ResponseFile(String url, int fileid, String filename) {
        this.url = url;
        this.fileid = fileid;
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
