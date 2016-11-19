package com.zlsadesign.autogyro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import agency.tango.materialintroscreen.SlideFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroGenericSlide extends SlideFragment {

  static final String STATE_TITLE = "title";
  static final String STATE_DESCRIPTION = "description";
  static final String STATE_IMAGE = "image";
  static final String STATE_BACKGROUND = "background";
  static final String STATE_BUTTONS = "buttons";

  static final String TAG = "IntroGenericSlide";

  protected Context context;

  protected View root;

  @BindView(R.id.image_slide) ImageView vImage;
  @BindView(R.id.txt_title_slide) TextView vTitle;
  @BindView(R.id.txt_description_slide) TextView vDescription;

  private String sTitle = "Title";
  private String sDescription = "Description";
  private int sImage = R.drawable.ic_finish;

  private int sBackground = R.color.primary;
  private int sButtons = R.color.primary_dark;

  @Override
  public void onAttach(Context context) {
    this.context = context;
    super.onAttach(context);
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {

    savedInstanceState.putString(STATE_TITLE, sTitle);
    savedInstanceState.putString(STATE_DESCRIPTION, sDescription);
    savedInstanceState.putInt(STATE_IMAGE, sImage);

    savedInstanceState.putInt(STATE_BACKGROUND, sBackground);
    savedInstanceState.putInt(STATE_BUTTONS, sButtons);

    super.onSaveInstanceState(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle state) {
    this.root = inflater.inflate(R.layout.slide_generic, container, false);

    ButterKnife.bind(this, this.root);

    if(state != null) {
      sTitle = state.containsKey(STATE_TITLE) ? state.getString(STATE_TITLE) : sTitle;
      sDescription = state.containsKey(STATE_DESCRIPTION) ? state.getString(STATE_DESCRIPTION) : sDescription;
      sImage = state.containsKey(STATE_IMAGE) ? state.getInt(STATE_IMAGE) : sImage;

      sBackground = state.containsKey(STATE_BACKGROUND) ? state.getInt(STATE_BACKGROUND) : sBackground;
      sButtons = state.containsKey(STATE_BUTTONS) ? state.getInt(STATE_BUTTONS) : sButtons;
    }

    vTitle.setText(sTitle);
    vDescription.setText(sDescription);
    vImage.setImageDrawable(this.context.getDrawable(this.sImage));

    //R.mipmap.image_intro_settings_permission

    vImage.setVisibility(View.VISIBLE);

    return this.root;
  }

  public void setTitle(String title) {
    this.sTitle = title;

    if(this.vTitle != null) {
      this.vTitle.setText(sTitle);
    }
  }

  public void setTitle(int title) {
    this.setTitle(getString(title));
  }

  public void setDescription(String description) {
    this.sDescription = description;

    if(this.vDescription != null) {
      this.vDescription.setText(sDescription);
    }
  }

  public void setDescription(int description) {
    this.setDescription(getString(description));
  }

  public void setImage(int image) {
    this.sImage = image;

    if(this.vImage != null) {
      this.vImage.setImageDrawable(this.context.getDrawable(this.sImage));
    }
  }

  public void setBackground(int color) {
    this.sBackground = color;
  }

  public void setButtons(int color) {
    this.sButtons = color;
  }

  @Override
  public int backgroundColor() {
    return this.sBackground;
  }

  @Override
  public int buttonsColor() {
    return this.sButtons;
  }

  @Override
  public String cantMoveFurtherErrorMessage() {
    return getString(R.string.intro_slide_overlay_permission_not_granted);
  }

}
