package com.zlsadesign.autogyro;

public class CommandEvent {

  static String COMMAND_START = "start";
  static String COMMAND_STOP = "stop";
  static String COMMAND_LEFT = "left";
  static String COMMAND_RIGHT = "right";
  static String COMMAND_FLIP = "flip";

  private String command;

  public CommandEvent(String command) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

}
