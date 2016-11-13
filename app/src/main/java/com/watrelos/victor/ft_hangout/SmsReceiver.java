package com.watrelos.victor.ft_hangout;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by victor on 4/12/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    private final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(ACTION_SMS_RECEIVED)) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        SmsDB smsDB = new SmsDB();

        Log.d("TOTO", "SMS RECEIVED");
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage message;
        String phoneNumber;
        String messageBody;
        for (int i = 0; i < pdus.length; i++) {
            message = SmsMessage.createFromPdu((byte[]) pdus[i]);
            messageBody = message.getMessageBody();
            phoneNumber = message.getDisplayOriginatingAddress();
            TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            String dstNumber = tMgr.getLine1Number();
            if (!smsDB.insertData(messageBody, phoneNumber, dstNumber, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString(), context))
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show();
            DisplaySms.sUpdateView();

        }
    }
}
