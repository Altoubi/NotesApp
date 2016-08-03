package com.altoubi.muthakerat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update extends AppCompatActivity {
    // define widget
    EditText editText_Title, editText_Text;
    // define strings
    String eTitle, eText, eTitle2, eText2;
    // define ID as int
    int eid;

    Button button_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Call DB_Helper class as db_helper
        DB_Helper db_helper = new DB_Helper(getBaseContext());

        // get data which passed as intent extra from MainActivity.class
        eid = getIntent().getExtras().getInt(db_helper.ITEM_ID);
        eTitle = getIntent().getExtras().getString(db_helper.ITEM_TITLE);
        eText = getIntent().getExtras().getString(db_helper.ITEM_TEXT);

// assign widget by id
        editText_Text = (EditText) findViewById(R.id.editText_Text);
        editText_Title = (EditText) findViewById(R.id.editText_Title);
// set text which passed as intent extra from MainActivity.class to TextView
        editText_Title.setText(eTitle);
        editText_Text.setText(eText);

        // assign button
        button_save = (Button) findViewById(R.id.button_save);
        // Make action when button clecked
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Call DB_Helper class as db_helper
                DB_Helper db_helper = new DB_Helper(getBaseContext());
                // assign
                editText_Text = (EditText) findViewById(R.id.editText_Text);
                editText_Title = (EditText) findViewById(R.id.editText_Title);
                // get string value from EditText
                eTitle2 = editText_Title.getText().toString();
                eText2 = editText_Text.getText().toString();
                // update data using the method updateItem in DB_Helper.class
                // passing (note ID , Note Title, Note Text )
                db_helper.updateItem(eid, eTitle2, eText2);
                // show msg after insert
                Toast.makeText(getBaseContext(), R.string.updated, Toast.LENGTH_LONG).show();
                // move to main activity
                startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }
}
