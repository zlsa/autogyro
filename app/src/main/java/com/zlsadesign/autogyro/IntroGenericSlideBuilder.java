package com.zlsadesign.autogyro;

import android.content.Context;

public class IntroGenericSlideBuilder {

  public String sTitle = "Title";
  public String sDescription = "Description";
  public int sImage = R.drawable.ic_finish;

  public int sBackground = R.color.material_blue_500;
  public int sButtons = R.color.material_blue_700;

  protected Context context;

  public IntroGenericSlideBuilder(Context context) {
    this.context = context;
  }

  public IntroGenericSlideBuilder title(String title) {
    this.sTitle = title;
    return this;
  }

  public IntroGenericSlideBuilder title(int title) {
    return this.title(this.context.getString(title));
  }

  public IntroGenericSlideBuilder description(String description) {
    this.sDescription = description;
    return this;
  }

  public IntroGenericSlideBuilder description(int description) {
    return this.description(this.context.getString(description));
  }

  public IntroGenericSlideBuilder image(int image) {
    this.sImage = image;
    return this;
  }

  public IntroGenericSlideBuilder backgroundColor(int color) {
    this.sBackground = color;
    return this;
  }

  public IntroGenericSlideBuilder buttonsColor(int color) {
    this.sButtons = color;
    return this;
  }

  public IntroGenericSlide build() {
    IntroGenericSlide slide = new IntroGenericSlide();

    slide.setTitle(this.sTitle);
    slide.setDescription(this.sDescription);
    slide.setImage(this.sImage);

    slide.setBackground(this.sBackground);
    slide.setButtons(this.sButtons);

    return slide;
  }

}
