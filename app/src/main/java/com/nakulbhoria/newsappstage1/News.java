package com.nakulbhoria.newsappstage1;

public class News {

    private String title;
    private String url;
    private String time;
    private String authorName;
    private String section;

    public News(String title, String url, String time, String authorName, String section) {
        this.title = title;
        this.url = url;
        this.time = time;
        this.authorName = authorName;
        this.section = section;
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

    public String getSection() {
        return section;
    }
}
