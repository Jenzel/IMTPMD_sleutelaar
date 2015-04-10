package com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar;

/**
 * Created by Jens Brokaar
 * s1066589
 * IMTPMD
 */

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Hoofdscherm extends Activity implements OnItemSelectedListener
         {

    ArrayAdapter<String> slotAdapter;

    static ArrayList<String> lijst;
    static ArrayList<JSONObject> slotenLijst;

    public static String ipadres = "192.168.56.101";
    public static int port = 4444;
    public String informatiebeknopt = null;
    public static String slotNaam;

    Spinner spinner_item;
    Button bestelbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoofdscherm_layout);

        lijst = new ArrayList<String>();
        JSONObject lijstObject = new JSONObject();
        try {
            lijstObject.put("slotenlijst", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String responseString = null;
        try {
            try {
                responseString = new ServerCommunicator(ipadres, port,
                        lijstObject.toString()).execute().get();

            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if (responseString == null) {
        } else {
            String jsonFix = responseString.replace("null", "");
            JSONArray JArray = null;
            try {
                JArray = new JSONArray(jsonFix);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject jObject = null;
            String value = null;
            lijst = new ArrayList<String>();

            for (int i = 0; i < JArray.length(); i++) {
                try {
                    jObject = JArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    value = jObject.getString("naam");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lijst.add(value);

            }

            slotenLijst = new ArrayList<JSONObject>();
            JSONObject beknopteinfoJObject = new JSONObject();
            try {
                for (int i = 0; i < lijst.size(); i++) {
                    beknopteinfoJObject.put("informatiebeknopt", lijst.get(i));
                    try {
                        try {
                            informatiebeknopt = new ServerCommunicator(ipadres,
                                    port, beknopteinfoJObject.toString()).execute()
                                    .get();

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    String infoFix = informatiebeknopt.replace("null", "");
                    JSONObject fixedjObject = new JSONObject(infoFix);
                    slotenLijst.add(fixedjObject);

                    Log.i("informatiebeknopt", infoFix);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //De spinner wordt aan de adapter toegevoegd
        //en krijgt invulling van de arraylist "lijst"
        spinner_item = (Spinner) findViewById(R.id.spinner);
        slotAdapter = new ArrayAdapter<String>(Hoofdscherm.this,
                android.R.layout.simple_spinner_dropdown_item, lijst);
        slotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item.setAdapter(slotAdapter);
        spinner_item.setOnItemSelectedListener(this);
        bestelbutton = (Button) findViewById(R.id.selecteren);
//        bestelbutton.setOnClickListener(this);

        String[] pref = DataClass.getInstance(this)
                .getMainActivityPreferences();
        if (pref[0] != null)
            spinner_item.setSelection(Integer.parseInt(pref[0]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Het Slotinfoscherm wordt geopend zodra de deze geactiveerd wordt
    //Dat gebeurt indirect door de volgende-button
//    public void onClick(View v) {
//        Intent i = new Intent(Hoofdscherm.this, Slotinfoscherm.class);
//        i.putExtra("naam", slotNaam.toString());
//        startActivity(i);
//    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub

        String[] loc = { "" + position };
        DataClass.getInstance(Hoofdscherm.this)
                .updateMainActivityPreferences(loc);

        TextView beknopteinfo = (TextView) findViewById(R.id.beknopte_info);

        try {
            beknopteinfo.setText(slotenLijst.get(position).getString(
                    "informatiebeknopt"));
            slotNaam = lijst.get(position);

        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
