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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import android.os.Bundle;
import androidx.preference.Preference;
import android.widget.Toast;

public class HelpFragment extends PreferenceFragmentCompat {

    @Override
	public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
		setPreferencesFromResource(R.xml.help, rootKey);

        Preference version = (Preference) findPreference("version");
//        Preference report = (Preference) findPreference("report");

		version.setSummary(BuildConfig.VERSION_NAME);

//        report.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(@NonNull Preference preference) {
//                Toast.makeText(getContext(), "Temporary Disabled", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        //* Disabled bug reporting *//
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

//	@NonNull
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//		// Tell the host activity that your fragment has menu options that it
//		// wants to add/replace/delete using the onCreateOptionsMenu method.
//		setHasOptionsMenu(true);
//
//		return inflater.inflate(R.layout.tablet_help, container, false);
//	}

	// @Override
//	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//		// inflater.inflate(R.menu.main, menu);
//		// super.onCreateOptionsMenu(menu, inflater);
//
//		if (menu != null) {
//			menu.findItem(R.id.action_refresh).setVisible(true);
//			menu.findItem(R.id.action_search).setVisible(true);
//			menu.findItem(R.id.action_add).setVisible(true);
//
//			if (menu.findItem(R.id.action_resume_all) != null) {
//				menu.findItem(R.id.action_resume_all).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_pause_all) != null) {
//				menu.findItem(R.id.action_pause_all).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_resume) != null) {
//				menu.findItem(R.id.action_resume).setVisible(false);
//			}
//			if (menu.findItem(R.id.action_pause) != null) {
//				menu.findItem(R.id.action_pause).setVisible(false);
//			}
//			if (menu.findItem(R.id.action_increase_prio) != null) {
//				menu.findItem(R.id.action_increase_prio).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_decrease_prio) != null) {
//				menu.findItem(R.id.action_decrease_prio).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_delete) != null) {
//				menu.findItem(R.id.action_delete).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_delete_drive) != null) {
//				menu.findItem(R.id.action_delete_drive).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_force_start) != null) {
//				menu.findItem(R.id.action_force_start).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_upload_rate_limit) != null) {
//				menu.findItem(R.id.action_upload_rate_limit).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_download_rate_limit) != null) {
//				menu.findItem(R.id.action_download_rate_limit).setVisible(false);
//			}
//
//            if (menu.findItem(R.id.action_recheck) != null) {
//                menu.findItem(R.id.action_recheck).setVisible(false);
//            }
//
//			if (menu.findItem(R.id.action_first_last_piece_prio) != null) {
//				menu.findItem(R.id.action_first_last_piece_prio).setVisible(false);
//			}
//
//            if (menu.findItem(R.id.action_sequential_download) != null) {
//                menu.findItem(R.id.action_sequential_download).setVisible(false);
//            }
//
//            if (menu.findItem(R.id.action_priority_menu) != null) {
//                menu.findItem(R.id.action_priority_menu).setVisible(false);
//            }
//
//			if (menu.findItem(R.id.action_add_tracker) != null) {
//				menu.findItem(R.id.action_add_tracker).setVisible(false);
//			}
//
//			if (menu.findItem(R.id.action_set_category) != null) {
//				menu.findItem(R.id.action_label_menu).setVisible(false);
//				menu.findItem(R.id.action_set_category).setVisible(false);
//				menu.findItem(R.id.action_delete_category).setVisible(false);
//
//			}
//
//
//        }
//	}

}
