package com.nakulbhoria.newsappstage1;

public class News {

    private String title;
    private String url;
    private String time;
    private String authorName;

    public News(String title, String url, String time, String authorName) {
        this.title = title;
        this.url = url;
        this.time = time;
        this.authorName = authorName;
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

    public String getAuthorName() {
        return authorName;
    }
}
