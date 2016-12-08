package com.zlsadesign.autogyro;

import android.annotation.TargetApi;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.N)
public class TileLeftService extends AutogyroTileService {

  @Override
  public String getCommand() {
    return CommandEvent.COMMAND_LEFT;
  }

}
