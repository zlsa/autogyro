package com.zlsadesign.autogyro;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

public class LibraryInfoAdapter extends ArrayAdapter<LibraryInfo> {

  public LibraryInfoAdapter(Context context, int resource, List<LibraryInfo> items) {
    super(context, resource, items);
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {

    if(view == null) {
      LayoutInflater vi;
      vi = LayoutInflater.from(getContext());
      view = vi.inflate(R.layout.item_library_info, null);
    }

    LibraryInfo li = getItem(position);

    if(li != null) {
      ((TextView) ButterKnife.findById(view, R.id.library_name)).setText(li.name);
      ((TextView) ButterKnife.findById(view, R.id.library_author)).setText(li.author);

      if(li.description.isEmpty()) {
        ButterKnife.findById(view, R.id.library_description_container).setVisibility(View.GONE);
      } else {
        ButterKnife.findById(view, R.id.library_description_container).setVisibility(View.VISIBLE);
        ((TextView) ButterKnife.findById(view, R.id.library_description)).setText(li.description);
      }
    }

    return view;
  }

}
