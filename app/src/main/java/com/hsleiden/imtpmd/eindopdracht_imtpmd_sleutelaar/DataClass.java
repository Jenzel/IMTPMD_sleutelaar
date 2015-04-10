package com.hsleiden.imtpmd.eindopdracht_imtpmd_sleutelaar;

/**
* Created by Jens Brokaar
* s1066589
* IMTPMD
*/

import java.util.ArrayList;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataClass {

    //Van te voren geïnitieerde instellingen die doorgegeven worden aan de overige classes
    private Editor editor;
    private SharedPreferences instellingen;

    private static final String PREFS_NAME = "Eindopdracht de Sleutelaar";
    public static String[] hoofdschermDefault = { "spinnerDropdown" };
    public static String[] klantenInfoDefault = { "naam", "adres", "telefoon", "email" };
    private static DataClass _instance;

    public static DataClass getInstance(Context c)
    {
        if( _instance == null )
            _instance = new DataClass(c);

        return _instance;
    }

    private DataClass(Context c)
    {
        instellingen = c.getSharedPreferences(PREFS_NAME, 0);
        editor = instellingen.edit();
    }

    public String[] getMainActivityPreferences()
    {
        ArrayList<String> resp = new ArrayList<String>();
        for(int i = 0; i < hoofdschermDefault.length; i++)
        {
            resp.add(instellingen.getString(hoofdschermDefault[i], null));
        }
        return (String[]) resp.toArray(new String[hoofdschermDefault.length]);
    }

    public String[] getCustomerInfoPreferences()
    {
        ArrayList<String> resp = new ArrayList<String>();
        for(int i = 0; i < klantenInfoDefault.length; i++)
        {
            resp.add(instellingen.getString(klantenInfoDefault[i], null));
        }
        return (String[]) resp.toArray(new String[klantenInfoDefault.length]);
    }

    //Vaste instellingen na het laatste gebruik
    public void updateMainActivityPreferences(String[] input)
    {
        if(input == null)
            return;
        for(int i = 0; i < input.length; i++)
        {
            editor.putString(hoofdschermDefault[i], input[i]);
        }
        editor.commit();
    }

    //Vaste gebruikersgegevens na het laatste gebruik
    public void updateCustomerInfoPreferences(String[] input)
    {
        for(int i = 0; i < input.length; i++)
        {
            editor.putString(klantenInfoDefault[i], input[i]);
        }
        editor.commit();
    }
}

