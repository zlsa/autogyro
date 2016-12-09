package com.zlsadesign.autogyro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroOverlayPermissionSlide extends IntroGenericSlide {

  private String TAG = "IntroOverlayPermissionSlide";

  private PermissionManager pm;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    this.pm = new PermissionManager(getActivity());

    this.setTitle(R.string.intro_slide_overlay_permission_title);
    this.setDescription(R.string.intro_slide_overlay_permission_description);
    this.setImage(R.mipmap.image_intro_overlay_permission);

    this.setBackground(R.color.material_blue_grey_700);
    this.setButtons(R.color.material_blue_grey_800);

    return this.root;
  }

  @Override
  public boolean canMoveFurther() {
    return this.pm.hasOverlayPermission();
  }

  @Override
  public String cantMoveFurtherErrorMessage() {
    return getString(R.string.intro_slide_overlay_permission_not_granted);
  }

}
