package com.example.user.usmandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import com.example.user.usmandroidapp.dao.User;

import java.io.*;

public class Messages extends AppCompatActivity {
private EditText messageS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

    }

    public void findMessages(View view){
        messageS = (EditText) findViewById(R.id.messages);
        String messages="";
        try{
            FileInputStream fin = openFileInput("mymessages");
            BufferedReader in = new BufferedReader(new InputStreamReader(fin));
            String temp;
            while( (temp = in.readLine()) != null){
                String[] words = temp.split(" ");
                messages +="Mess: " + words[1] + " Key: " + words[2]  + "\n";
            }
            in.close();
            fin.close();
            messageS.setText(messages);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
