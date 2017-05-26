package com.codetest.srinivas.kubratest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codetest.srinivas.kubratest.adapter.UsersAdapter;
import com.codetest.srinivas.kubratest.model.UserObjectModel;

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

public class MainActivity extends AppCompatActivity {

    private ListView lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvUsers = (ListView) findViewById(R.id.lvUserList);

        new GetDataForTheList().execute("http://jsonplaceholder.typicode.com/users");
    }

    private class GetDataForTheList extends AsyncTask<String, Void, List<UserObjectModel>> {

        @Override
        protected List<UserObjectModel> doInBackground(String... urls) {

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
                List<UserObjectModel> userList = new ArrayList<>();

                for(int i = 0; i < parentArray.length(); i++) {
                    JSONObject eachUserObject = parentArray.getJSONObject(i);

                    UserObjectModel userObjectModel = new UserObjectModel();

                    userObjectModel.setId(eachUserObject.getInt("id"));
                    userObjectModel.setName(eachUserObject.getString("name"));
                    userObjectModel.setUsername(eachUserObject.getString("username"));

                    UserObjectModel.Address address = new UserObjectModel.Address();
                    address.setStreet(eachUserObject.getJSONObject("address").getString("street"));
                    address.setSuite(eachUserObject.getJSONObject("address").getString("suite"));
                    address.setCity(eachUserObject.getJSONObject("address").getString("city"));
                    address.setZipcode(eachUserObject.getJSONObject("address").getString("zipcode"));

                    userObjectModel.setAddress(address);
                    userList.add(userObjectModel);
                }

                return userList;

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(reader != null) {
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
        protected void onPostExecute(final List<UserObjectModel> result) {
            super.onPostExecute(result);

            UsersAdapter usersAdapter = new UsersAdapter(getApplicationContext(), R.layout.each_row_for_user, result);
            lvUsers.setAdapter(usersAdapter);

            lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
                    intent.putExtra("id", result.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }
}
