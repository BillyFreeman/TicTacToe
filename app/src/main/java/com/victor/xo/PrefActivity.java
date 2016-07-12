package com.victor.xo;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;

/**
 * Created by Віктор on 05.05.2015.
 */
public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
