package com.example.softxpertt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.stripe.okhttp3.OkHttpClient;
import com.stripe.okhttp3.Request;
import com.stripe.okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ListView _list;

    static ArrayList<String> brandNames = new ArrayList<>();
    static ArrayList<String> constructionYear = new ArrayList<>();
    static ArrayList<Boolean> isUsed = new ArrayList<>();
    static ArrayList<String> imageId = new ArrayList<>();

    static int page = 1;
    boolean done = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _list = findViewById(R.id.list_view);

        if(done) {
            getAll();
            done = false;
        }

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            refreshData(); // your code
            pullToRefresh.setRefreshing(false);
        });

    }

    private void refreshData(){
        brandNames = new ArrayList<>();
        constructionYear = new ArrayList<>();
        isUsed = new ArrayList<>();
        imageId = new ArrayList<>();
        page = 1;
        getAll();
    }


    private void getAll(){
        try {
            URL url = new URL("http://demo1585915.mockable.io/api/v1/cars?page"+page);
        } catch (MalformedURLException e) {
            Toast.makeText(getBaseContext(), "Failed. Try again later", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        new getAllTask().execute();
    }


    public class getAllTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://demo1585915.mockable.io/api/v1/cars?page="+page)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray data = obj.getJSONArray("data");
                    for(int i = 0; i < data.length() ; i++){
                        JSONObject object = data.getJSONObject(i);
                        String brand = String.valueOf(object.get("brand"));
                        String year = "unknown";
                        if(!object.get("constractionYear").equals(null))
                            year = String.valueOf(object.get("constractionYear"));
                        Boolean used = (Boolean) object.get("isUsed");
                        String image = "";
                        if(!object.get("imageUrl").equals(null))
                            image = String.valueOf(object.get("imageUrl"));
                        brandNames.add(brand);
                        constructionYear.add(year);
                        isUsed.add(used);
                        imageId.add(image);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();}
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {

            //list view
            CustomList customList = new CustomList(MainActivity.this, brandNames, constructionYear, isUsed, imageId);
            _list.setAdapter(customList);

            _list.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    if(MainActivity.page < 4) {
                        MainActivity.page++;
                        getAll();
                    }
                    int index = _list.getFirstVisiblePosition();
                    View v = _list.getChildAt(0);
                    int top = (v == null) ? 0 : (v.getTop() - _list.getPaddingTop());
                    _list.setSelectionFromTop(index, top);
                    return true;
                }
            });

        }
    }

}
