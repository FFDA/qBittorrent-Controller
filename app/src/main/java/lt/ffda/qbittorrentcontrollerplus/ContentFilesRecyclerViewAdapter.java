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

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ListIterator;

public class ContentFilesRecyclerViewAdapter extends RecyclerView.Adapter<ContentFilesRecyclerViewAdapter.ViewHolder> {

    // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_FILE_ITEM = 0;


    // All items
    public static ArrayList<TorrentDetailsItem> items;

    // Subitems
    public static ArrayList<TorrentDetailsItem> fileItems;

    private static MainActivity mainActivity;
    private Context context;


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewFile;
        TextView textViewInfo;
        TextView textViewPriorityInfo;
        TextView textViewPercentage;
        ProgressBar progressBar;


        public ViewHolder(final View itemView, int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            textViewFile = (TextView) itemView.findViewById(R.id.name);
            textViewInfo = (TextView) itemView.findViewById(R.id.info);
            textViewPriorityInfo = (TextView) itemView.findViewById(R.id.priorityInfo);
            textViewPercentage = (TextView) itemView.findViewById(R.id.percentage);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);

        }


        // In order to track the item position in RecyclerView
        // Handle item click and set the selection
        @Override
        public void onClick(View view) {

            TorrentDetailsItem recyclerItem;

            // Get item
            recyclerItem = items.get(getLayoutPosition());

            // Set file priority
            if (recyclerItem.getAction().equals("setFilePriority")) {
                TorrentDetailsFragment.fileContentRowPosition = getAbsoluteAdapterPosition();
                mainActivity.openContextMenu(itemView);
            }

        }

    }


    ContentFilesRecyclerViewAdapter(MainActivity mainActivity, Context context, ArrayList<TorrentDetailsItem> fileItems) {

        this.mainActivity = mainActivity;
        this.context = context;


        // All items

        ContentFilesRecyclerViewAdapter.fileItems = fileItems;


        ContentFilesRecyclerViewAdapter.items = new ArrayList<TorrentDetailsItem>();

        // Add items
        ContentFilesRecyclerViewAdapter.items.addAll(fileItems);


//        Log.d("Debug", "ContentFilesRecyclerViewAdapter instantiated");
    }


    //Below first we override the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    @NonNull
    @Override
    public ContentFilesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.d("Debug", "onCreateViewHolder invoked");

        //inflate your layout and pass it to view holder
        if (viewType == TYPE_FILE_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contentfile_row, parent, false); //Inflating the layout
            ViewHolder vhItem = new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object
        }

        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(ContentFilesRecyclerViewAdapter.ViewHolder holder, int position) {

        TorrentDetailsItem item = items.get(position);

//        Log.d("Debug", "onBindViewHolder - item.info: " + item.getInfo());


        if (item.getType() == TorrentDetailsItem.FILE) {

            holder.textViewFile.setText(item.getName());
            holder.textViewInfo.setText(item.getSize());
            holder.textViewPriorityInfo.setText(getPriorityString(item.getPriority()));


            // Set progress bar

            int index = item.getProgressAsString().indexOf(".");

            if (index == -1) {
                index = item.getProgressAsString().indexOf(",");

                if (index == -1) {
                    index = item.getProgressAsString().length();
                }
            }

            String percentage = item.getProgressAsString().substring(0, index);

            holder.progressBar.setProgress(Integer.parseInt(percentage));

            holder.textViewPercentage.setText(percentage + "%");
        }

        if (item.getType() == TorrentDetailsItem.TRACKER) {

            holder.textViewInfo.setText(item.getInfo());
        }


        // Uncomment for long click menu
//        holder.itemView.setLongClickable(true);


    }


    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        // Return the number of items in the list (header + item actions)

//        Log.d("Debug", "getItemCount: " + items.size());

        return items.size();
    }


    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {

//        Log.d("Debug", "DrawerItemRecyclerViewAdapter - items.size(): " + items.size());
//        Log.d("Debug", "DrawerItemRecyclerViewAdapter - position: " + position);

        // Default
//        Log.d("Debug", "ContentFilesRecyclerViewAdapter - TYPE_FILE_ITEM");
        return TYPE_FILE_ITEM;

    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void refreshContentFiles(ArrayList<TorrentDetailsItem> contentFiles) {

        ContentFilesRecyclerViewAdapter.fileItems = contentFiles;

        ContentFilesRecyclerViewAdapter.items = new ArrayList<TorrentDetailsItem>();

        // Add items
        ContentFilesRecyclerViewAdapter.items.addAll(ContentFilesRecyclerViewAdapter.fileItems);

//        Log.d("Debug", "refreshContentFiles - contentFiles.size: " + contentFiles.size());
//        Log.d("Debug", "refreshContentFiles - items.size: " + items.size());

        ListIterator iterator = items.listIterator();

        while (iterator.hasNext()) {

            TorrentDetailsItem item = (TorrentDetailsItem) iterator.next();

//            Log.d("Debug", "refreshContentFiles - TYPE: " + item.getType());

        }


        // Refresh
        notifyDataSetChanged();

    }

    public String getPriorityString(int priority) {

        String priorityString;


        switch (priority) {

            case 0:
                priorityString = context.getResources().getString(R.string.action_file_dont_download);
                break;
            case 1:
                priorityString = context.getResources().getString(R.string.action_file_normal_priority);
                break;
            case 6:
                priorityString = context.getResources().getString(R.string.action_file_high_priority);
                break;
            case 7:
                priorityString = context.getResources().getString(R.string.action_file_maximum_priority);
                break;
            default:
                priorityString = "";
                break;

        }

        return priorityString;
    }

}
