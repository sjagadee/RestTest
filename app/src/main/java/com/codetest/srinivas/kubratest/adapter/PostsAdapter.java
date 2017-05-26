package com.codetest.srinivas.kubratest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codetest.srinivas.kubratest.R;
import com.codetest.srinivas.kubratest.model.PostObjectModel;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by srinivas on 5/25/17.
 */

public class PostsAdapter extends ArrayAdapter<PostObjectModel> {

    private List<PostObjectModel> postsList;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public PostsAdapter(Context context, int resource, List<PostObjectModel> objects) {
        super(context, resource, objects);

        this.postsList = objects;
        this.resource = resource;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.each_row_for_post, null);
        }

        TextView tvTitle;
        TextView tvBody;

        tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        tvTitle.setText(postsList.get(position).getTitle());
        tvBody.setText(postsList.get(position).getBody());

        return convertView;
    }

}
