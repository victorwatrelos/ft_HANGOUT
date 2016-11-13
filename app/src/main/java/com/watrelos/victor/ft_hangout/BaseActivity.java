package com.watrelos.victor.ft_hangout;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vwatrelo on 11/13/16.
 */

public class BaseActivity extends AppCompatActivity {

    private Date date;
    private boolean background = false;
    static int  running_act = 0;


    @Override
    public void onStop() {
        super.onStop();
        running_act--;
        if (running_act == 0)
            date = new Date();
    }

    @Override
    public void onStart() {
        super.onStart();
        running_act++;
        if (date != null) {
            Toast toast = Toast.makeText(this, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), Toast.LENGTH_SHORT);
            toast.show();
        }
        date = null;
    }
}
