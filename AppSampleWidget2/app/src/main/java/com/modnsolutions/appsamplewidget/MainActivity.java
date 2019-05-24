package com.modnsolutions.appsamplewidget;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private SharedPreferences mPrefs;

    public static final String mSharedPrefFile = "com.modnsolutions.appsamplewidget.2";
    public static final String KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getSharedPreferences(mSharedPrefFile, 0);
        String value = mPrefs.getString(KEY, "");

        mEditText = findViewById(R.id.edittext);
        mEditText.setText(value);
    }

    public void save(View view) {
        String value = mEditText.getText().toString();
        if (!value.equals("")) {
            SharedPreferences.Editor prefEditor = mPrefs.edit();
            prefEditor.putString(KEY, value);
            prefEditor.apply();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Enter a string.", Toast.LENGTH_SHORT).show();
    }
}
