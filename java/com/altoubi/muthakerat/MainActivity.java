package com.altoubi.muthakerat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ListView lv;

    private DB_Helper dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DB_Helper(getBaseContext());

        displayListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNew.class));
            }
        });
    }

    private void displayListView() {

        // get all data from sqlite database ...
        Cursor cursor = dbHelper.getAll();
        // The  columns
        String[] columns = new String[]{
                dbHelper.ITEM_TEXT,
                dbHelper.ITEM_ID,
                dbHelper.ITEM_ADDED,
                dbHelper.ITEM_TITLE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                0,
                0,
                R.id.added,
                R.id.title
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        //context , XML file in layout (res/drawable/item_row.xml) , cursor data , columns , to xml view , null
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.item_row,
                cursor,
                columns,
                to,
                0);
        // change value off date from miliseconds to "yyyy-MM-dd HH:mm" format
        dataAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                if (i == 3) {
                    long createDate = cursor.getLong(i);
                    TextView textView = (TextView) view;
                    textView.setText(getDate(createDate,"yyyy-MM-dd HH:mm"));
                    return true;
                }
                return false;
            }
        });
        // defind list view
        ListView listView = (ListView) findViewById(R.id.listView);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        // on item clicked longer delete the entry ...
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> listView, View view, int i, long l) {
                // get postion
                Cursor cursor = (Cursor) listView.getItemAtPosition(i);
                // get ID of note from the postion ..
                int iID = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.ITEM_ID));
                // delete using the method deleteItem() in DB_Helper
                dbHelper.deleteItem(iID);
                // reload this activity to refresh data in list view
                startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                return true;
            }
        });

        // on item clicked get data from row and pass it to Update Acticity ...
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the ID, Title and Text from this row in the database.
                String iText = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.ITEM_TEXT));
                String iTitle = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.ITEM_TITLE));
                int iID = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.ITEM_ID));

                // pass data to next activity (Update.class) ...
                Intent intent = new Intent(getApplicationContext(), Update.class);
                intent.putExtra(dbHelper.ITEM_ID, iID);
                intent.putExtra(dbHelper.ITEM_TITLE, iTitle);
                intent.putExtra(dbHelper.ITEM_TEXT, iText);
                startActivity(intent);
            }
        });
    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
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
