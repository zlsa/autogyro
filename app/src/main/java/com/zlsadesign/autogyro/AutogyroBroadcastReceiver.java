package com.zlsadesign.autogyro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutogyroBroadcastReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    context.startService(new Intent(context, AutogyroService.class));
  }


}
