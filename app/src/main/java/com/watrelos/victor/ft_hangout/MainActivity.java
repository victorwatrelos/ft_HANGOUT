package com.watrelos.victor.ft_hangout;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String EXTRA_CONTACT_ID = "com.watrelos.victor.ft_hangout.ExtraContactId";
    private ContactDB contactDB;
    private ContactViewCursorAdapter customAdapter = null;
    private ListView listContactView;
    private Date date;

    public void openSettings(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    private void    updateColor() {

        SharedPreferences settings = getSharedPreferences(Settings.PREF_KEY, 0);
        Integer color = settings.getInt(Settings.COLOR_ID, R.color.colorPrimaryDark);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, color)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateColor();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewContact.class);
                startActivity(intent);
            }
        });

        listContactView = (ListView) findViewById(R.id.list_contact);
        contactDB = new ContactDB();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                customAdapter = new ContactViewCursorAdapter(MainActivity.this, contactDB.getContactsCursor(MainActivity.this));
                MainActivity.this.listContactView.setAdapter(customAdapter);
            }
        });
        listContactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayContact.class);
                intent.putExtra(EXTRA_CONTACT_ID, id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        date = new Date();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (date != null) {
            Toast toast = Toast.makeText(this, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), Toast.LENGTH_SHORT);
            toast.show();
        }
        updateColor();
        if (customAdapter != null) {
            customAdapter = new ContactViewCursorAdapter(MainActivity.this, contactDB.getContactsCursor(MainActivity.this));
            MainActivity.this.listContactView.setAdapter(customAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
