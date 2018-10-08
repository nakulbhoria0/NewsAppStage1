package com.nakulbhoria.newsappstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2018-01-01&show-tags=contributor&api-key=test";
    private NewsAdapter adapter;
    private TextView empty;
    private ConnectivityManager connectivityManager;
    private NetworkInfo nI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        empty = findViewById(R.id.text);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        nI = connectivityManager.getActiveNetworkInfo();


        getLoaderManager().initLoader(1, null, this).forceLoad();


        List<News> news = new ArrayList<>();

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = findViewById(R.id.list);

        newsListView.setEmptyView(empty);

        adapter = new NewsAdapter(this, news);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(adapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News current = adapter.getItem(position);

                Uri newsUri = Uri.parse(current.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        adapter.clear();
        View bar = findViewById(R.id.progress);
        bar.setVisibility(View.GONE);
        if (nI == null || !nI.isConnected()) {
            empty.setText("No internet connection");

        } else if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        } else {
            empty.setText("No News found");
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();

    }
}
