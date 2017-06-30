package com.teamzero.resolutionchanger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import eu.chainfire.libsuperuser.Shell;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        button = (Button) findViewById(R.id.hd_res);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                (new StartUp()).setContext(v.getContext()).execute("HD");
                TextView res = (TextView) findViewById(R.id.resolution);
                res.setVisibility(View.VISIBLE);
                res.setText(R.string.hd_res);
            }
        });
        button = (Button) findViewById(R.id.fhd_res);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                (new StartUp()).setContext(v.getContext()).execute("FULLHD");
                TextView res = (TextView) findViewById(R.id.resolution);
                res.setVisibility(View.VISIBLE);
                res.setText(R.string.fhd_res);
            }
        });
        button = (Button) findViewById(R.id.reset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new StartUp()).setContext(v.getContext()).execute("reset");
                TextView res = (TextView) findViewById(R.id.resolution);
                res.setVisibility(View.INVISIBLE);
            }
        });
        button = (Button) findViewById(R.id.battery_plus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new StartUp()).setContext(v.getContext()).execute("batteryplus");
                TextView res = (TextView) findViewById(R.id.resolution);
                res.setVisibility(View.VISIBLE);
                res.setText(R.string.battery_res);
            }
        });
    }
    private class StartUp extends AsyncTask<String, Void, Void> {
        private Context context = null;
        boolean suAvailable = false;
        public StartUp setContext(Context context) {
            this.context = context;
            return this;
        }
        @Override
        protected Void doInBackground(String... params) {
            suAvailable = Shell.SU.available();
            if (suAvailable) {
                switch (params[0]) {
                    case "HD":
                        Shell.SU.run("wm size 720x1280");
                        break;
                    case "FULLHD":
                        Shell.SU.run("wm size 1080x1920");
                        break;
                    case "batteryplus":
                        Shell.SU.run("wm size 540x960");
                        break;
                    case "reset":
                        Shell.SU.run("wm size reset");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Root Acess not found, please check SU Binary", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        private long[] params;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_main) {
        } else if (id == R.id.nav_density) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
