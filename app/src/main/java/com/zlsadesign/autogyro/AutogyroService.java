package com.zlsadesign.autogyro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.provider.Settings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AutogyroService extends Service {

  static int NOTIFICATION_ID = 2948;

  static int ROTATION_PORTRAIT = 0;
  static int ROTATION_LANDSCAPE = 1;
  static int ROTATION_PORTRAIT_INVERT = 2;
  static int ROTATION_LANDSCAPE_INVERT = 3;

  static String ACTION_ROTATE_LEFT = "rotate_left";
  static String ACTION_ROTATE_RIGHT = "rotate_right";
  static String ACTION_FLIP = "flip";

  private View overlay;
  private WindowManager.LayoutParams params;
  private WindowManager wm;

  private AutogyroNotification notification;

  private boolean running = false;

  private int accelerometer_rotation = 0;
  private int user_rotation = Surface.ROTATION_0;

  private int rotation = ROTATION_PORTRAIT;

  private BroadcastReceiver receiver;

  @Override
  public void onCreate() {

    PermissionManager pm = new PermissionManager(this);

    if(!pm.hasOverlayPermission() || !pm.hasSettingsPermission()) {
      stop();
    }

    EventBus.getDefault().register(this);

    createNotification();

    if(getPrefs().getBoolean("enabled", false)) {
      start();
    }

  }

  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    Log.i("onDestroy", "destroyed");
    EventBus.getDefault().unregister(this);
    stop();

    destroyNotification();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  protected void start() {
    if(running) return;

    running = true;

    if(overlay == null)
      createOverlay();

    saveSettings();
    addOverlay();

    addNotification();

    restoreRotation();
    setRotation();
  }

  protected void stop() {
    if(!running) return;

    running = false;

    restoreSettings();
    removeOverlay();
    removeNotification();
  }

  protected void toggle(boolean enabled) {
    if(enabled) start();
    else        stop();
  }

  private void saveSettings() {
    try {
      accelerometer_rotation = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
    } catch (Settings.SettingNotFoundException e) {
      Log.d("saveSettings", "ACCELEROMETER_ROTATION not found");
    }

    try {
      user_rotation = Settings.System.getInt(getContentResolver(), Settings.System.USER_ROTATION);
    } catch (Settings.SettingNotFoundException e) {
      Log.d("saveSettings", "USER_ROTATION not found");
    }

    Log.d("saveSettings", "USER_ROTATION = " + user_rotation);

    Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
  }

  private void restoreSettings() {
    Settings.System.putInt(getContentResolver(), Settings.System.USER_ROTATION, user_rotation);
    Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, accelerometer_rotation);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onCommandEvent(CommandEvent event) {
    Log.d("onCommandEvent", "command " + event.getCommand());

    // Start/stop

    if(event.getCommand().equals(CommandEvent.COMMAND_START)) {
      start();
    } else if(event.getCommand().equals(CommandEvent.COMMAND_STOP)) {
      stop();

      // Rotation
    } else if(event.getCommand().equals(CommandEvent.COMMAND_LEFT)) {
      offsetRotation(-1);
    } else if(event.getCommand().equals(CommandEvent.COMMAND_RIGHT)) {
      offsetRotation(1);
    } else if(event.getCommand().equals(CommandEvent.COMMAND_FLIP)) {
      offsetRotation(2);
    }
  }

  private SharedPreferences getPrefs() {
    return PreferenceManager.getDefaultSharedPreferences(this);
  }

  private void restoreRotation() {
    this.rotation = getPrefs().getInt("rotation", ROTATION_PORTRAIT);
  }

  private void saveRotation() {
    SharedPreferences.Editor editor = getPrefs().edit();
    editor.putInt("rotation", this.rotation);
    editor.apply();
  }

  // Overlay management.

  protected void createOverlay() {
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    overlay = inflater.inflate(R.layout.view_overlay, null);
    params = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
        PixelFormat.TRANSLUCENT);

    params.gravity = Gravity.CENTER | Gravity.TOP;
    params.width = 0;
    params.height = 0;

    params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;

    wm = (WindowManager) getSystemService(WINDOW_SERVICE);
  }

  protected void addOverlay() {
    wm.addView(overlay, params);
  }

  protected void removeOverlay() {
    if(overlay != null) {
      wm.removeView(overlay);
      overlay = null;
    }
  }

  protected void createNotification() {
    notification = new AutogyroNotification(this);

    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_ROTATE_LEFT);
    filter.addAction(ACTION_ROTATE_RIGHT);
    filter.addAction(ACTION_FLIP);

    receiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_ROTATE_LEFT)) {
          offsetRotation(-1);
        } else if(intent.getAction().equals(ACTION_ROTATE_RIGHT)) {
          offsetRotation(1);
        } else if(intent.getAction().equals(ACTION_FLIP)) {
          offsetRotation(2);
        }
      }
    };

    registerReceiver(receiver, filter);
  }

  protected void addNotification() {
    notification.show();
  }

  protected void removeNotification() {
    notification.hide();
  }

  protected void destroyNotification() {
    notification.destroy();
  }

  protected void setRotation(int new_rotation) {
    this.rotation = ((new_rotation % 4) + 4) % 4;

    Log.d("setRotation", "requested rotation: " + new_rotation);
    Log.d("setRotation", "new rotation: " + this.rotation);

    int[] overlay_orientations = {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,
        ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT,
        ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
    };

    int[] system_orientations = {
        Surface.ROTATION_0,
        Surface.ROTATION_90,
        Surface.ROTATION_180,
        Surface.ROTATION_270
    };

    int overlay_rotation = overlay_orientations[this.rotation];
    int system_rotation = system_orientations[this.rotation];

    if(overlay != null) {
      params.screenOrientation = overlay_rotation;
      wm.updateViewLayout(overlay, params);
    }

    Settings.System.putInt(getContentResolver(), Settings.System.USER_ROTATION, system_rotation);

    Log.d("setRotation", "system rotation should be: " + system_rotation);
    try {
      int system_rot = Settings.System.getInt(getContentResolver(), Settings.System.USER_ROTATION);
      Log.d("setRotation", "system rotation is actually: " + system_rot);
    } catch (Settings.SettingNotFoundException e) {
      Log.d("saveSettings", "USER_ROTATION not found");
    }

    saveRotation();
  }

  protected void offsetRotation(int offset) {
    Log.d("offsetRotation", "offset: " + offset);
    setRotation(this.rotation + offset);
  }

  protected void setRotation() {
    setRotation(this.rotation);
  }

}
