//package com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar;
//
///**
//* Created by Jens Brokaar
//* s1066589
//* IMTPMD
//*/
//
//import java.util.concurrent.ExecutionException;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar.Hoofdscherm;
//
//
//public class Bestellingscherm extends Activity implements OnClickListener {
//
//    private static String naam;
//    private static String adres;
//    private static String telefoon;
//    private static String email;
//
//    Button afbreken;
//    Button bestellen;
//
//    TextView gekozen;
//    String gekozenservice;
//
//    EditText naamInvulVeld;
//    EditText adresInvulVeld;
//    EditText telefoonInvulVeld;
//    EditText emailInvulVeld;
//
//    String ip = Hoofdscherm.ipadres;
//    int port = Hoofdscherm.port;
//    String gegevensFix;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.bestelling_layout);
//
//        TextView gekozen = (TextView) findViewById(R.id.informatieView);
//        this.gekozen = gekozen;
//
//        Intent intent = getIntent();
//        gekozenservice = intent.getStringExtra("gekozen");
//        gekozen.setText("Uw keuze: "
//                + gekozenservice);
//
//        afbreken = (Button) findViewById(R.id.vorige);
//        afbreken.setOnClickListener(this);
//        bestellen = (Button) findViewById(R.id.volgende);
//        bestellen.setOnClickListener(this);
//
//        naamInvulVeld = (EditText) findViewById(R.id.naam);
//        adresInvulVeld = (EditText) findViewById(R.id.adres);
//        telefoonInvulVeld = (EditText) findViewById(R.id.telefoon);
//        emailInvulVeld = (EditText) findViewById(R.id.email);
//
//        String[] prefs = DataClass.getInstance(this)
//                .getCustomerInfoPreferences();
//        if (prefs[0] != null)
//            this.naamInvulVeld.setText(prefs[0]);
//        if (prefs[1] != null)
//            this.adresInvulVeld.setText(prefs[1]);
//        if (prefs[2] != null)
//            this.telefoonInvulVeld.setText(prefs[2]);
//        if (prefs[3] != null)
//            this.emailInvulVeld.setText(prefs[3]);
//    }
//
//    //In deze methode wordt de ingevulde tekst omgezet naar een JSON object
//    private void plaatsBestelling() {
//        final TextView naamKlant = (TextView) findViewById(R.id.naam);
//        final TextView adresKlant = (TextView) findViewById(R.id.adres);
//        final TextView telefoonKlant = (TextView) findViewById(R.id.telefoon);
//        final TextView emailKlant = (TextView) findViewById(R.id.email);
//
//        naam = naamInvulVeld.getText().toString();
//        adres = adresInvulVeld.getText().toString();
//        telefoon = telefoonInvulVeld.getText().toString();
//        email = emailInvulVeld.getText().toString();
//
//        JSONObject bestelling = new JSONObject();
//        JSONObject service = new JSONObject();
//        JSONObject gegevens = new JSONObject();
//        JSONArray bestelArray = new JSONArray();
//
//             try {
//                service.put("servicenaam", gekozenservice.toString());
//                gegevens.put("kopernaam", naam);
//                gegevens.put("koperadres", adres);
//                gegevens.put("kopertelnr", telefoon);
//                gegevens.put("koperemail", email);
//
//                bestelArray.put(service);
//                bestelArray.put(gegevens);
//                bestelling.put("aanvraag", bestelArray);
//
//                 } catch (JSONException e) {
//                    }
//                  String response = null;
//
//             try {
//                  try {
//                        response = new ServerCommunicator(ip,
//                        port, bestelling.toString()).execute().get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//                }
//             } catch (InterruptedException e1) {
//                e1.printStackTrace();
//                  }
//                if(response == null){}
//                 else{
//                    gegevensFix = response.replace("null", "");
//                    Toast.makeText(this, "Bedankt voor uw bestelling! Voor 12:00 uur besteld is vandaag nog binnen.", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(this, Hoofdscherm.class);
//                    startActivity(intent);
//                    finish();
//        }
//        naam = naamKlant.getText().toString();
//        adres = adresKlant.getText().toString();
//        telefoon = telefoonKlant.getText().toString();
//        email = emailKlant.getText().toString();
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        String[] invulGegevens = {
//                this.naamInvulVeld.getText().toString(),
//                this.adresInvulVeld.getText().toString(),
//                this.telefoonInvulVeld.getText().toString(),
//                this.emailInvulVeld.getText().toString() };
//        DataClass.getInstance(this).updateCustomerInfoPreferences(invulGegevens);
//
//        switch (v.getId()) {
//            case R.id.volgende:
//                plaatsBestelling();
//
//                break;
//
//            case R.id.vorige:
//                Intent intent = new Intent(this, Hoofdscherm.class);
//                startActivity(intent);
//                finish();
//                break;
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            onBackPressed();
//        }
//
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, Hoofdscherm.class);
//        startActivity(intent);
//        finish();
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                Intent i = new Intent(this, Hoofdscherm.class);
//                startActivity(i);
//                finish();
//
//                return (true);
//        }
//
//        return (super.onOptionsItemSelected(item));
//    }
//
//}
