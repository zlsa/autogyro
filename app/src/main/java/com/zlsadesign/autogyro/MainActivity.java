package com.zlsadesign.autogyro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

  protected PermissionManager pm;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    setActionBar(toolbar);

    this.pm = new PermissionManager(this);

    // If we don't have permissions, OR if we've never used the intro screen; show the intro screen

    if(!pm.hasOverlayPermission() || !pm.hasSettingsPermission() || !usedIntroScreen()){
      Log.d("onCreate", "starting intro activity");
      Intent intent = new Intent(this, IntroActivity.class);
      startActivity(intent);
    }

    setUsedIntroScreen();

    startService(new Intent(this, AutogyroService.class));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  private void openLibraryInfo() {
    startActivity(new Intent(this, LibraryInfoActivity.class));
  }

  private void openHelpFeedback() {
    startActivity(new Intent(this, HelpFeedbackActivity.class));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.open_library_info:
        openLibraryInfo();
        return true;
      case R.id.open_help_feedback:
        openHelpFeedback();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }


  private SharedPreferences getPrefs() {
    return PreferenceManager.getDefaultSharedPreferences(this);
  }

  private boolean usedIntroScreen() {
    return getPrefs().getBoolean("usedIntroScreen", false);
  }

  private void setUsedIntroScreen() {
    SharedPreferences.Editor editor = getPrefs().edit();
    editor.putBoolean("usedIntroScreen", true);
    editor.apply();
  }

}
