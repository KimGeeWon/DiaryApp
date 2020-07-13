package com.andrstudy.lecture.fcmtest.diaryapp;

public class ListViewItem {
    private String titleStr;
    private String contentStr;
    private String dateStr;
    private String idStr;

    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setContent(String content) { contentStr = content ; }
    public void setId(String idStr) { this.idStr = idStr; }
    public void setDate(String dateStr) { this.dateStr = dateStr; }

    public String getTitle() {
        return this.titleStr ;
    }
    public String getContent() {
        return this.contentStr ;
    }
    public String getId() { return idStr; }
    public String getDate() { return dateStr; }
}
