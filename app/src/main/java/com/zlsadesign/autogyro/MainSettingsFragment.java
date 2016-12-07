package com.zlsadesign.autogyro;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.ListPreference;

public class MainSettingsFragment extends PreferenceFragment {

  private ListPreference mListPreference;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preference_main);
  }

}
