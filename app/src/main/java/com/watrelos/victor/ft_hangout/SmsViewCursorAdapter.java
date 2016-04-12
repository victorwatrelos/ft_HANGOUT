package com.watrelos.victor.ft_hangout;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by victor on 4/10/16.
 */
public class SmsViewCursorAdapter extends CursorAdapter {
    public SmsViewCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.sms_row, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView smsContent = (TextView) view.findViewById(R.id.sms_content);
        smsContent.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));

        TextView smsSrc = (TextView) view.findViewById(R.id.sms_src);
        smsSrc.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));

        TextView smsDate = (TextView) view.findViewById(R.id.sms_date);
        smsDate.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
    }
}
