package com.zlsadesign.autogyro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryInfoActivity extends Activity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.list)
  ListView listview;

  List<LibraryInfo> libraries = new ArrayList<LibraryInfo>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_library_info);

    ButterKnife.bind(this);

    setActionBar(toolbar);
    getActionBar().setDisplayHomeAsUpEnabled(true);

    addLibrary(
        new LibraryInfo()
            .setName("Material Intro Screen")
            .setAuthor("TangoAgency")
            .setLink("https://github.com/TangoAgency/material-intro-screen")
            .setDescription("Inspired by Heinrich Reimer's Material Intro library; developed with love from scratch.")
            .setLicense("MIT")
    );

    addLibrary(
        new LibraryInfo()
            .setName("ButterKnife")
            .setAuthor("Jake Wharton")
            .setLink("https://github.com/JakeWharton/butterknife")
            .setDescription("Bind Android views and callbacks to fields and methods.")
            .setLicense("Apache License, Version 2.0")
    );

    addLibrary(
        new LibraryInfo()
            .setName("Material Design Dimens")
            .setAuthor("Dmitry Malkovich")
            .setLink("https://github.com/DmitryMalkovich/material-design-dimens")
            .setDescription("Material Design dimensions and colors, packaged up as easy-to-use resources.")
            .setLicense("Apache License, Version 2.0")
    );

    addLibrary(
        new LibraryInfo()
            .setName("EventBus")
            .setAuthor("Markus Junginger")
            .setLink("https://github.com/greenrobot/EventBus")
            .setDescription("Android optimized event bus that simplifies communication between Activities, Fragments, Threads, Services, etc. Less code, better quality.")
            .setLicense("Apache License, Version 2.0")
    );

    Collections.sort(libraries, new LibraryInfoComparator());

    LibraryInfoAdapter customAdapter = new LibraryInfoAdapter(this, R.layout.item_library_info, libraries);

    listview.setAdapter(customAdapter);

    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);

        if(!libraries.get(position).link.isEmpty()) {
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(libraries.get(position).link)));
        }

      }

    });


  }

  private void addLibrary(LibraryInfo info) {
    libraries.add(info);
  }

}