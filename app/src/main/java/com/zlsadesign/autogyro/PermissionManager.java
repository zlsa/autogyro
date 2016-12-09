package com.zlsadesign.autogyro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class PermissionManager {

  private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 0;
  private static final int ACTION_MANAGE_SETTINGS_PERMISSION_REQUEST_CODE = 1;

  private Activity activity;
  private Context context;

  PermissionManager(Activity activity) {
    this.activity = activity;
    this.context = activity;
  }

  PermissionManager(Context context) {
    this.context = context;
  }

  public boolean hasOverlayPermission() {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return Settings.canDrawOverlays(this.context);
    }
    return true;
  }

  public void requestOverlayPermission() {
    Intent intent = null;

    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.activity.getPackageName()));
      activity.startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
    }

  }

  public boolean hasSettingsPermission() {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return Settings.System.canWrite(this.context);
    }
    return true;
  }

  public void requestSettingsPermission() {
    Intent intent = null;

    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + this.activity.getPackageName()));
      activity.startActivityForResult(intent, ACTION_MANAGE_SETTINGS_PERMISSION_REQUEST_CODE);
    }

  }

}
