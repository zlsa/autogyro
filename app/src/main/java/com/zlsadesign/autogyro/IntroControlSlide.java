package com.zlsadesign.autogyro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import agency.tango.materialintroscreen.SlideFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroControlSlide extends SlideFragment {

  @BindView(R.id.control_methods_list)
  LinearLayout vControlMethods;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.slide_intro_control, container, false);

    ButterKnife.bind(this, view);

    this.addControlMethod(new IntroControlMethodNotification(getActivity()));
    this.addControlMethod(new IntroControlMethodGesture(getActivity()));

    return view;
  }

  private void addControlMethod(IntroControlMethod method) {
    this.vControlMethods.addView(method.create(this.vControlMethods).getView());
  }

  @Override
  public int backgroundColor() {
    return R.color.material_blue_grey_500;
  }

  @Override
  public int buttonsColor() {
    return R.color.material_blue_grey_700;
  }

  @Override
  public boolean canMoveFurther() {
    return true;
  }

  @Override
  public String cantMoveFurtherErrorMessage() {
    return getString(R.string.intro_slide_control_at_least_one_method);
  }
}
