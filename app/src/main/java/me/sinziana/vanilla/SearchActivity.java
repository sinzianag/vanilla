package me.sinziana.vanilla;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Sinziana on 1/17/17.
 */

public class SearchActivity extends AppCompatActivity {

    private Toolbar _toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        _toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(_toolbar);
        System.out.println("%% onCreate");
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        _toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(_toolbar);
       System.out.println("%% newIntent");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        System.out.println("%% handleIntent");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("%% query" + query);
            getSupportActionBar().setTitle("Searching for..." + query);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

