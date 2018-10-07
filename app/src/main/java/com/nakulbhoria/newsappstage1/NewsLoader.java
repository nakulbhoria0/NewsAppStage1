package com.nakulbhoria.newsappstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<News> loadInBackground() {

        List<News> news = QueryNews.fetchNewsData(url);
        return news;
    }
}
