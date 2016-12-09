package com.zlsadesign.autogyro;

class CommandEvent {

  static String COMMAND_START = "start";
  static String COMMAND_STOP = "stop";
  static String COMMAND_LEFT = "left";
  static String COMMAND_RIGHT = "right";
  static String COMMAND_FLIP = "flip";

  static String COMMAND_SHOW_NOTIFICATION = "show_notify";
  static String COMMAND_HIDE_NOTIFICATION = "hide_notify";

  private String command;

  CommandEvent(String command) {
    this.command = command;
  }

  String getCommand() {
    return command;
  }

}
