package com.watrelos.victor.ft_hangout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditContact extends BaseActivity {

    private EditText ledit;
    private EditText fedit;
    private EditText pedit;
    private EditText aedit;
    private EditText cedit;
    private long     id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ledit = (EditText) findViewById(R.id.ledit);
        fedit = (EditText) findViewById(R.id.fedit);
        pedit = (EditText) findViewById(R.id.pedit);
        aedit = (EditText) findViewById(R.id.aedit);
        cedit = (EditText) findViewById(R.id.cedit);
        Intent intent = getIntent();
        ledit.setText(intent.getStringExtra(DisplayContact.LASTNAME));
        fedit.setText(intent.getStringExtra(DisplayContact.FIRSTNAME));
        pedit.setText(intent.getStringExtra(DisplayContact.PHONE));
        aedit.setText(intent.getStringExtra(DisplayContact.ADDRESS));
        cedit.setText(intent.getStringExtra(DisplayContact.CODE));
        id = intent.getLongExtra(DisplayContact.ID, -1);
        Button btn_edit = (Button)findViewById(R.id.btn_edit_contact);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactDB contactDB = new ContactDB();
                contactDB.update(id, ledit.getText().toString(), fedit.getText().toString(), pedit.getText().toString(), cedit.getText().toString(), aedit.getText().toString(), EditContact.this);
                finish();
            }
        });

    }
}
