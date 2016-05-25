package com.firefield.praesidium;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class Check extends IntentService {

    public Check() {
        super("Check");
    }
int fl=0;
    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sp =
                getSharedPreferences("MyPrefs",
                        Context.MODE_PRIVATE);
        String a = "",b="";
        a = sp.getString("PT", "");
        b = sp.getString("FLAG", "");
        if(b.equals("1"))
        {
fl=1;

            return START_NOT_STICKY;
        }
        else
        {

            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            int bit = 0, q = 0;
            if (cursor.moveToLast()) { // must check the result to prevent exception
                String msgData;
                if(cursor==null  ){
                    cursor.moveToFirst();
                }
                do {
                    msgData = "";
                    for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                        if (cursor.getColumnName(idx).equals("body")) {
                            msgData += cursor.getString(idx);

                        }
                    }

                    if (msgData.equals(a)) {
                        bit = 1;
                        break;
                    }
                    else
                    {
                        q++;
                        if(q==15)
                        {
                            Intent intent1 = new Intent(this, Check.class);
                            startService(intent1);
                        }
                    }
                    // use msgData
                } while (cursor.moveToPrevious());

                if (bit == 1) {
                    cursor.close();
                    Intent dialogIntent = new Intent(getBaseContext(), Blank.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(dialogIntent);
                }
                else
                {
                    cursor.close();
                }

            } else {
                // empty box, no SMS
            }
            return START_STICKY;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(fl==1)
        {
        }
        else {
            Intent intent = new Intent(this, Check.class);
            startService(intent);
        }
    }
}
