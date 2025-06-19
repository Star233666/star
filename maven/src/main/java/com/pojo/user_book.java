package com.pojo;

public class user_book {
    private Integer id;
    private String BookName;
    private String AuthorName;
    private String PublishTime;
    public String Condition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    @Override
    public String toString() {
        return "user_book{" +
                "id=" + id +
                ", BookName='" + BookName + '\'' +
                ", AuthorName='" + AuthorName + '\'' +
                ", PublishTime='" + PublishTime + '\'' +
                ", Condition='" + Condition + '\'' +
                '}';
    }
}
