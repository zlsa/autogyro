package com.zlsadesign.autogyro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpFeedbackActivity extends Activity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.list)
  ListView listview;

  List<HelpFeedbackItem> items = new ArrayList<HelpFeedbackItem>();


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_help_feedback);

    ButterKnife.bind(this);

    setActionBar(toolbar);
    getActionBar().setDisplayHomeAsUpEnabled(true);

    items.add(new HelpFeedbackItem()
        .setIcon(R.drawable.ic_notify)
        .setPrimary(getString(R.string.app_name))
        .setSecondary(BuildConfig.VERSION_NAME)
    );

    items.add(new HelpFeedbackItem()
        .setIcon(R.drawable.ic_link)
        .setPrimary(getString(R.string.help_website))
        .setSecondary("zlsadesign.com/autogyro")
        .setAction(HelpFeedbackItem.ACTION_URI, "http://zlsadesign.com/autogyro/")
    );

    items.add(new HelpFeedbackItem()
        .setIcon(R.drawable.ic_bug_report)
        .setPrimary(getString(R.string.help_bug_report))
        .setSecondary("github.com/zlsa/autogyro/issues/new")
        .setAction(HelpFeedbackItem.ACTION_URI, "https://github.com/zlsa/autogyro/issues/new")
    );

    items.add(new HelpFeedbackItem()
        .setIcon(R.drawable.ic_github)
        .setPrimary(getString(R.string.help_repository))
        .setSecondary("github.com/zlsa/autogyro")
        .setAction(HelpFeedbackItem.ACTION_URI, "https://github.com/zlsa/autogyro")
    );

    items.add(new HelpFeedbackItem()
        .setIcon(R.drawable.ic_email)
        .setPrimary(getString(R.string.help_email))
        .setSecondary("jonross.zlsa@gmail.com")
        .setAction(HelpFeedbackItem.ACTION_EMAIL, "jonross.zlsa@gmail.com")
    );

    HelpFeedbackItemAdapter customAdapter = new HelpFeedbackItemAdapter(this, R.layout.item_help_feedback, items);

    listview.setAdapter(customAdapter);

    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HelpFeedbackItem item = items.get(position);

        if(item.action_type == HelpFeedbackItem.ACTION_URI) {
          startActivityCheck(new Intent(Intent.ACTION_VIEW, Uri.parse(item.action)), "opening websites");
        } else if(item.action_type == HelpFeedbackItem.ACTION_EMAIL) {
          Intent intent = new Intent(Intent.ACTION_SEND);

          intent.setType("*/*");
          intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ item.action });
          intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

          startActivityCheck(intent, "sending emails");
        }

      }

    });

  }

  private void startActivityCheck(Intent intent, String destination) {
    if(intent.resolveActivity(getPackageManager()) != null) {
      startActivity(intent);
    } else {
      Toast.makeText(this, "No app available for " + destination, Toast.LENGTH_LONG).show();
    }
  }
}