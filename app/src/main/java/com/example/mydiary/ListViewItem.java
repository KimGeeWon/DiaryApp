package com.example.mydiary;

public class ListViewItem {
    private String titleStr ;
    private String contentStr ;

    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setContent(String content) {
        contentStr = content ;
    }

    public String getTitle() {
        return this.titleStr ;
    }
    public String getContent() {
        return this.contentStr ;
    }
}
