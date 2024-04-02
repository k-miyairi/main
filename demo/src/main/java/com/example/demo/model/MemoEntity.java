package com.example.demo.model;

public class MemoEntity {

    private final int id;
    private final String title;
    private final String content;
    private final String create_time;
    private final String update_time;

    public MemoEntity(int id, String title, String content, String create_time, String update_time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return create_time;
    }

    public String GetUpdateTime() {
        return update_time;
    }

    public String toString() {
        String str = "id:" + this.id + "\r\n"
                + "title:" + this.title + "\r\n"
                + "content:" + this.content + "\r\n"
                + "create_time:" + this.create_time + "\r\n"
                + "update_time:" + this.update_time;
        return str;
    }

}
