package cn.zhangle.myapplication.domain;

import java.io.Serializable;

/**
 * @Classname News
 * @Description TODO
 * @Date 2021/6/14 15:17
 * @Created by zl363
 * 仙人保佑，阿乐代码永无bug
 */
public class News implements Serializable {
    private int id;
    private String title;
    private String date;
    private String from;

    public News() {
    }

    public News(int id, String title, String date, String from) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
