package com.watrelos.victor.ft_hangout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

public class DisplayContact extends BaseActivity {

    private static final String TAG = "DisplayContact";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String CODE = "code";
    public static final String ID = "id";

    private long                contact_id;
    private TextView            firstname_view;
    private TextView            lastname_view;
    private TextView            address;
    private TextView            code;
    private TextView            phone_view;
    public Map<String, String>  contact;

    private void    updateColor() {

        SharedPreferences settings = getSharedPreferences(Settings.PREF_KEY, 0);
        Integer color = settings.getInt(Settings.COLOR_ID, R.color.colorPrimaryDark);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, color)));
    }

    private void    updateDisplay()
    {
        ContactDB contactDB = new ContactDB();
        contact = contactDB.getContact(contact_id, this);

        String firstname_str = this.getString(R.string.firstname);
        String lastname_str = this.getString(R.string.lastname);
        String phone_str = this.getString(R.string.phone);
        String address_str = this.getString(R.string.address);
        String code_str = this.getString(R.string.code);

        firstname_view.setText(firstname_str + ": " + contact.get("firstname"));
        lastname_view.setText(lastname_str + ": " + contact.get("lastname"));
        phone_view.setText(phone_str + ": " + contact.get("phone"));
        address.setText(address_str + ": " + contact.get("address"));
        code.setText(code_str + ": " + contact.get("code"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateColor();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        contact_id = intent.getLongExtra(MainActivity.EXTRA_CONTACT_ID, -1);
        if (contact_id < 0)
            Log.e(TAG, "Error on getting back the contact id");

        firstname_view = (TextView) findViewById(R.id.view_firstname);
        lastname_view = (TextView) findViewById(R.id.view_lastname);
        phone_view = (TextView) findViewById(R.id.view_phone);
        address = (TextView) findViewById(R.id.view_address);
        code = (TextView) findViewById(R.id.view_code);
        phone_view = (TextView) findViewById(R.id.view_phone);
        updateDisplay();

        Button btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactDB contactDB = new ContactDB();
                contactDB.deleteEntry(contact_id, DisplayContact.this);
                finish();

            }
        });
        Button btn_edit = (Button)findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayContact.this, EditContact.class);
                intent.putExtra(FIRSTNAME, contact.get("firstname"));
                intent.putExtra(LASTNAME, contact.get("lastname"));
                intent.putExtra(PHONE, contact.get("phone"));
                intent.putExtra(CODE, contact.get("code"));
                intent.putExtra(ADDRESS, contact.get("address"));
                intent.putExtra(ID, contact_id);
                startActivity(intent);
            }
        });
        Button btn_sms = (Button)findViewById(R.id.btn_sms);
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayContact.this, DisplaySms.class);
                intent.putExtra(PHONE, contact.get("phone"));
                intent.putExtra(ID, contact_id);
                intent.putExtra(FIRSTNAME, contact.get("firstname"));
                intent.putExtra(LASTNAME, contact.get("lastname"));
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        updateColor();
        updateDisplay();

    }

}
