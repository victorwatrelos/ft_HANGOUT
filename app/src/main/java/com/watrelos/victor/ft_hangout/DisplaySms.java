package com.watrelos.victor.ft_hangout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplaySms extends BaseActivity {

    private ListView listSms;
    private SmsViewCursorAdapter customAdapter;
    private String  phoneNumberSrc;
    private static boolean  isOn = false;
    private static DisplaySms ins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Create", "FDSFDSFDSF");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        phoneNumberSrc = intent.getStringExtra(DisplayContact.PHONE);
        listSms = (ListView) findViewById(R.id.list_sms);
        updateView();
        final EditText text_sms = (EditText)findViewById(R.id.content_sms);
        findViewById(R.id.btn_send_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumberSrc, null, text_sms.getText().toString(), null, null);
                        SmsDB smsDB = new SmsDB();
                        smsDB.insertData(text_sms.getText().toString(), phoneNumberSrc, phoneNumberSrc, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), DisplaySms.this);
                        Toast.makeText(DisplaySms.this, DisplaySms.this.getString(R.string.msg_send), Toast.LENGTH_SHORT);
                        updateView();
                    }
                });
            }
        });

    }
    public void onResume() {
        super.onResume();
        isOn = true;
        ins = this;
    }

    public void onPause() {
        super.onPause();
        isOn = false;
        ins = null;
    }

    static public void  sUpdateView() {
        if (isOn)
            ins.updateView();
    }
    private void scrollMyListViewToBottom() {
        listSms.post(new Runnable() {
            @Override
            public void run() {
                listSms.setSelection(listSms.getCount() - 1);
            }
        });
    }

    public void    updateView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                SmsDB smsDB = new SmsDB();
                customAdapter = new SmsViewCursorAdapter(DisplaySms.this, smsDB.getSmsCursor(DisplaySms.this, DisplaySms.this.phoneNumberSrc, DisplaySms.this.phoneNumberSrc));
                DisplaySms.this.listSms.setAdapter(customAdapter);
                DisplaySms.this.scrollMyListViewToBottom();
            }
        });
    }
}
