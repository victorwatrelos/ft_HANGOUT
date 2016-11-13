package com.watrelos.victor.ft_hangout;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final Integer[] color = {R.color.colorPrimaryDark, R.color.red, R.color.blue, R.color.green, R.color.black};
    public static final String      PREF_KEY = "MyPrefs";
    public static final String      COLOR_ID = "ColorId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Spinner spinner = (Spinner) findViewById(R.id.colors_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        SharedPreferences settings = getSharedPreferences(PREF_KEY, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(COLOR_ID, color[pos]);
        editor.commit();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, color[pos])));
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
