package com.paularagones.moode.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;

import com.paularagones.moode.Adapters.RowSpinnerStyleAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import java.util.List;

public class AddNewStatusActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    private static final String LOG_TAG = AddNewStatusActivity.class.getSimpleName();
    private Spinner spinnerFeelings;
    private Spinner spinnerActivity;
    private Spinner spinnerLocation;
    private Spinner spinnerPerson;
    private Button submitButton;
    private Status status;
    private EditText etReason;

    List<SpinnerResult> feelingsList;
    List<SpinnerResult> personList;
    List<SpinnerResult> activityList;
    List<SpinnerResult> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_status);

        status = new Status();

        ///ToDO create async task, to change once the new spinner design has changed as well
        DBAdapter dbAdapter = new DBAdapter(this);
        feelingsList =  dbAdapter.getFeelingsList();
        personList =  dbAdapter.getPersonList();
        activityList =  dbAdapter.getActivityList();
        locationList =  dbAdapter.getLocationList();

        RowSpinnerStyleAdapter feelingsAdapter = new RowSpinnerStyleAdapter(this, feelingsList);
        RowSpinnerStyleAdapter personAdapter = new RowSpinnerStyleAdapter(this, personList);
        RowSpinnerStyleAdapter activityAdapter = new RowSpinnerStyleAdapter(this, activityList);
        RowSpinnerStyleAdapter locationAdaper = new RowSpinnerStyleAdapter(this, locationList);

        spinnerFeelings = (Spinner) findViewById(R.id.spinner_feelings);
        spinnerActivity = (Spinner) findViewById(R.id.spinner_activity);
        spinnerLocation = (Spinner) findViewById(R.id.spinner_location);
        spinnerPerson = (Spinner) findViewById(R.id.spinner_person);
        etReason = (EditText) findViewById(R.id.et_reason);
        submitButton = (Button) findViewById(R.id.submit_button);

        spinnerFeelings.setAdapter(feelingsAdapter);
        spinnerActivity.setAdapter(activityAdapter);
        spinnerLocation.setAdapter(locationAdaper);
        spinnerPerson.setAdapter(personAdapter);

        spinnerFeelings.setOnItemSelectedListener(this);
        spinnerActivity.setOnItemSelectedListener(this);
        spinnerLocation.setOnItemSelectedListener(this);
        spinnerPerson.setOnItemSelectedListener(this);
        submitButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                DBAdapter dbAdapter = new DBAdapter(this);
                status.setNotes(etReason.getText().toString());
                dbAdapter.addNewStatus(status);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_status_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ActivityOptionsService activityOptionsService = new ActivityOptionsService();
        activityOptionsService.openActivity(this ,id);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        Log.e(LOG_TAG, position);

        switch (parent.getId()) {
            case R.id.spinner_feelings :
                status.setFeelingsID(feelingsList.get(position).getID());
                break;
            case R.id.spinner_activity :
                status.setActivityID(activityList.get(position).getID());
                break;
            case R.id.spinner_location :
                status.setLocationID(locationList.get(position).getID());
                break;
            case R.id.spinner_person :
                status.setPersonID(personList.get(position).getID());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
