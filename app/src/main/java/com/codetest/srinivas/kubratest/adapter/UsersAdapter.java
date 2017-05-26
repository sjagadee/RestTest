package com.codetest.srinivas.kubratest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codetest.srinivas.kubratest.R;
import com.codetest.srinivas.kubratest.model.UserObjectModel;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by srinivas on 5/25/17.
 */

public class UsersAdapter extends ArrayAdapter<UserObjectModel> {

    private List<UserObjectModel> usersList;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public UsersAdapter(Context context, int resource, List<UserObjectModel> objects) {
        super(context, resource, objects);

        this.usersList = objects;
        this.resource = resource;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.each_row_for_user, null);
        }

        TextView tvUserName;
        TextView tvUserAddressLine1;
        TextView tvUserAddressLine2;

        tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvUserAddressLine1 = (TextView) convertView.findViewById(R.id.tvUserAddressLine1);
        tvUserAddressLine2 = (TextView) convertView.findViewById(R.id.tvUserAddressLine2);

        tvUserName.setText(usersList.get(position).getUsername());
        tvUserAddressLine1.setText(usersList.get(position).getAddress().getStreet() + ", " + usersList.get(position).getAddress().getSuite());
        tvUserAddressLine2.setText(usersList.get(position).getAddress().getCity() + ", " + usersList.get(position).getAddress().getZipcode());

        return convertView;
    }

}
