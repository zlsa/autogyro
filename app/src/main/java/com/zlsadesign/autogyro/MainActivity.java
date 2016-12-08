package com.zlsadesign.autogyro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.iamhabib.easy_preference.EasyPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

  protected PermissionManager pm;
  protected EasyPreference.Builder prefs;

  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    setActionBar(toolbar);

    this.pm = new PermissionManager(this);
    this.prefs = EasyPreference.with(this);

    // If we don't have permissions, OR if we've never used the intro screen; show the intro screen

    if(!pm.hasOverlayPermission() || !pm.hasSettingsPermission() || !usedIntroScreen()){
      Log.d("onCreate", "starting intro activity");
      Intent intent = new Intent(this, IntroActivity.class);
      startActivity(intent);
    }

    setUsedIntroScreen();
  }

  private boolean usedIntroScreen() {
    return prefs.getBoolean("usedIntroScreen", false);
  }

  private void setUsedIntroScreen() {
    prefs.addBoolean("usedIntroScreen", true).save();
  }

}
