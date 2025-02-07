/*
 * Copyright (C) 2023 FFDA
 * This file is part of qBittorrent Controller+.
 *
 * qBittorrent Controller+ is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * qBittorrent Controller+ is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with qBittorrent Controller+. If not, see <https://www.gnu.org/licenses/>.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Original License
 * MIT License
 * Copyright (c) 2020 Luis M. Gallardo D.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lt.ffda.qbittorrentcontrollerplus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SettingsAdvancedFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private ListPreference currentServer;
    private EditTextPreference hostname;
    private EditTextPreference subfolder;
    private CheckBoxPreference https;
    private EditTextPreference port;
    private EditTextPreference username;
    private EditTextPreference password;
    private String currentServerValue;
    private CheckBoxPreference auto_refresh;
    private ListPreference refresh_period;
    private EditTextPreference connection_timeout;
    private EditTextPreference data_timeout;
    private CheckBoxPreference dark_ui;
    private CheckBoxPreference enable_notifications;
    private ListPreference notification_period;
    private EditTextPreference ssid;
    private EditTextPreference local_hostname;
    private EditTextPreference local_port;
    private Preference keystore_path;
    private EditTextPreference keystore_password;
    private CheckBoxPreference pathAndCategoryDialog;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences_advanced, rootKey);

        SharedPreferences sharedPrefs = getPreferenceManager().getSharedPreferences();
        this.currentServerValue = sharedPrefs.getString("currentServer", "1");

        //   Get preferences from screen
//        currentServer = (ListPreference) findPreference("currentServer");
        hostname = (EditTextPreference) findPreference("hostname");
        subfolder = (EditTextPreference) findPreference("subfolder");
        https = (CheckBoxPreference) findPreference("https");
        port = (EditTextPreference) findPreference("port");
        username = (EditTextPreference) findPreference("username");
        password = (EditTextPreference) findPreference("password");
        auto_refresh = (CheckBoxPreference) findPreference("auto_refresh");
        refresh_period = (ListPreference) findPreference("refresh_period");
        connection_timeout = (EditTextPreference) findPreference("connection_timeout");
        data_timeout = (EditTextPreference) findPreference("data_timeout");

        dark_ui = (CheckBoxPreference) findPreference("dark_ui");

        enable_notifications = (CheckBoxPreference) findPreference("enable_notifications");
        notification_period = (ListPreference) findPreference("notification_period");


        ssid = (EditTextPreference) findPreference("ssid");
        local_hostname = (EditTextPreference) findPreference("local_hostname");
        local_port = (EditTextPreference) findPreference("local_port");

        keystore_path = (Preference) findPreference("keystore_path");
        keystore_password = (EditTextPreference) findPreference("keystore_password");

        // Get values for server
        getQBittorrentServerValues(this.currentServerValue);

        EditTextPreference pref2 = findPreference("ssid");
        pref2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                return true;
            }
        });

        Preference pref3 = findPreference("local_hostname");
        pref3.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                return true;
            }
        });

        Preference pref4 = findPreference("local_port");
        pref4.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                return true;
            }
        });

        Preference keystore_path = (Preference) findPreference("keystore_path");
        keystore_path.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getKeystoreFile.launch(new String[]{"*/*"});
                return true;
            }
        });

        // savepath and category (category) pathAndCategoryDialogcheckbox
//        pathAndCategoryDialog = (CheckBoxPreference) findPreference("pathAndCategoryDialog");


        // Enable savepath and category preference
//        pathAndCategoryDialog.setEnabled(true);

//        // Checkbox value
//        if (MainActivity.pathAndCategoryDialog) {
//            pathAndCategoryDialog.setChecked(true);
//        } else {
//            pathAndCategoryDialog.setChecked(false);
//        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        //Update values on Screen
        this.refreshScreenValues();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        saveQBittorrentServerValues();
        super.onPause();
    }

    ActivityResultLauncher<String[]> getKeystoreFile = registerForActivityResult(new ActivityResultContracts.OpenDocument(), result -> {
        // Creating directory to store keystore files in internal storage if it does not exit
        if (!(new File(getContext().getFilesDir(), "keystore")).exists()) {
            (new File(getContext().getFilesDir(), "keystore")).mkdirs();
        }

        // Using DocumentFile just in case user selects it from CloudStorage
        DocumentFile keystoreFile = DocumentFile.fromSingleUri(getContext(), result);

        // Creating file in app-specific storage to store keystore file
        File keystorePathValue = new File(getContext().getFilesDir().getPath() + "/keystore/" + keystoreFile.getName());

        // Copying file to app-specific storage
        try {
            InputStream keystoreInputStream = getContext().getContentResolver().openInputStream(result);
            OutputStream keystoreOutputStream = new FileOutputStream(keystorePathValue, false);
            // Copying keystore file
            byte[] buf = new byte[4 * 1024];
            while (keystoreInputStream.read(buf) > 0) {
                keystoreOutputStream.write(buf, 0, buf.length);
            }
            keystoreInputStream.close();
            keystoreOutputStream.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Failed to save keystore file", Toast.LENGTH_SHORT).show();
        }

        // Saving keystore file path to preferences
        SharedPreferences.Editor editor = getPreferenceManager().getSharedPreferences().edit();;
        editor.putString("keystore_path" + this.currentServerValue, keystorePathValue.getPath());
        editor.commit();

        // Setting path to keystore file as settings summary
        keystore_path.setSummary(keystorePathValue.getPath());
    });

    public void refreshScreenValues() {
//        this.currentServer.setSummary(this.currentServer.getEntry());
//        this.hostname.setSummary(this.hostname.getText());
        this.subfolder.setSummary(this.subfolder.getText());
        this.port.setSummary(this.port.getText());
//        this.username.setSummary(this.username.getText());
//        this.refresh_period.setSummary(this.refresh_period.getEntry());
//        this.notification_period.setSummary(this.notification_period.getEntry());
        this.connection_timeout.setSummary(this.connection_timeout.getText());
        this.data_timeout.setSummary(this.data_timeout.getText());
        this.ssid.setSummary(this.ssid.getText());
        this.local_hostname.setSummary(this.local_hostname.getText());
        this.local_port.setSummary(this.local_port.getText());
    }

    public void getQBittorrentServerValues(String value) {
        SharedPreferences sharedPrefs = getPreferenceManager().getSharedPreferences();

//        this.currentServer.setSummary(currentServer.getEntry());
//        this.hostname.setText(sharedPrefs.getString("hostname" + value, ""));
//        this.hostname.setSummary(sharedPrefs.getString("hostname" + value, ""));
//
//        if (this.hostname.getText().toString().equals("")) {
//            this.hostname.setSummary(getString(R.string.settings_qbittorrent_hostname_hint));
//        }

        this.subfolder.setText(sharedPrefs.getString("subfolder" + value, ""));
        this.subfolder.setSummary(sharedPrefs.getString("subfolder" + value, ""));

        this.https.setChecked(sharedPrefs.getBoolean("https" + value, false));

        this.port.setText(sharedPrefs.getString("port" + value, "8080"));
        this.port.setSummary(sharedPrefs.getString("port" + value, "8080"));

//        this.username.setText(sharedPrefs.getString("username" + value, "admin"));
//        this.username.setSummary(sharedPrefs.getString("username" + value, "admin"));
//
//        this.password.setText(sharedPrefs.getString("password" + value, "adminadmin"));
//
//        if (this.refresh_period.getEntry() == null) {
//            this.refresh_period.setValueIndex(6);
//        }
//
//        this.refresh_period.setSummary(this.refresh_period.getEntry());

        this.connection_timeout.setText(sharedPrefs.getString("connection_timeout" + value, "10"));
        this.connection_timeout.setSummary(sharedPrefs.getString("connection_timeout" + value, "10"));

        this.data_timeout.setText(sharedPrefs.getString("data_timeout" + value, "20"));
        this.data_timeout.setSummary(sharedPrefs.getString("data_timeout" + value, "20"));


//        this.dark_ui.setChecked(sharedPrefs.getBoolean("dark_ui", false));
//
//        if (this.notification_period.getEntry() == null) {
//            this.notification_period.setValueIndex(1);
//        }
//
//        this.notification_period.setSummary(this.notification_period.getEntry());


        this.ssid.setText(sharedPrefs.getString("ssid" + value, ""));
        this.ssid.setSummary(sharedPrefs.getString("ssid" + value, ""));

        this.local_hostname.setText(sharedPrefs.getString("local_hostname" + value, ""));
        this.local_hostname.setSummary(sharedPrefs.getString("local_hostname" + value, ""));

        this.local_port.setText(sharedPrefs.getString("local_port" + value, ""));
        this.local_port.setSummary(sharedPrefs.getString("local_port" + value, ""));

        this.keystore_path.setSummary(sharedPrefs.getString("keystore_path" + value, ""));
        this.keystore_password.setText(sharedPrefs.getString("keystore_password" + value, ""));
    }

    public void saveQBittorrentServerValues() {
//        if (keystore_path == null) {
//            Log.i("prefServerName", "keystore_path buvo nulis");
//        } else {
//            Log.i("prefServerName", "keystore_path nebuvo nulis");
//        }
//        this.currentServerValue = currentServer.getValue();

        // Save options locally
        SharedPreferences sharedPrefs = getPreferenceManager().getSharedPreferences();

        SharedPreferences.Editor editor = sharedPrefs.edit();


//        if (hostname.getText().toString() != null && hostname.getText().toString() != "") {
//            editor.putString("hostname" + this.currentServerValue, this.hostname.getText().toString());
//        }

        if (subfolder.getText().toString() != null) {
            editor.putString("subfolder" + this.currentServerValue, this.subfolder.getText().toString());
        }

        editor.putBoolean("https" + currentServerValue, https.isChecked());

        if (port.getText().toString() != null && port.getText().toString() != "") {

            editor.putString("port" + currentServerValue, port.getText().toString());
        }

//        if (username.getText().toString() != null && username.getText().toString() != "") {
//
//            editor.putString("username" + currentServerValue, username.getText().toString());
//        }
//
//        if (password.getText().toString() != null && password.getText().toString() != "") {
//
//            editor.putString("password" + currentServerValue, password.getText().toString());
//        }

        if (connection_timeout.getText().toString() != null && connection_timeout.getText().toString() != "") {
            editor.putString("connection_timeout", connection_timeout.getText().toString());
        }

        if (data_timeout.getText().toString() != null && data_timeout.getText().toString() != "") {
            editor.putString("data_timeout", data_timeout.getText().toString());
        }

//        editor.putBoolean("dark_ui" + currentServerValue, dark_ui.isChecked());

        if (ssid.getText().toString() != null && ssid.getText().toString() != "") {
            editor.putString("ssid" + currentServerValue, ssid.getText().toString());
        }

        if (local_hostname.getText().toString() != null && local_hostname.getText().toString() != "") {
            editor.putString("local_hostname" + currentServerValue, local_hostname.getText().toString());
        }

        if (local_port.getText().toString() != null && local_port.getText().toString() != "") {
            editor.putString("local_port" + currentServerValue, local_port.getText().toString());
        }

        if (keystore_path.getSummary().toString() != null) {
            editor.putString("keystore_path" + currentServerValue, keystore_path.getSummary().toString());
        }

        if (keystore_password.getText().toString() != null) {
            editor.putString("keystore_password" + currentServerValue, keystore_password.getText().toString());
        }

        // Commit changes
        editor.apply();


        // Refresh drawer servers
        String[] navigationDrawerServerItems;
        navigationDrawerServerItems = getResources().getStringArray(R.array.qBittorrentServers);

        ArrayList<DrawerItem> serverItems = new ArrayList<>();

        // Server items
        int currentServerIntValue = 1;

        try {
            currentServerIntValue = Integer.parseInt(currentServerValue);
        } catch (NumberFormatException e) {

        }

        serverItems.add(new DrawerItem(R.drawable.ic_drawer_servers, getResources().getString(R.string.drawer_servers_category), MainActivity.DRAWER_SERVERS, false, null));

        for (int i = 0; i < navigationDrawerServerItems.length; i++) {
            serverItems.add(new DrawerItem(R.drawable.ic_drawer_subitem, navigationDrawerServerItems[i], MainActivity.DRAWER_ITEM_SERVER, ((i + 1) == currentServerIntValue), "changeCurrentServer"));
        }

        try {
            MainActivity.rAdapter.refreshDrawerServers(serverItems);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Couldn't refresh servers", Toast.LENGTH_SHORT).show();
        }
    }
}
