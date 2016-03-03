package com.paularagones.moode.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

public abstract class AddNewAbstract extends AppCompatActivity implements View.OnClickListener {


    protected String columnName;
    protected String tableName;
    protected EditText etNewRecord;
    protected Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_something);


        etNewRecord = (EditText) findViewById(R.id.et_new_record);
        submitButton = (Button) findViewById(R.id.button_add_new);
        submitButton.setOnClickListener(this);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setButtonName(String tableName) { submitButton.setText(submitButton.getText() + " " + tableName);}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_new:
                DBAdapter dbAdapter = new DBAdapter(this);

                dbAdapter.addNewRecord(getTableName(), getColumnName(), etNewRecord.getText().toString());
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
}
