package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private int fileid;
    private String filename;
    private String contenttype;
    private String filesize;
    private byte[] filedata;

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public int getFileid() {
        return fileid;
    }

    public String getFilename() {
        return filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public File() {
    }

    public File(int fileid, String filename, String contenttype, String filesize, byte[] filedata) {
        this.fileid = fileid;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.filedata = filedata;
    }
}
