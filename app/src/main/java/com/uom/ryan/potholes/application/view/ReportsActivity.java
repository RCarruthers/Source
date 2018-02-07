package com.uom.ryan.potholes.application.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.uom.ryan.potholes.R;
import com.uom.ryan.potholes.application.presenter.ReportsPresenter;
import com.uom.ryan.potholes.application.presenter.ReportsPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReportsActivity extends AppCompatActivity implements ReportsView {

    @BindView(R.id.id_report_recycler_view)
    RecyclerView reportRecyclerView;
    LinearLayoutManager layoutManager;
    ReportsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        presenter = new ReportsPresenterImpl(this, this);
        initRecyclerView();
        presenter.populateAdapter();

    }

    protected void initRecyclerView() {
        reportRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        reportRecyclerView.setLayoutManager(layoutManager);
    }


    public void setRecyclerViewAdapter(FirebaseRecyclerAdapter adapter){
        reportRecyclerView.setAdapter(adapter);
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return reportRecyclerView;
    }



}
