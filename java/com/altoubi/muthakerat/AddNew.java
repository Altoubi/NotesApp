package com.altoubi.muthakerat;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNew extends AppCompatActivity {

    //  edit text
    EditText editText_Title, editText_Text;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        // assign buttin save
        button_save = (Button) findViewById(R.id.button_save);

        // make action
        // on button_save clicked ... validate data and insert it in database
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get edit text by id
                editText_Text = (EditText) findViewById(R.id.editText_Text);
                editText_Title = (EditText) findViewById(R.id.editText_Title);

                // check if its not empty
                // use .getText().length() to get the length of string
                if (editText_Text.getText().length() > 0 || editText_Title.getText().length() > 0) {
                    // if its not empty do inserting proccess

                    // call DB_Helper class to db
                    DB_Helper db = new DB_Helper(getBaseContext());
                    // pass data to the method (addNew()) in DB_Helper
                    db.addNew(editText_Title.getText().toString(), editText_Text.getText().toString());
                    // make Toast msg if when  added
                    Toast.makeText(getBaseContext(), R.string.added, Toast.LENGTH_LONG).show();
                    // move to main screen ( MainActivity.class)
                    startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {

                    // if its empty show msg ...
                    Toast.makeText(getBaseContext(), R.string.EmptyText, Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
