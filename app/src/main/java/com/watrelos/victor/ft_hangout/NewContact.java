package com.watrelos.victor.ft_hangout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewContact extends BaseActivity {

    protected boolean fname_valid = false;
    protected boolean lname_valid = false;
    protected boolean pname_valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn = (Button)findViewById(R.id.btn_create_contact);
        final EditText fname = (EditText) findViewById(R.id.firstname_text);
        final EditText lname = (EditText) findViewById(R.id.lastname_text);
        final EditText pname = (EditText) findViewById(R.id.phone_text);
        final EditText aname = (EditText) findViewById(R.id.address_text);
        final EditText cname = (EditText) findViewById(R.id.code_text);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fname_valid && pname_valid && lname_valid)
                {
                    ContactDB contactDB = new ContactDB();
                    contactDB.insertData(
                                    fname.getText().toString(),
                                    lname.getText().toString(),
                                    pname.getText().toString(),
                                    aname.getText().toString(),
                                    cname.getText().toString(),
                                    NewContact.this);
                    finish();
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(NewContact.this).create();
                    alertDialog.setTitle(getResources().getString(R.string.title_alert_new_contact));
                    alertDialog.setMessage(getResources().getString(R.string.content_alert_new_contact));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

            }
        });
        fname.addTextChangedListener(new TextValidator(fname) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() > 1)
                    fname_valid = true;
                else
                    fname_valid = false;
            }
        });
        lname.addTextChangedListener(new TextValidator(lname) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() > 1)
                    lname_valid = true;
                else
                    lname_valid = false;
            }
        });
        pname.addTextChangedListener(new TextValidator(pname) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() > 1)
                    pname_valid = true;
                else
                    pname_valid = false;
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        updateColor();
    }
    private void    updateColor() {

        SharedPreferences settings = getSharedPreferences(Settings.PREF_KEY, 0);
        Integer color = settings.getInt(Settings.COLOR_ID, R.color.colorPrimaryDark);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, color)));
    }
}
