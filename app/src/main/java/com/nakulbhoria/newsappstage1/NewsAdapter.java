package com.nakulbhoria.newsappstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class NewsAdapter extends ArrayAdapter<News> {
    private static final String TIME_SEPARATOR = "T";

    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        News currentNews = getItem(position);

        String publicationDate = currentNews.getTime();
        String dateInDays = null;
        String timeInHourse = null;

        if (publicationDate.contains(TIME_SEPARATOR)) {
            String[] parts = publicationDate.split(TIME_SEPARATOR);
            dateInDays = parts[0];
            String tempTimeInHourse = parts[1];

            if(tempTimeInHourse.contains("Z")){
                String[] partsTime = tempTimeInHourse.split("Z");
                timeInHourse = partsTime[0];
            }
        }
        String tmpDate = dateInDays + " " + timeInHourse;

        TextView title = view.findViewById(R.id.title);
        TextView date = view.findViewById(R.id.date);

        title.setText(currentNews.getTitle());
        date.setText(tmpDate);

        return view;
    }
}
