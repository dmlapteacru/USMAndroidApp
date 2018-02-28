package com.example.user.usmandroidapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.user.usmandroidapp.dao.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText textLogin, textPassword;
    private String file = "mydata";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setBackgroundColor(Color.RED);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login(View view){
        textLogin = (EditText) findViewById(R.id.textLogin);
        textPassword = (EditText) findViewById(R.id.textPassword);
        String username = textLogin.getText().toString();
        String password = textPassword.getText().toString();
        try {
            FileInputStream fin = openFileInput(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(fin));
            String temp;
            Boolean success =false;
            while( (temp = in.readLine()) != null){
                String[] words = temp.split(" ");
                if(username.equals(words[0])&&password.equals(words[1])){
                    success=true;
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("WUser",username);
                    startActivity(intent);
                }
            }
            if(!success){
                Toast.makeText(getBaseContext(),"Sign IN FAILED",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void regActivity(View view){
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
    }

}
