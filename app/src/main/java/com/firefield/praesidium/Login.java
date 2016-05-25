package com.firefield.praesidium;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class Login extends AppCompatActivity {
    TextView t4;
    Button b1;
    EditText et1, et2,et3,et4,et5;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        t4 = (TextView) findViewById(R.id.T4);
        t4.setText(telephonyManager.getDeviceId());
        loadData();
        b1 = (Button) findViewById(R.id.B1);
        et1 = (EditText) findViewById(R.id.ET1);
        et2 = (EditText) findViewById(R.id.ET2);
        et3 = (EditText) findViewById(R.id.ET3);
        et4 = (EditText) findViewById(R.id.ET4);
        et5 = (EditText) findViewById(R.id.ET5);
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

                Intent intent = new Intent(Login.this, start.class);
                startActivity(intent);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void saveData() {
        SharedPreferences sp =
                getSharedPreferences("MyPrefs",
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Name", et1.getText().toString());
        editor.putString("Phone1", et2.getText().toString());
        editor.putString("Phone2", et3.getText().toString());
        editor.putString("PT", et4.getText().toString());
        editor.putString("PT1", et5.getText().toString());
        editor.putString("FLAG", "0");
        editor.commit();
    }

    private void loadData() {
        et1 = (EditText) findViewById(R.id.ET1);
        et2 = (EditText) findViewById(R.id.ET2);
        et3 = (EditText) findViewById(R.id.ET3);
        et4 = (EditText) findViewById(R.id.ET4);
        et5 = (EditText) findViewById(R.id.ET5);
        SharedPreferences sp =
                getSharedPreferences("MyPrefs",
                        Context.MODE_PRIVATE);
        String a = "", b = "", c = "",d="",e="";
        a = sp.getString("Name", "");
        //Log.d(TAG, a);


        b = sp.getString("Phone1", "");
        c = sp.getString("Phone2", "");
        d = sp.getString("PT", "");
        e = sp.getString("PT1", "");

        if (!(a.equals(""))) {
           /* AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
            alertDialog.setMessage(a);
            alertDialog.show();*/
                 et1.setText(a, TextView.BufferType.EDITABLE);
            et2.setText(b,TextView.BufferType.EDITABLE);
             et3.setText(c,TextView.BufferType.EDITABLE);
            et4.setText(d,TextView.BufferType.EDITABLE);
            et5.setText(e,TextView.BufferType.EDITABLE);

        }
    }

    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadData();
    }


}
