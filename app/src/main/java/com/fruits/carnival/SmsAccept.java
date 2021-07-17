package com.fruits.carnival;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit.Retrofit;
import retrofit.http.GET;

public class SmsAccept extends AppCompatActivity {

    EditText phone;
    Button get;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_accept);


        phone = findViewById(R.id.phone);
        get = findViewById(R.id.get_sms);


        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_number;
                txt_number = phone.getText().toString();
            }
        });
    }
}