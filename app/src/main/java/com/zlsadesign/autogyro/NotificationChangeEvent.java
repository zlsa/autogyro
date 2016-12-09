package com.zlsadesign.autogyro;

class NotificationChangeEvent {

  static String COMMAND_SHOW = "show";
  static String COMMAND_HIDE = "hide";

  static String COMMAND_BUTTON_HIDE = "button_hide";
  static String COMMAND_BUTTON_SHOW = "button_show";
  static String COMMAND_PRIORITY = "priority";

  static String BUTTON_LEFT = "left";
  static String BUTTON_RIGHT = "right";
  static String BUTTON_FLIP = "flip";
  static String BUTTON_SETTINGS = "settings";


  private String command;
  private String button;

  NotificationChangeEvent(String command, String button) {
    this.command = command;
    this.button = button;
  }

  NotificationChangeEvent(String command) {
    this(command, "");
  }

  String getCommand() {
    return command;
  }

  public String getButton() {
    return button;
  }

}
