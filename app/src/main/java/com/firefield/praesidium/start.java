package com.firefield.praesidium;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class start extends AppCompatActivity {
Button b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        b2=new Button(this);
        b2=(Button) findViewById(R.id.B2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this,Login.class);
                startActivity(intent);
            }
        });



       Intent intent = new Intent(this, Check.class);
        startService(intent);

       // toggleNetworkProvider(this);


    }

    /*

    private static boolean getNetworkProviderState(Context context) {
        return Settings.Secure.isLocationProviderEnabled(context.getContentResolver(), "network");
    }
    private void toggleNetworkProvider(Context context) {
        Settings.Secure.setLocationProviderEnabled(getContentResolver(), "network", !getNetworkProviderState(context));
    }*/
}
