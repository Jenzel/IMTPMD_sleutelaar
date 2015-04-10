package com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar;

/**
* Created by Jens Brokaar
* s1066589
* IMTPMD
*/

import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Slotinfoscherm extends Activity {

    public static String ipadres = Hoofdscherm.ipadres;
    public static int port = Hoofdscherm.port;
    private String completeInfo;

    Button vorigeVenster;
    Button volgendeVenster;
    String systeemKeuze;
    TextView titel;
    TextView informatie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.slotinfo_layout);

        Intent intent = getIntent();
        systeemKeuze = intent.getStringExtra("naam");

        TextView titel = (TextView) findViewById(R.id.slotNaamView);
        this.titel = titel;
        titel.setText(systeemKeuze);

        JSONObject beknopteinfoJObject = new JSONObject();
        try {
            beknopteinfoJObject.put("informatie", systeemKeuze);
            try {
                try {
                    completeInfo = new ServerCommunicator(ipadres, port,
                            beknopteinfoJObject.toString()).execute().get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            String infoFix = completeInfo.replace("null", "");
            JSONObject fixedjObject = new JSONObject(infoFix);
            completeInfo = fixedjObject.getString("informatie");

            Log.i("informatie", infoFix);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView info = (TextView) findViewById(R.id.informatieView);
        this.informatie = info;
        info.setText(completeInfo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.vorige:
//                Intent iback = new Intent(this, Hoofdscherm.class);
//                startActivity(iback);
//
//                finish();
//                break;
//            case R.id.volgende:
//
//                Intent inext = new Intent(this, Bestellingscherm.class);
//                inext.putExtra("gekozen", (String) systeemKeuze);
//                startActivity(inext);
//
//                finish();
//                break;
//        }
//    }
}