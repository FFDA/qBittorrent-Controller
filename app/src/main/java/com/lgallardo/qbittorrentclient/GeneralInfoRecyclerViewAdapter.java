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
package com.lgallardo.qbittorrentclient;

/*
  Created by lgallard on 28/08/15.
 */

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GeneralInfoRecyclerViewAdapter extends RecyclerView.Adapter<GeneralInfoRecyclerViewAdapter.ViewHolder> {

    // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_GENERALINFO_ITEM = 2;


    // All items
    public static ArrayList<GeneralInfoItem> items;

    private static MainActivity mainActivity;
    private Context context;


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewLabel;
        TextView textViewValue;


        public ViewHolder(final View itemView, int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            textViewLabel = (TextView) itemView.findViewById(R.id.label);
            textViewValue = (TextView) itemView.findViewById(R.id.value);

        }


        // In order to track the item position in RecyclerView
        // Handle item click and set the selection
        @Override
        public void onClick(View view) {


            GeneralInfoItem recyclerItem;


            // Get item
            recyclerItem = items.get(getLayoutPosition());


            // Perform Action
//            Log.d("Debug", "onClicked invoked!");


            // Add tracker
            if (recyclerItem.getAction().equals("generalInfo")) {

//                Log.d("Debug", "generalInfo");
//                mainActivity.openContextMenu(itemView);

                //notifyDataSetChanged();
            }


        }

    }


    GeneralInfoRecyclerViewAdapter(MainActivity mainActivity, Context context, ArrayList<GeneralInfoItem> generalInfoItems) {

        this.mainActivity = mainActivity;
        this.context = context;


        // All items
        GeneralInfoRecyclerViewAdapter.items = new ArrayList<GeneralInfoItem>();

        // Add items
        GeneralInfoRecyclerViewAdapter.items.addAll(generalInfoItems);


//        Log.d("Debug", "GeneralInfoRecyclerViewAdapter instantiated");
    }


    //Below first we override the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    @NonNull
    @Override
    public GeneralInfoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.d("Debug", "onCreateViewHolder invoked");

        //inflate your layout and pass it to view holder
        if (viewType == TYPE_GENERALINFO_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.generalinfo_row, parent, false); //Inflating the layout
            ViewHolder vhItem = new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view
            return vhItem; // Returning the created object

        }

        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(@NonNull GeneralInfoRecyclerViewAdapter.ViewHolder holder, int position) {

        GeneralInfoItem item = items.get(position);

//        Log.d("Debug", "onBindViewHolder - item.category: " + item.getLabel());
//        Log.d("Debug", "onBindViewHolder - item.value: " + item.getValue());


        if (item.getType() == GeneralInfoItem.GENERALINFO) {

            holder.textViewLabel.setText(item.getLabel());
            holder.textViewValue.setText(item.getValue());
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

//        if (items.get(position).getType() == TYPE_TRACKER_ITEM) {
//            Log.d("Debug", "ContentFilesRecyclerViewAdapter - TYPE_TRACKER_ITEM");
//            return TYPE_TRACKER_ITEM;
//        }

        // Default
//        Log.d("Debug", "GeneralInfoRecyclerViewAdapter - TYPE_TRACKER_ITEM");
        return TYPE_GENERALINFO_ITEM;

    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public void refreshGeneralInfo(ArrayList<GeneralInfoItem> generalInfoItems) {


        GeneralInfoRecyclerViewAdapter.items = new ArrayList<GeneralInfoItem>();

        // Add items
//        TrackersRecyclerViewAdapter.items.addAll(TrackersRecyclerViewAdapter.fileItems);
        GeneralInfoRecyclerViewAdapter.items.addAll(generalInfoItems);

        // Refresh
        notifyDataSetChanged();

    }


}
