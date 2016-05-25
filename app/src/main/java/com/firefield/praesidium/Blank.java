package com.firefield.praesidium;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Blank extends AppCompatActivity {
    private OverlayDialog mOverlayDialog;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        // touch & full screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();
lock1(this);
        //wifi
        WifiManager wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        boolean wifiEnabled = wm.isWifiEnabled();
        if (!wifiEnabled) {
            wm.setWifiEnabled(true);
        }
        //silent mode
        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        //feedback
        Vibrator vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(0);

        final Handler handler = new Handler();
        handler.postDelayed(null, 300000);
        gps = new GPSTracker(Blank.this);

        double la=0,lo=0;
        // check if GPS enabled
        if(gps.canGetLocation()){

            la = gps.getLatitude();
           lo = gps.getLongitude();

        }
        //sms
        SharedPreferences sp =
                getSharedPreferences("MyPrefs",
                        Context.MODE_PRIVATE);
        String a=sp.getString("Phone1", "");
        String b=sp.getString("Phone2", "");
        String aaa=a+" phone is stolen current location of phone: la :"+String.valueOf(la)+" lo: "+String.valueOf(lo);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(b, null, aaa, null, null);

        ConnectivityManager dataManager;
        dataManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = null;
        try {
            dataMtd = ConnectivityManager.class
                    .getDeclaredMethod(
                            "setMobileDataEnabled",
                            boolean.class);
            dataMtd.setAccessible(true);

            dataMtd.invoke(dataManager, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

        Checkup();

    }
private void Checkup()
{
    SharedPreferences sp =
            getSharedPreferences("MyPrefs",
                    Context.MODE_PRIVATE);
    String a = "",b="";
    a = sp.getString("PT1", "");
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
                    Intent intent1 = new Intent(this, Blank.class);
                    startService(intent1);
                }
            }
            // use msgData
        } while (cursor.moveToPrevious());

        if (bit == 1) {
            SharedPreferences sp1 =
                    getSharedPreferences("MyPrefs",
                            Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putString("FLAG", "1");
            cursor.close();


        }
        else
        {
            cursor.close();
        }

    } else {
        // empty box, no SMS
    }
}
    @Override
    public void onBackPressed()
    {

    }
    @Override
    protected void onResume()
    {
        super.onResume();

    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Intent i=new Intent(this,Check.class);
        startService(i);
    }

    public void lock1(Activity activity) {
        if (mOverlayDialog == null) {
            mOverlayDialog = new OverlayDialog(activity);
            mOverlayDialog.show();
        }
    }
    private static class OverlayDialog extends AlertDialog {

        public OverlayDialog(Activity activity) {
            super(activity, R.style.OverlayDialog);
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            params.dimAmount = 0.0F; // transparent
            params.width = 0;
            params.height = 0;
            params.gravity = Gravity.BOTTOM;
            getWindow().setAttributes(params);
             setOwnerActivity(activity);
            setCancelable(false);
        }

        public final boolean dispatchTouchEvent(MotionEvent motionevent) {
            return true;
        }

        protected final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            FrameLayout framelayout = new FrameLayout(getContext());
            framelayout.setBackgroundColor(0);
            setContentView(framelayout);
        }
    }
}
