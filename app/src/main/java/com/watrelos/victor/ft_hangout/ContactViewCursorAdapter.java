package com.watrelos.victor.ft_hangout;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by victor on 3/29/16.
 */
public class ContactViewCursorAdapter extends CursorAdapter {

    public ContactViewCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.contact_row, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView contactFirstName = (TextView) view.findViewById(R.id.contact_firstname);
        contactFirstName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));

        TextView contactLastName = (TextView) view.findViewById(R.id.contact_lastname);
        contactLastName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
    }

}
