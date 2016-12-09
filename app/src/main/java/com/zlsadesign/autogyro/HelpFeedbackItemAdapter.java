package com.zlsadesign.autogyro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

public class HelpFeedbackItemAdapter extends ArrayAdapter<HelpFeedbackItem> {

  public HelpFeedbackItemAdapter(Context context, int resource, List<HelpFeedbackItem> items) {
    super(context, resource, items);
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {

    if(view == null) {
      LayoutInflater vi;
      vi = LayoutInflater.from(getContext());
      view = vi.inflate(R.layout.item_help_feedback, null);
    }

    HelpFeedbackItem item = getItem(position);

    if(item != null) {

      if(item.icon != 0) {
        ((ImageView) ButterKnife.findById(view, R.id.icon)).setImageResource(item.icon);
        ButterKnife.findById(view, R.id.icon).setVisibility(View.VISIBLE);
      } else {
        ButterKnife.findById(view, R.id.icon).setVisibility(View.INVISIBLE);
      }

      ((TextView) ButterKnife.findById(view, R.id.primary)).setText(item.primary);
      ((TextView) ButterKnife.findById(view, R.id.secondary)).setText(item.secondary);
    }

    return view;
  }

}
