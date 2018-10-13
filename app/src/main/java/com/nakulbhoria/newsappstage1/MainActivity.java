package com.nakulbhoria.newsappstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?";
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String newsTopic = sharedPreferences.getString(getString(R.string.settings_news_topic_key),
                getString(R.string.settings_news_topic_default) );
        String orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri uri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = uri.buildUpon();

        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("section", newsTopic);
        uriBuilder.appendQueryParameter("orderby", orderBy);
        uriBuilder.appendQueryParameter("from-date", "2018-10-01");
        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter("show-tags", "contributor");

        Log.e(LOG_TAG, "onCreateLoader: url" + uriBuilder.toString() );


        return new NewsLoader(MainActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        adapter.clear();
        View bar = findViewById(R.id.progress);
        bar.setVisibility(View.GONE);
        if (nI == null || !nI.isConnected()) {
            empty.setText(R.string.no_internet);

        } else if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        } else {
            empty.setText(R.string.no_news);
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
