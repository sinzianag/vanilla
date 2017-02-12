package me.sinziana.vanilla;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the activity used for Searching a Pun
 * It will receive an intent that contains a query
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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        _toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(_toolbar);
       System.out.println("%% newIntent");
        handleIntent(intent);
    }

    /**
     * Handle receiving a new intent from a search
     * @param intent
     */
    private void handleIntent(Intent intent) {
        System.out.println("%% handleIntent");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // TODO - Save query so  the app can resume state when comming out of background.
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("%% query" + query);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle("Searching for..." + query);
                ab.setDisplayHomeAsUpEnabled(true);
            }

            PunStorage database = new PunStorage(this);
            Cursor cur = database.searchForPuns(query);

            final ArrayList<String> list = new ArrayList<String>();
            if (cur != null) {
                cur.moveToFirst();
                while (!cur.isAfterLast()) {
                    list.add(cur.getString(1));
                    cur.moveToNext();
                }
            }

            final ListView listview = (ListView) findViewById(R.id.listview);
            final StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.pun_result, list);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    view.animate().setDuration(2000).alpha(0)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    list.remove(item);
                                    adapter.notifyDataSetChanged();
                                    view.setAlpha(1);
                                }
                            });
                }

            });

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

    /**
     * Adapter for the List View
     */
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}

//            RequestQueue queue = Volley.newRequestQueue(this);
//            // this is for the emulator ( Change to 127.0.0.1 )
//            String url ="http://10.0.2.2:8080/" + query;
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            _textView.setText(response);
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    _textView.setText("That didn't work! " + error.getMessage());
//                }
//            });
//            queue.add(stringRequest);

