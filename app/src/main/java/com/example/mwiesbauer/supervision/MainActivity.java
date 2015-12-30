package com.example.mwiesbauer.supervision;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivitiesDataSource datasource;
    private boolean isOn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            datasource = new ActivitiesDataSource(this);
            datasource.open();
            List<Activity> values = datasource.getAllActivities();

            ArrayAdapter<Activity> adapter = new ArrayAdapter<Activity>(this, android.R.layout.simple_list_item_1, values);

            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(adapter);



        } catch (SQLException e) {
            System.out.println(e);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void startActivity(View view) {
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<Activity> adapter = (ArrayAdapter<Activity>) lv.getAdapter();
        Activity activity = null;

        Chronometer chrono = (Chronometer) findViewById(R.id.chronometer);


        if (view.getId() == R.id.bExcavation) {
            Button bExcavation = (Button) findViewById(R.id.bExcavation);

            if (isOn) {
                chrono.stop();
                bExcavation.setText("Exavation stopped");
                bExcavation.setBackgroundColor(Color.LTGRAY);
                isOn = false;
            } else {
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                bExcavation.setText("Excavation ongoing");
                bExcavation.setBackgroundColor(Color.RED);
                isOn = true;
            } // end else
        } // end if


        if (view.getId() == R.id.bAdd) {
            String[] activities = new String[] {"Excavation", "Shotcrete", "Mucking"};
            int nextInt = new Random().nextInt(3);
            activity = datasource.createActivity(activities[nextInt]);
            adapter.add(activity);
        } // end add

        if (view.getId() == R.id.bDelete) {
            ListView lv2 = (ListView) findViewById(R.id.listView);
            if (lv2.getAdapter().getCount() > 0) {
                System.out.println("selected: " + lv2.getSelectedItemPosition());

                activity = (Activity) lv2.getAdapter().getItem(0);
                datasource.deleteActivity(activity);
                adapter.remove(activity);
            }

        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
