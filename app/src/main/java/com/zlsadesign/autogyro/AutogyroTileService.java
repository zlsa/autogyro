package com.zlsadesign.autogyro;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@TargetApi(Build.VERSION_CODES.N)
abstract public class AutogyroTileService extends TileService {

  abstract public String getCommand();

  public void onClick() {
    Log.d("onClick", "command: " + this.getCommand());
    EventBus.getDefault().post(new CommandEvent(this.getCommand()));
  }

  public void onStartListening() {
    super.onStartListening();

    Log.d("onStartListening", "Listening!");
    EventBus.getDefault().register(this);
  }

  public void onStopListening() {
    super.onStopListening();

    Log.d("onStopListening", "No longer listening.");
    EventBus.getDefault().unregister(this);
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onCommandEvent(CommandEvent event) {

    String command = event.getCommand();

    if(command.equals(CommandEvent.COMMAND_START)) {
      this.activate();
    } else if(command.equals(CommandEvent.COMMAND_STOP)) {
      this.deactivate();
    }

  }

  private void activate() {
    Log.d("activation", "activated");

    Tile tile = getQsTile();

    tile.setState(Tile.STATE_ACTIVE);
    tile.updateTile();
  }

  private void deactivate() {
    Log.d("activation", "deactivated");

    Tile tile = getQsTile();

    tile.setState(Tile.STATE_UNAVAILABLE);
    tile.updateTile();
  }

}
