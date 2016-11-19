package com.zlsadesign.autogyro;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import agency.tango.materialintroscreen.parallax.ParallaxLinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroControlMethod {

  protected Activity activity;
  protected String name = "";

  protected View root;

  @BindView(R.id.card_settings) View vSettings;
  @BindView(R.id.toggle_switch) Switch vSwitch;

  protected boolean enabled = false;

  Map<Integer, Boolean> mCheckBoxes = new HashMap<Integer, Boolean>();

  public IntroControlMethod(Activity activity) {
    this.activity = activity;
  }

  protected void setName(String name) {
    this.name = name;
  }

  protected void setName(int name) {
    this.setName(this.activity.getString(name));
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;

    this.setCollapse(!enabled);
  }

  public void setState(Bundle state) {
    if(state.containsKey("enabled"))
      this.setEnabled(state.getBoolean("enabled"));

    if(state.containsKey("checkbox"))
      this.setCheckBoxState(state.getBundle("checkbox"));
  }

  public Bundle getState() {
    Bundle bundle = new Bundle();

    bundle.putBoolean("enabled", this.enabled);

    bundle.putBundle("checkbox", this.getCheckBoxState());

    return bundle;
  }

  public void setCheckBoxState(Bundle state) {
    for(String key : state.keySet()) {
      //key, bundle.get(key).toString()); //To Implement
    }
  }

  public Bundle getCheckBoxState() {
    Bundle bundle = new Bundle();

    for(Map.Entry<Integer, Boolean> entry : this.mCheckBoxes.entrySet()) {
      bundle.putBoolean(Integer.toString(entry.getKey()), entry.getValue());
    }

    return bundle;
  }

  public boolean setParallaxStrength(float strength) {

    try {
      Field field = ParallaxLinearLayout.LayoutParams.class.getDeclaredField("parallaxFactor");
      field.setAccessible(true);
      field.set(this.root.getLayoutParams(), strength);
    } catch(NoSuchFieldException e) {
      return false;
    } catch (IllegalAccessException e) {
      return false;
    }

    return true;
  }

  // Add settings

  protected void addCheckBox(int number, String title, boolean checked) {
    LayoutInflater inflater = LayoutInflater.from(this.activity);
    View view = inflater.inflate(R.layout.view_control_method_setting_checkbox, (ViewGroup) this.root, false);

    TextView vName = ButterKnife.findById(view, R.id.name);
    final CheckBox vCheckBox = ButterKnife.findById(view, R.id.checkbox);

    vCheckBox.setChecked(checked);

    vName.setText(title);

    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        vCheckBox.setChecked(!vCheckBox.isChecked());
      }
    });

    mCheckBoxes.put(number, !checked);

    ((ViewGroup) this.vSettings).addView(view);
  }

  protected void addCheckBox(int number, int title, boolean checked) {
    addCheckBox(number, this.activity.getString(title), checked);
  }

  // View stuff

  public View getView() {
    return this.root;
  }

  IntroControlMethod create(ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(this.activity);
    this.root = inflater.inflate(R.layout.view_control_method, parent, false);

    ButterKnife.bind(this, this.root);

    this.initCollapse();

    vSwitch.setText(this.name);

    this.initItems();

    return this;
  }

  void initItems() {

  }

  // Expand/collapse stuff

  private void initCollapse() {
    vSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setEnabled(isChecked);
      }
    });

    vSwitch.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return event.getActionMasked() == MotionEvent.ACTION_MOVE;
      }
    });

  }

  private void setCollapse(boolean collapsed) {
    if(collapsed)
      collapse(vSettings);
    else
      expand(vSettings);
  }

  private static void expand(final View v) {
    v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    final int targetHeight = v.getMeasuredHeight();

    v.getLayoutParams().height = 1;
    v.setVisibility(View.VISIBLE);

    Animation a = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        v.getLayoutParams().height = interpolatedTime == 1
                ? ViewGroup.LayoutParams.WRAP_CONTENT
                : (int)(targetHeight * interpolatedTime);
        v.requestLayout();
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };

    a.setInterpolator(new AnticipateInterpolator(-1.0f));
    a.setDuration(targetHeight / 2);
    v.startAnimation(a);
  }

  private static void collapse(final View v) {
    final int initialHeight = v.getMeasuredHeight();

    Animation a = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        if(interpolatedTime == 1){
          v.setVisibility(View.GONE);
        }else{
          v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
          v.requestLayout();
        }
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };

    a.setInterpolator(new AnticipateInterpolator(-1.0f));
    a.setDuration(initialHeight / 2);
    v.startAnimation(a);
  }

}
