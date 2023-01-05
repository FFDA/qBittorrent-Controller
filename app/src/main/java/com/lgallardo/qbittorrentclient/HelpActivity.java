/*
 *   Copyright (c) 2014-2015 Luis M. Gallardo D.
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the GNU Lesser General Public License v3.0
 *   which accompanies this distribution, and is available at
 *   http://www.gnu.org/licenses/lgpl.html
 *
 */
package com.lgallardo.qbittorrentclient;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_help_view, new HelpFragment())
                .commit();

        // Displays toolbar
        Toolbar imageViewActivityToolbar = findViewById(R.id.activity_help_toolbar);
        setSupportActionBar(imageViewActivityToolbar);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true); // Enables home (arrow back button)
        toolbar.setTitle(R.string.help_category);


//        addPreferencesFromResource(R.xml.help);

//        PackageInfo pInfo = null;
//        try {
//            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        // Get preferences from screen
//        version = (Preference) findPreference("version");
//        report = (Preference) findPreference("report");

        //Set version
//        version.setSummary(pInfo.versionName);

        // Set click event
//        report.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            public boolean onPreferenceClick(Preference preference) {
//
//
//                // get prompts.xml view
//                LayoutInflater li = LayoutInflater.from(HelpActivity.this);
//                View addTorrentView = li.inflate(R.layout.send_report, null);
//
//                // URL input
//                final EditText reportDescription = (EditText) addTorrentView.findViewById(R.id.report_description);
//
//                if (!isFinishing()) {
//                    // Dialog
//                    AlertDialog.Builder builder = new AlertDialog.Builder(HelpActivity.this);
//
//                    // Set add_torrent.xml to AlertDialog builder
//                    builder.setView(addTorrentView);
//
//                    // Cancel
//                    builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                            CustomLogger.setMainActivityReporting(false);
//
//                        }
//                    });
//
//                    // Ok
//                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User accepted the dialog
//
//                            CustomLogger.setReportDescription(reportDescription.getText().toString());
//                            CustomLogger.setMainActivityReporting(true);
//
//                            finish();
//
//                        }
//                    });
//
//                    // Create dialog
//                    AlertDialog dialog = builder.create();
//
//                    // Show dialog
//                    dialog.show();
//                }
//
//
//                return true;
//            }
//        });


//        // Set last state in intent result
//        Intent result = new Intent();
//        result.putExtra("currentState", MainActivity.currentState);
//        setResult(Activity.RESULT_OK, result);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Overrides default action for back button that reloaded MainView activity to blank screen
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////		getMenuInflater().inflate(R.menu.setting, menu);
//        return true;
//    }
}
