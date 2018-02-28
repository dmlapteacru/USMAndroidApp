package com.example.user.usmandroidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.usmandroidapp.dao.User;
import org.w3c.dom.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText textEnc, key, sendTo;
    private TextView hello, welcomeUser;
    private Button btn, btn1,send, btnShowMess;
    @Override
    // метод инициализации и загрузки activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //инициализация кнопок и установка фоновых цветов
        btn = (Button) findViewById(R.id.btnEnc);
        btn1 = (Button) findViewById(R.id.btnDec);
        send = (Button) findViewById(R.id.sendMess);
        btnShowMess = (Button) findViewById(R.id.btnShowMess);
        send.setBackgroundColor(Color.DKGRAY);
        btn.setBackgroundColor(Color.DKGRAY);
        btn1.setBackgroundColor(Color.DKGRAY);
        btnShowMess.setBackgroundColor(Color.DKGRAY);
        welcomeUser = (TextView) findViewById(R.id.welcomeUser);
        welcomeUser.setText("Welcome, " + getIntent().getStringExtra("WUser"));
    }

    /*Для некоторых методов или использования классов необходимо
    * ввести анотацию минимальной версии API, в которой классы не Deprecated и компилируются*/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    //Метод, исполняюзийся при нажатии кнопки ENCRYPT
    public void btnEncr(View view){
        //Инициализация кнопок и текстовых полей
        btn = (Button) findViewById(R.id.btnEnc);
        btn1 = (Button) findViewById(R.id.btnDec);
        hello = (TextView) findViewById(R.id.hello);
        textEnc = (EditText) findViewById(R.id.textEnc);
        key = (EditText) findViewById(R.id.key);
        btn.setBackgroundColor(Color.RED);
        btn1.setBackgroundColor(Color.DKGRAY);
        //Пробуем зашифровать текст, используя ключ
        try {
            hello.setText(caesarEncrypt(textEnc.getText().toString(), key.getText().toString()));
        }catch (Exception e){
            //Перехватываем исключение, летящее в случае, если текстовые поля пустые или ключ не является цифрой
            e.printStackTrace();
            btn.setBackgroundColor(Color.DKGRAY);
            btn1.setBackgroundColor(Color.DKGRAY);
            //Выдаем ошибку на экране с соответсвующей причиной поломки
            if(TextUtils.isEmpty(textEnc.getText())){
                textEnc.setError("Complete!");
            } else if(!TextUtils.isDigitsOnly(key.getText())){
                key.setError("NOT A NUMBER");
            } else key.setError("Complete!");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void btnDecr(View view){
        btn = (Button) findViewById(R.id.btnEnc);
        btn1 = (Button) findViewById(R.id.btnDec);
        hello = (TextView) findViewById(R.id.hello);
        textEnc = (EditText) findViewById(R.id.textEnc);
        key = (EditText) findViewById(R.id.key);
        btn1.setBackgroundColor(Color.RED);
        btn.setBackgroundColor(Color.DKGRAY);
        try {
            hello.setText(caesarDecrypt(textEnc.getText().toString(), key.getText().toString()));
        }catch (Exception e){
            e.printStackTrace();
            btn.setBackgroundColor(Color.DKGRAY);
            btn1.setBackgroundColor(Color.DKGRAY);
            if(TextUtils.isEmpty(textEnc.getText())){
                textEnc.setError("Complete!");
            } else if(!TextUtils.isDigitsOnly(key.getText())){
                key.setError("NOT A NUMBER");
            }else key.setError("Complete!");
        }
    }
    //Метод шифрования по методу Цезаря
    //Для шифрования была использована вся таблица ASCII
    //Смещение по ключу
    public String caesarEncrypt(String txt, String key1){
        int key = Integer.parseInt(key1);
        final int MAX_VAL = 125, MIN_VAL = 32;
        String encryptedTxt;
        int txtInt;
        char[] txtChar = new char[txt.length()];

        for(int i=0; i<txt.length(); i++){
            txtInt = (int)txt.charAt(i) + key;
            if(txtInt > MAX_VAL) txtInt = MIN_VAL + key;
            txtChar[i] = (char)txtInt;
        }

        encryptedTxt = new String(txtChar);
        return encryptedTxt;
    }

    //Метод расшифровки по ключу
    public String caesarDecrypt(String txt, String key1){
        int key = Integer.parseInt(key1);
        final int MAX_VAL = 125, MIN_VAL = 32;
        String decryptedTxt;
        int txtInt;
        char[] txtChar = new char[txt.length()];
        for(int i=0; i<txt.length(); i++){
            txtInt = (int)txt.charAt(i) - key;
            if(txtInt < MIN_VAL) txtInt = MAX_VAL - key;
            txtChar[i] = (char)txtInt;
        }

        decryptedTxt = new String(txtChar);
        return decryptedTxt;
    }
    public void sendMessage(View view){
        textEnc = (EditText) findViewById(R.id.textEnc);
        key = (EditText) findViewById(R.id.key);
        sendTo = (EditText) findViewById(R.id.textToUser);
        if(sendTo.getText() == null || !checkUserExists(sendTo.getText().toString())){
            Toast.makeText(getBaseContext(),"Send FAILED. NO SUCH USER",Toast.LENGTH_SHORT).show();
        } else {
            String newMessage =sendTo.getText().toString()+ " " + caesarEncrypt(textEnc.getText().toString(), key.getText().toString()) + " " + key.getText().toString() + "\n";
            try{
                FileOutputStream fout = openFileOutput("mymessages", MODE_APPEND);
                fout.write(newMessage.getBytes());
                fout.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        textEnc.setText("");
        key.setText("");
        sendTo.setText("");
    }
    public boolean checkUserExists(String username){
        List<User> users = new ArrayList<>(findAllUsers());
        for (User user:users
             ) {
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            FileInputStream fin = openFileInput("mydata");
            BufferedReader in = new BufferedReader(new InputStreamReader(fin));
            String temp;
            while( (temp = in.readLine()) != null){
                String[] words = temp.split(" ");
                users.add(new User(words[0], words[1]));
            }
            in.close();
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
    public void showMessages(View view){
        Intent intent = new Intent(this, Messages.class);
        intent.putExtra("WUserN",getIntent().getStringExtra("WUser"));
        startActivity(intent);
    }

    /*//Метод выхода из приложения
    public void logout(View view){
        //При нажатии на кнопку Exit создается окно с подтвеждением
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit ??");
        //PositiveButton используется как кнопка подтверждения операции
        //"ДА"
        //При нажатии осуществляется выход из программы
        builder.setPositiveButton("Yes. Exit now!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                //finish();
                System.exit(1);
                moveTaskToBack(true);
                android.os.Process.killProcess(Process.myPid());
            }

        });
        //NegativeButton используется для отмены операции
        //При нажатии окно выхода закрывается
        builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

}
