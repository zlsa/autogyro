package com.zlsadesign.autogyro;

import android.app.Activity;

public class IntroControlMethodNotification extends IntroControlMethod {

  private static int SHOW_LEFT = 0;
  private static int SHOW_RIGHT = 1;
  private static int SHOW_FLIP = 2;

  public IntroControlMethodNotification(Activity activity) {
    super(activity);

    this.setName(R.string.control_method_notification_use);
  }

  void initItems() {
    super.initItems();

    this.addCheckBox(SHOW_LEFT, R.string.control_method_notification_show_left, true);
    this.addCheckBox(SHOW_RIGHT, R.string.control_method_notification_show_right, true);
    this.addCheckBox(SHOW_FLIP, R.string.control_method_notification_show_flip, false);

    this.setParallaxStrength(0.4f);
  }

}
