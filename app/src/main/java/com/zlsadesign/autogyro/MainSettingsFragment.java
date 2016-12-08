package com.zlsadesign.autogyro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.preference.ListPreference;
import android.util.Log;

import com.iamhabib.easy_preference.EasyPreference;

import org.greenrobot.eventbus.EventBus;

public class MainSettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preference_main);
  }

  @Override
  public void onResume() {
    super.onResume();

    SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
    prefs.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();

    SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
    prefs.unregisterOnSharedPreferenceChangeListener(this);
  }


  @Override
  public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

    if(key.equals("enabled")) {
      boolean enabled = prefs.getBoolean("enabled", true);

      if(enabled) {
        EventBus.getDefault().postSticky(new CommandEvent(CommandEvent.COMMAND_START));
      } else {
        EventBus.getDefault().postSticky(new CommandEvent(CommandEvent.COMMAND_STOP));
      }

    }

  }

}
