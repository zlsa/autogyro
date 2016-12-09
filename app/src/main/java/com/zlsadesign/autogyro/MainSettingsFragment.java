package com.zlsadesign.autogyro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;

public class MainSettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preference_main);

    Context hostActivity = getActivity();
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(hostActivity);

    updateNotificationPriority(Integer.valueOf(prefs.getString("notification_priority", "4")));
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

  private void updateNotificationPriority(int priority) {
    String[] priorities = getResources().getStringArray(R.array.notification_priority);

    Preference pref = findPreference("notification_priority");
    pref.setSummary(priorities[priority]);
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

    } else if(key.equals("notification_priority")) {
      int priority = Integer.valueOf(prefs.getString(key, "4"));

      EventBus.getDefault().postSticky(new NotificationChangeEvent(NotificationChangeEvent.COMMAND_PRIORITY, String.valueOf(priority)));

      updateNotificationPriority(priority);
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
        case "show_notification_settings":
          button = NotificationChangeEvent.BUTTON_SETTINGS;
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
