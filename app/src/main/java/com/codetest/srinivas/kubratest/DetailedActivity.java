package com.codetest.srinivas.kubratest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.codetest.srinivas.kubratest.adapter.PostsAdapter;
import com.codetest.srinivas.kubratest.model.PostObjectModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srinivas on 5/25/17.
 */

public class DetailedActivity extends AppCompatActivity {

    private int userId;
    private ListView lvPostList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_activity);

        lvPostList = (ListView) findViewById(R.id.lvPostList);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            userId = (int) bundle.get("id");
        }

        new GetDataForThePostList().execute("http://jsonplaceholder.typicode.com/posts?userId=" + userId);
    }

    private class GetDataForThePostList extends AsyncTask<String, Void, List<PostObjectModel>> {

        @Override
        protected List<PostObjectModel> doInBackground(String... urls) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String retData = buffer.toString();

                JSONArray parentArray = new JSONArray(retData);
                List<PostObjectModel> postList = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject eachPostObject = parentArray.getJSONObject(i);

                    PostObjectModel postObjectModel = new PostObjectModel();

                    postObjectModel.setTitle(eachPostObject.getString("title"));
                    postObjectModel.setBody(eachPostObject.getString("body"));

                    postList.add(postObjectModel);
                }

                return postList;

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<PostObjectModel> result) {
            super.onPostExecute(result);

            PostsAdapter postsAdapter = new PostsAdapter(getApplicationContext(), R.layout.each_row_for_post, result);
            lvPostList.setAdapter(postsAdapter);

        }

    }

}
