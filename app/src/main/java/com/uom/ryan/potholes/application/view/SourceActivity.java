package com.uom.ryan.potholes.application.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.uom.ryan.potholes.R;
import com.uom.ryan.potholes.application.flic.FlicButtonManager;
import com.uom.ryan.potholes.application.model.User;
import com.uom.ryan.potholes.application.presenter.SourcePresenter;
import com.uom.ryan.potholes.application.presenter.SourcePresenterImpl;
import com.uom.ryan.potholes.login.view.LoginActivity;

import static com.uom.ryan.potholes.util.Constants.TOAST_SIGNED_OUT;


public class SourceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, SourceView {

    private GoogleMap mMap;
    FirebaseAuth firebaseAuth;
    FlicButtonManager flicButtonManager;
    SourcePresenter presenter;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        currentUser = new User();
        setContentView(R.layout.activity_source);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        presenter = new SourcePresenterImpl(this);
        initialiseMap();
        flicButtonManager = new FlicButtonManager(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.source, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
       //  if (id == R.id.action_settings) {
       //      return true;
       //   }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(item.getItemId()) {
            case R.id.nav_profile:
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_reports:
                intent = new Intent(getBaseContext(), ReportsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                firebaseAuth.signOut();
                Toast.makeText(this, TOAST_SIGNED_OUT, Toast.LENGTH_SHORT).show();
                navigateToLoginActivity();
                break;
            case R.id.nav_flic_button:
                flicButtonManager.grabButton();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
       flicButtonManager.onActivityResult(requestCode, resultCode, data);
    }

    public void initialiseMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void navigateToLoginActivity() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        presenter.onMapReady(googleMap);
    }

    private void makeToast(String toastString) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
    }


}
