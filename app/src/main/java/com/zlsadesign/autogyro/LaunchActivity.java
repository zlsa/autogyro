package com.zlsadesign.autogyro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.iamhabib.easy_preference.EasyPreference;

public class LaunchActivity extends Activity {

  protected PermissionManager pm;
  protected EasyPreference.Builder prefs;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

}
