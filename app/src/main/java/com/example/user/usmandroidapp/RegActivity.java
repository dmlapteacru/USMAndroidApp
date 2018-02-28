package com.example.user.usmandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import java.io.FileOutputStream;
import java.io.IOException;

public class RegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }
    public void regNewUser(View view){
        EditText textLogin = (EditText) findViewById(R.id.textLogin);
        EditText textPassword = (EditText) findViewById(R.id.textPassword);

        String newUser = textLogin.getText().toString() + " " + textPassword.getText().toString() + "\n";
        try{
            FileOutputStream fout = openFileOutput("mydata", MODE_APPEND);
            fout.write(newUser.getBytes());
            fout.close();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
