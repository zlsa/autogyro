package com.zlsadesign.autogyro;

import android.graphics.drawable.Drawable;

public class HelpFeedbackItem {

  static public int ACTION_NONE = 0;
  static public int ACTION_URI = 1;
  static public int ACTION_EMAIL = 2;

  public String primary = "";
  public String secondary = "";

  public int icon = 0;

  public int action_type = ACTION_NONE;
  public String action = "";

  public HelpFeedbackItem setPrimary(String primary) {
    this.primary = primary;
    return this;
  }

  public HelpFeedbackItem setSecondary(String secondary) {
    this.secondary = secondary;
    return this;
  }

  public HelpFeedbackItem setIcon(int icon) {
    this.icon = icon;
    return this;
  }

  public HelpFeedbackItem setAction(int action_type, String action) {
    this.action_type = action_type;
    this.action = action;
    return this;
  }

}

