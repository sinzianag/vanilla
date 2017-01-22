package me.sinziana.vanilla;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Sinziana on 1/17/17.
 */

public class SearchActivity extends AppCompatActivity {

    private Toolbar _toolbar;
    TextView _textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        _toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(_toolbar);
        System.out.println("%% onCreate");
        handleIntent(getIntent());
        _textView = (TextView) findViewById(R.id.textView);
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

            RequestQueue queue = Volley.newRequestQueue(this);
            // this is for the emulator ( Change to 127.0.0.1 )
            String url ="http://10.0.2.2:8080/" + query;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            _textView.setText(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    _textView.setText("That didn't work! " + error.getMessage());
                }
            });
            queue.add(stringRequest);
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

