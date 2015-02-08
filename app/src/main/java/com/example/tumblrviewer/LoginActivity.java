package com.example.tumblrviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.username_et);
        mPassword = (EditText) findViewById(R.id.password_et);

    }

    public void sendMessage(View view) {

        Intent intent = new Intent(this, WeHaveTheMunchiesActivity.class);


        if (verifyCredentials(mUsername.getText().toString(), mPassword.getText().toString())) {
            startActivity(intent);
        } else {

            Toast toast = Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean verifyCredentials(String email, String password) {

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            return true;
        } else {
            return false;
        }
    }


}
