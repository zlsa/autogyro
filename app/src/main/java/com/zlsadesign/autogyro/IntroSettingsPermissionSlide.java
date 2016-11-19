package com.zlsadesign.autogyro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroSettingsPermissionSlide extends IntroGenericSlide {

  private String TAG = "IntroSettingsPermissionSlide";

  private PermissionManager pm;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    this.pm = new PermissionManager(getActivity());

    this.setTitle(R.string.intro_slide_settings_permission_title);
    this.setDescription(R.string.intro_slide_settings_permission_description);
    this.setImage(R.mipmap.image_intro_settings_permission);

    return this.root;
  }

  @Override
  public boolean canMoveFurther() {
    return this.pm.hasSettingsPermission();
  }

  @Override
  public String cantMoveFurtherErrorMessage() {
    return getString(R.string.intro_slide_overlay_permission_not_granted);
  }

}
