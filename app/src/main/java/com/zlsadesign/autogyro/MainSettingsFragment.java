package com.zlsadesign.autogyro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

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

    } else if(key.equals("show_notification")) {
      boolean enabled = prefs.getBoolean("show_notification", true);

      if(enabled) {
        EventBus.getDefault().postSticky(new NotificationChangeEvent(NotificationChangeEvent.COMMAND_SHOW));
      } else {
        EventBus.getDefault().postSticky(new NotificationChangeEvent(NotificationChangeEvent.COMMAND_HIDE));
      }

    } else if(key.startsWith("show_notification_")) {
      boolean enabled = prefs.getBoolean(key, false);

      String button = "";
      switch(key) {
        case "show_notification_left":
          button = NotificationChangeEvent.BUTTON_LEFT;
          break;
        case "show_notification_right":
          button = NotificationChangeEvent.BUTTON_RIGHT;
          break;
        case "show_notification_flip":
          button = NotificationChangeEvent.BUTTON_FLIP;
          break;
      }

      if(enabled) {
        EventBus.getDefault().postSticky(new NotificationChangeEvent(NotificationChangeEvent.COMMAND_BUTTON_SHOW, button));
      } else {
        EventBus.getDefault().postSticky(new NotificationChangeEvent(NotificationChangeEvent.COMMAND_BUTTON_HIDE, button));
      }

    }

  }

}
