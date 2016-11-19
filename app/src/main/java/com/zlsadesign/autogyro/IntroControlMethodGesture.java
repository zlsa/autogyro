package com.zlsadesign.autogyro;

import android.app.Activity;

public class IntroControlMethodGesture extends IntroControlMethod {

  public IntroControlMethodGesture(Activity activity) {
    super(activity);

    this.setName(R.string.control_method_use_gestures);
  }

  void initItems() {
    super.initItems();

    this.addCheckBox(0, "Use vertical dragging instead of rotation", false);

    this.setParallaxStrength(0.25f);
  }

}
