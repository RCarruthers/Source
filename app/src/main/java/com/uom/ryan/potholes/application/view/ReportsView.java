package com.uom.ryan.potholes.application.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

/**
 * Created by Ryan on 04/11/2017.
 */

public interface ReportsView {
    void setRecyclerViewAdapter(FirebaseRecyclerAdapter adapter);
    public LinearLayoutManager getLayoutManager();
    public RecyclerView getRecyclerView();
}
