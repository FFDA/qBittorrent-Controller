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

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

public class ItemstFragment extends ListFragment {

    static public ActionMode mActionMode;
//    public int nr = 0;
    int secondContainer;
    TorrentDetailsFragment detailsFragment;
//    private RecyclerView recyclerView;
    private RefreshListener refreshListener;
    public static View.OnClickListener originalListener;

    public static SwipeRefreshLayout mSwipeRefreshLayout;

    public ItemstFragment() {
    }

    public void setSecondFragmentContainer(int container) {

        this.secondContainer = container;

    }

    public int getSecondFragmentContainer() {

        return this.secondContainer;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        // Tell the host activity that your fragment has menu options that it
        // wants to add/replace/delete using the onCreateOptionsMenu method.
        setHasOptionsMenu(true);

        // Get Refresh Listener
        refreshListener = (RefreshListener) getActivity();

        View rootView = inflater.inflate(R.layout.activity_main_original, container, false);


        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setColorSchemeColors(getContext().getColor(R.color.primary), getContext().getColor(R.color.primary_dark), getContext().getColor(R.color.primary_text));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListener.swipeRefresh();
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {

            getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

            // Get adapter
            final TorrentListAdapter mAdapter = (TorrentListAdapter) this.getListAdapter();
            getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                private int nr = 0;

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                    if (checked) {
                        nr++;
                        mAdapter.setNewSelection(position, checked);

                    } else {
                        nr--;
                        mAdapter.removeSelection(position);
                    }

                    // Set title with number of items selected
                    mode.setTitle("" + nr);

                }

                @Override
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    nr = 0;
                    MenuInflater inflater = getActivity().getMenuInflater();
                    inflater.inflate(R.menu.main_contextual_action_bar, menu);

                    mSwipeRefreshLayout.setEnabled(false);

                    ItemstFragment.mActionMode = actionMode;

                    return true;

                }

                @Override
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

                    menu.findItem(R.id.action_first_last_piece_prio).setVisible(true);
                    menu.findItem(R.id.action_sequential_download).setVisible(true);

                    // TODO: set category/category visible after implement it
                    menu.findItem(R.id.action_label_menu).setVisible(false);
                    menu.findItem(R.id.action_set_category).setVisible(false);
                    menu.findItem(R.id.action_delete_category).setVisible(false);

                    return true;
                }

                // This actions are click in the torrent list view (CAB)
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                    AlertDialog.Builder builder;
                    AlertDialog dialog;
                    String hashes = null;
                    final String hashesStr;

                    // Get MainActivity
                    final MainActivity m = (MainActivity) getActivity();

                    // Get hashes
                    for (int i = 0; mAdapter.getCount() > i; i++) {


                        if (mAdapter.isPositionChecked(i)) {

                            if (hashes == null) {
                                hashes = mAdapter.getData()[i].getHash();
                            } else {
                                hashes = hashes + "|" + mAdapter.getData()[i].getHash();
                            }
                        }
                    }

                    hashesStr = hashes;

                    ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

//                    switch (item.getItemId()) {
                    if (item.getItemId() == R.id.action_pause) {
                        m.pauseSelectedTorrents(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_resume) {

//                        case R.id.action_resume:

                        m.startSelectedTorrents(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_delete) {
//                        case R.id.action_delete:

                        if (!getActivity().isFinishing()) {
                            builder = new AlertDialog.Builder(getActivity());

                            // Message
                            builder.setMessage(R.string.dm_deleteSelectedTorrents).setTitle(R.string.dt_deleteSelectedTorrents);

                            // Cancel
                            builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User canceled the dialog
                                }
                            });

                            // Ok
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User accepted the dialog
                                    m.deleteSelectedTorrents(hashesStr);

                                }
                            });

                            // Create dialog
                            dialog = builder.create();

                            // Show dialog
                            dialog.show();

                            // Clear selection
                            nr = 0;

                            // Enable SwipeRefresh
                            mSwipeRefreshLayout.setEnabled(true);

                            mAdapter.clearSelection();
                            mode.finish();

                        }

                        return true;
                    } else if (item.getItemId() == R.id.action_delete_drive) {
//                        case R.id.action_delete_drive:

                        if (!getActivity().isFinishing()) {
                            builder = new AlertDialog.Builder(getActivity());

                            // Message
                            builder.setMessage(R.string.dm_deleteDriveSelectedTorrents).setTitle(R.string.dt_deleteDriveSelectedTorrents);

                            // Cancel
                            builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User canceled the dialog
                                }
                            });

                            // Ok
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User accepted the dialog
                                    m.deleteDriveSelectedTorrents(hashesStr);

                                }
                            });

                            // Create dialog
                            dialog = builder.create();

                            // Show dialog
                            dialog.show();

                            // Clear selection
                            nr = 0;

                            // Enable SwipeRefresh
                            mSwipeRefreshLayout.setEnabled(true);

                            mAdapter.clearSelection();
                            mode.finish();

                        }

                        return true;
                    } else if (item.getItemId() == R.id.action_force_start) {
//                        case R.id.action_force_start:

                        m.forceStartSelectedTorrents(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_increase_prio) {
//                        case R.id.action_increase_prio:
                        m.increasePrioTorrent(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_decrease_prio) {
//                        case R.id.action_decrease_prio:
                        m.decreasePrioTorrent(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_max_prio) {
//                        case R.id.action_max_prio:
                        m.maxPrioTorrent(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_min_prio) {
//                        case R.id.action_min_prio:
                        m.minPrioTorrent(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_upload_rate_limit) {
//                        case R.id.action_upload_rate_limit:

                        m.uploadRateLimitDialog(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_download_rate_limit) {
//                        case R.id.action_download_rate_limit:

                        m.downloadRateLimitDialog(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_recheck) {
//                        case R.id.action_recheck:

                        m.recheckTorrents(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_sequential_download) {
//                        case R.id.action_sequential_download:

                        m.toggleSequentialDownload(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_first_last_piece_prio) {
//                        case R.id.action_first_last_piece_prio:

                        m.toggleFirstLastPiecePrio(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_set_category) {
//                        case R.id.action_set_category:

                        m.setCategoryDialog(hashes);

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else if (item.getItemId() == R.id.action_delete_category) {
//                        case R.id.action_delete_category:

                        m.setCategory(hashes, " ");

                        // Clear selection
                        nr = 0;

                        // Enable SwipeRefresh
                        mSwipeRefreshLayout.setEnabled(true);

                        mAdapter.clearSelection();
                        mode.finish();

                        return true;
                    } else {
//                        default:
                            // Enable SwipeRefresh
                            mSwipeRefreshLayout.setEnabled(true);
                            return true;
                    }


                }

                @Override
                public void onDestroyActionMode(ActionMode actionMode) {
                    if (mAdapter != null) {
                        mAdapter.clearSelection();
                    }
                    ItemstFragment.mActionMode = null;

                    mSwipeRefreshLayout.setEnabled(true);

                }
            });

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    if(MainActivity.listViewRefreshing) {
                        return true;
                    }
                    getListView().setItemChecked(position, !mAdapter.isPositionChecked(position));
                    return false;
                }
            });

        } catch (Exception e) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView parent, @NonNull View v, int position, long id) {
        if(!MainActivity.listViewRefreshing) {
            ListItemClicked(position);
        }
    }

    public void ListItemClicked(int position) {

//        ListView lv = this.getListView();

//        int count = lv.getCount();

        Torrent torrent = MainActivity.lines[position];

        if (torrent.getHash().equals(TorrentDetailsFragment.hashToUpdate) && getActivity().findViewById(R.id.fragment_container) != null) {

            // Update torrent details

            FragmentManager fragmentManager = getParentFragmentManager();
            if(!(fragmentManager.findFragmentByTag("secondFragment") instanceof AboutFragment)) {

                detailsFragment = (TorrentDetailsFragment) fragmentManager.findFragmentByTag("secondFragment");

                if (detailsFragment != null && torrent != null) {

                    detailsFragment.updateDetails(torrent);
                }
            }
            else{
                newDetailsFragment(position);
            }
        } else {

            newDetailsFragment(position);

        }
    }

    private void newDetailsFragment(int position){

        detailsFragment = new TorrentDetailsFragment();

        // Get torrent from MainActivity
        detailsFragment.setTorrent(MainActivity.lines[position]);

        detailsFragment.setPosition(position);

        if (detailsFragment != null) {
            FragmentManager fragmentManager = getParentFragmentManager();

            if (getActivity().findViewById(R.id.one_frame) != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.one_frame, detailsFragment, "firstFragment")
                        .addToBackStack("secondFragment")
                        .commit();

                // Change toolbar home button behaviour
                originalListener = MainActivity.drawerToggle.getToolbarNavigationClickListener();

                MainActivity.drawerToggle.setDrawerIndicatorEnabled(false);
                MainActivity.drawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
                MainActivity.drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // Disable refreshing
                        MainActivity.disableRefreshSwipeLayout();

                        // Set default toolbar behaviour
                        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
                        MainActivity.drawerToggle.setDrawerIndicatorEnabled(true);
                        MainActivity.drawerToggle.setToolbarNavigationClickListener(originalListener);
                        ((MainActivity) getActivity()).setSelectionAndTitle(MainActivity.currentState);

                        // Show herderInfo in phone's view
                        if (getActivity().findViewById(R.id.one_frame) != null) {

                            if (MainActivity.headerInfo != null) {
                                if (MainActivity.header) {
                                    MainActivity.headerInfo.setVisibility(View.VISIBLE);
                                } else {
                                    MainActivity.headerInfo.setVisibility(View.GONE);
                                }
                            }

                        }

                        FragmentManager fm = getParentFragmentManager();

                        fm.popBackStack();


                    }
                });
            } else {
                fragmentManager.beginTransaction()
                        .replace(this.getSecondFragmentContainer(), detailsFragment, "secondFragment")
                        .addToBackStack("secondFragment")
                        .commit();
            }
        }

    }

    // @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // inflater.inflate(R.menu.main, menu);
        // super.onCreateOptionsMenu(menu, inflater);

        if (menu != null) {
            menu.findItem(R.id.action_refresh).setVisible(true);
            menu.findItem(R.id.action_search).setVisible(true);
            menu.findItem(R.id.action_resume_all).setVisible(true);
            menu.findItem(R.id.action_pause_all).setVisible(true);
            menu.findItem(R.id.action_add).setVisible(true);

            if (menu.findItem(R.id.action_resume) != null) {
                menu.findItem(R.id.action_resume).setVisible(false);
            }

            if (menu.findItem(R.id.action_pause) != null) {
                menu.findItem(R.id.action_pause).setVisible(false);
            }

            if (menu.findItem(R.id.action_increase_prio) != null) {
                menu.findItem(R.id.action_increase_prio).setVisible(false);
            }

            if (menu.findItem(R.id.action_decrease_prio) != null) {
                menu.findItem(R.id.action_decrease_prio).setVisible(false);
            }

            if (menu.findItem(R.id.action_max_prio) != null) {
                menu.findItem(R.id.action_max_prio).setVisible(false);
            }

            if (menu.findItem(R.id.action_min_prio) != null) {
                menu.findItem(R.id.action_min_prio).setVisible(false);
            }

            if (menu.findItem(R.id.action_delete) != null) {
                menu.findItem(R.id.action_delete).setVisible(false);
            }

            if (menu.findItem(R.id.action_delete_drive) != null) {
                menu.findItem(R.id.action_delete_drive).setVisible(false);
            }

            if (menu.findItem(R.id.action_force_start) != null) {
                menu.findItem(R.id.action_force_start).setVisible(false);
            }

            if (menu.findItem(R.id.action_upload_rate_limit) != null) {
                menu.findItem(R.id.action_upload_rate_limit).setVisible(false);
            }

            if (menu.findItem(R.id.action_download_rate_limit) != null) {
                menu.findItem(R.id.action_download_rate_limit).setVisible(false);
            }

            if (menu.findItem(R.id.action_recheck) != null) {
                menu.findItem(R.id.action_recheck).setVisible(false);
            }

            if (menu.findItem(R.id.action_first_last_piece_prio) != null) {
                menu.findItem(R.id.action_first_last_piece_prio).setVisible(false);
            }

            if (menu.findItem(R.id.action_sequential_download) != null) {
                menu.findItem(R.id.action_sequential_download).setVisible(false);
            }

            if (menu.findItem(R.id.action_priority_menu) != null) {
                menu.findItem(R.id.action_priority_menu).setVisible(false);
            }

            if (menu.findItem(R.id.action_add_tracker) != null) {
                menu.findItem(R.id.action_add_tracker).setVisible(false);
            }

            if (menu.findItem(R.id.action_add_tracker) != null) {
                menu.findItem(R.id.action_add_tracker).setVisible(false);
            }

            if (menu.findItem(R.id.action_set_category) != null) {
                menu.findItem(R.id.action_label_menu).setVisible(false);
                menu.findItem(R.id.action_set_category).setVisible(false);
                menu.findItem(R.id.action_delete_category).setVisible(false);

            }

            menu.findItem(R.id.action_toggle_alternative_rate).setVisible(true);

            // Set Alternate Speed limit state
            if (MainActivity.alternative_speeds) {
                menu.findItem(R.id.action_toggle_alternative_rate).setChecked(true);
            } else {
                menu.findItem(R.id.action_toggle_alternative_rate).setChecked(false);
            }

            menu.findItem(R.id.action_sortby_name).setVisible(true);
            menu.findItem(R.id.action_sortby_size).setVisible(true);
            menu.findItem(R.id.action_sortby_eta).setVisible(true);
            menu.findItem(R.id.action_sortby_priority).setVisible(true);
            menu.findItem(R.id.action_sortby_progress).setVisible(true);
            menu.findItem(R.id.action_sortby_ratio).setVisible(true);
            menu.findItem(R.id.action_sortby_speed).setVisible(true);
            menu.findItem(R.id.action_sortby_downloadSpeed).setVisible(true);
            menu.findItem(R.id.action_sortby_uploadSpeed).setVisible(true);
            menu.findItem(R.id.action_sortby_date).setVisible(true);
            menu.findItem(R.id.action_sortby_added_on).setVisible(true);
            menu.findItem(R.id.action_sortby_completed_on).setVisible(true);

            if (MainActivity.sortby_value == MainActivity.SORTBY_NAME) {
                menu.findItem(R.id.action_sortby_name).setIcon(R.drawable.ic_stat_completed);
            }

            if (MainActivity.sortby_value == MainActivity.SORTBY_SIZE) {
                menu.findItem(R.id.action_sortby_size).setIcon(R.drawable.ic_stat_completed);
            }


            if (MainActivity.sortby_value == MainActivity.SORTBY_ETA) {
                menu.findItem(R.id.action_sortby_eta).setIcon(R.drawable.ic_stat_completed);
            }

            if (MainActivity.sortby_value == MainActivity.SORTBY_PRIORITY) {
                menu.findItem(R.id.action_sortby_priority).setIcon(R.drawable.ic_stat_completed);
            }

            if (MainActivity.sortby_value == MainActivity.SORTBY_PROGRESS) {
                menu.findItem(R.id.action_sortby_progress).setIcon(R.drawable.ic_stat_completed);
            }

            if (MainActivity.sortby_value == MainActivity.SORTBY_RATIO) {
                menu.findItem(R.id.action_sortby_ratio).setIcon(R.drawable.ic_stat_completed);
            }

            if (MainActivity.sortby_value == MainActivity.SORTBY_DOWNLOAD) {
                menu.findItem(R.id.action_sortby_downloadSpeed).setIcon(R.drawable.ic_stat_completed);
                menu.findItem(R.id.action_sortby_speed).setIcon(R.drawable.ic_stat_completed);
            }

            if (MainActivity.sortby_value == MainActivity.SORTBY_UPLOAD) {
                menu.findItem(R.id.action_sortby_uploadSpeed).setIcon(R.drawable.ic_stat_completed);
                menu.findItem(R.id.action_sortby_speed).setIcon(R.drawable.ic_stat_completed);

            }

            if (MainActivity.sortby_value == MainActivity.SORTBY_ADDEDON) {
                menu.findItem(R.id.action_sortby_added_on).setIcon(R.drawable.ic_stat_completed);
                menu.findItem(R.id.action_sortby_date).setIcon(R.drawable.ic_stat_completed);

            }
            if (MainActivity.sortby_value == MainActivity.SORTBY_COMPLETEDON) {
                menu.findItem(R.id.action_sortby_completed_on).setIcon(R.drawable.ic_stat_completed);
                menu.findItem(R.id.action_sortby_date).setIcon(R.drawable.ic_stat_completed);
            }


            if (MainActivity.reverse_order) {
                menu.findItem(R.id.action_sortby_reverse_order).setIcon(R.drawable.ic_stat_completed);
            }
        }
    }

}
