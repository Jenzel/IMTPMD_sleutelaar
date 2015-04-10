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

    private static DataClass _instance;
    private static final String PREFS_NAME = "Eindopdracht de Sleutelaar";
    private SharedPreferences settings;
    private Editor editor;
    public static String[] mainViewPreferences = { "spinnerDropdown" };
    public static String[] customerInfoPreferences = { "naam", "adres", "telefoon", "email" };

    public static DataClass getInstance(Context c)
    {
        if( _instance == null )
            _instance = new DataClass(c);

        return _instance;
    }

    private DataClass(Context c)
    {
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
    }

    public String[] getMainActivityPreferences()
    {
        ArrayList<String> resp = new ArrayList<String>();
        for(int i = 0; i < mainViewPreferences.length; i++)
        {
            resp.add(settings.getString(mainViewPreferences[i], null));
        }
        return (String[]) resp.toArray(new String[mainViewPreferences.length]);
    }

    public String[] getCustomerInfoPreferences()
    {
        ArrayList<String> resp = new ArrayList<String>();
        for(int i = 0; i < customerInfoPreferences.length; i++)
        {
            resp.add(settings.getString(customerInfoPreferences[i], null));
        }
        return (String[]) resp.toArray(new String[customerInfoPreferences.length]);
    }

    public void updateMainActivityPreferences(String[] input)
    {
        if(input == null)
            return;
        for(int i = 0; i < input.length; i++)
        {
            editor.putString(mainViewPreferences[i], input[i]);
        }
        editor.commit();
    }

    public void updateCustomerInfoPreferences(String[] input)
    {
        for(int i = 0; i < input.length; i++)
        {
            editor.putString(customerInfoPreferences[i], input[i]);
        }
        editor.commit();
    }
}

