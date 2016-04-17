package edu.uw.tcss450.monitorui.activities;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import edu.uw.tcss450.monitorui.fragments.HrDbFragment;
import edu.uw.tcss450.monitorui.R;
import edu.uw.tcss450.monitorui.fragments.Spo2DbFragment;
import edu.uw.tcss450.monitorui.network.NsdHelper;
import edu.uw.tcss450.monitorui.services.MonitorSvc;

public class DashboardActivity extends AppCompatActivity implements HrDbFragment.OnFragmentInteractionListener,
        Spo2DbFragment.OnFragmentInteractionListener {
    public static final String TAG = "MonitorUI";
    private NsdHelper mNsdHelper;
    private View hrdb_fragment;
    private View spo2db_fragment;
    private MonitorSvc monitorSvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sync data here...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        hrdb_fragment = findViewById(R.id.hrdb);
        hrdb_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HRActivity.class));
            }
        });

        spo2db_fragment = findViewById(R.id.spo2db);
        spo2db_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "SpO2 clicked!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        Button start_svc = (Button) findViewById(R.id.button_start);
        start_svc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MonitorSvc.class);
                intent.setAction(MonitorSvc.ACTION_START_SERVICE);
                startService(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSpo2Update(Uri uri) {

    }

    @Override
    public void onHRUpdate(Uri uri) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting.");
        mNsdHelper = new NsdHelper(this);
        mNsdHelper.initializeNsd();
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Resuming.");
        super.onResume();
        if (mNsdHelper != null) {
            mNsdHelper.discoverServices();
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Pausing.");
        if (mNsdHelper != null) {
            mNsdHelper.stopDiscovery();
        }
        super.onPause();
    }
}
