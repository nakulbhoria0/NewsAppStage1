package com.nakulbhoria.newsappstage1;

public class News {

    private String title;
    private String url;
    private String time;

    public News(String title, String url, String time) {
        this.title = title;
        this.url = url;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }
}
