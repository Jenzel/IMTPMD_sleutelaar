package com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar;

/**
* Created by Jens Brokaar
* s1066589
* IMTPMD
*/

import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar.Hoofdscherm;


public class Bestellingscherm extends Activity {

    private static String naam;
    private static String adres;
    private static String telefoon;
    private static String email;

    Button afbreken;
    Button bestellen;

    TextView gekozen;
    String gekozenservice;

    EditText naamInvulVeld;
    EditText adresInvulVeld;
    EditText telefoonInvulVeld;
    EditText emailInvulVeld;

    String ip = Hoofdscherm.ipadres;
    int port = Hoofdscherm.port;
    String gegevensFix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestelling_layout);

        TextView gekozen = (TextView) findViewById(R.id.informatieView);
        this.gekozen = gekozen;

        Intent intent = getIntent();
        gekozenservice = intent.getStringExtra("gekozen");
        gekozen.setText("Uw keuze: "
                + gekozenservice);

        naamInvulVeld = (EditText) findViewById(R.id.naam);
        adresInvulVeld = (EditText) findViewById(R.id.adres);
        telefoonInvulVeld = (EditText) findViewById(R.id.telefoon);
        emailInvulVeld = (EditText) findViewById(R.id.email);

        String[] prefs = DataClass.getInstance(this)
                .getCustomerInfoPreferences();
        if (prefs[0] != null)
            this.naamInvulVeld.setText(prefs[0]);
        if (prefs[1] != null)
            this.adresInvulVeld.setText(prefs[1]);
        if (prefs[2] != null)
            this.telefoonInvulVeld.setText(prefs[2]);
        if (prefs[3] != null)
            this.emailInvulVeld.setText(prefs[3]);
    }


}
