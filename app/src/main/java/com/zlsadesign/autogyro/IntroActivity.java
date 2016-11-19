package com.zlsadesign.autogyro;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class IntroActivity extends MaterialIntroActivity {

  public PermissionManager pm;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.pm = new PermissionManager(this);

    this.addWelcomeSlide();

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      this.addRequestOverlayPermissionSlide();
      this.addRequestSettingsPermissionSlide();
    }

    this.addControlMethodSlide();
    this.addReadySlide();

  }

  private void addRequestOverlayPermissionSlide() {

    final Context context = this;

    addSlide(new IntroOverlayPermissionSlide(),
            new MessageButtonBehaviour(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Toast.makeText(context, getString(R.string.intro_slide_overlay_permission_return_message), Toast.LENGTH_LONG).show();
                pm.requestOverlayPermission();
              }
            }, getString(R.string.intro_slide_permission_grant)));
  }

  private void addRequestSettingsPermissionSlide() {

    final Context context = this;

    addSlide(new IntroSettingsPermissionSlide(),
            new MessageButtonBehaviour(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Toast.makeText(context, getString(R.string.intro_slide_settings_permission_return_message), Toast.LENGTH_LONG).show();
                pm.requestSettingsPermission();
              }
            }, getString(R.string.intro_slide_permission_grant)));
  }

  private void addControlMethodSlide() {

    addSlide(new IntroControlSlide());
  }

  private void addWelcomeSlide() {

    int desc = R.string.intro_slide_welcome_description;

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      desc = R.string.intro_slide_welcome_description_m;
    }

    addSlide(new IntroGenericSlideBuilder(this)
            .backgroundColor(R.color.material_light_green_500)
            .buttonsColor(R.color.material_light_green_700)
            .image(R.mipmap.image_intro_ready)
            .title(R.string.intro_slide_welcome_title)
            .description(desc)
            .build());
  }

  private void addReadySlide() {

    addSlide(new IntroGenericSlideBuilder(this)
            .backgroundColor(R.color.material_light_green_500)
            .buttonsColor(R.color.material_light_green_700)
            .image(R.mipmap.image_intro_ready)
            .title(R.string.intro_slide_ready_title)
            .description(R.string.intro_slide_ready_description)
            .build());
  }

}
