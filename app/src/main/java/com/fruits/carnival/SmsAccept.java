package com.fruits.carnival;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telecom.CallScreeningService;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fruits.carnival.system.ApiClientSmsGorod;
import com.squareup.okhttp.Response;

import java.util.Objects;

import retrofit.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;

public class SmsAccept extends AppCompatActivity {

    EditText edtPhone, edtOTP;
    Button verifyOTPBtn, generateOTPBtn;
    String verificationId;

    //new

    private Response responseBody;
    private int passCode;
    private String fullTextSms;
    private String codeCountry = "7";


    SharedPreferences sPref;

    final String SAVED_TEXT = "Status_user";





    //end













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_accept);

        //neew

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        passCode = generateCode();
        fullTextSms = "Ваш код подтверждения: " + passCode;



        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);

        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                                                      Toast.makeText(SmsAccept.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                                                  } else {
                                                      String phone = edtPhone.getText().toString();

                                                      Log.e("PHONE_NUMBER", phone);

                                                      ApiClientSmsGorod.getInstance()
                                                              .getApiServiceSmsGorod()
                                                              .sendSmsWithoutApiKey(phone, fullTextSms)
                                                              .enqueue(new Callback<com.squareup.okhttp.Response>() {
                                                                  @Override
                                                                  public void onResponse(Call<com.squareup.okhttp.Response> call, retrofit2.Response<com.squareup.okhttp.Response> response) {


                                                                  }

                                                                  @Override
                                                                  public void onFailure(Call<com.squareup.okhttp.Response> call, Throwable t) {
                                                                      t.printStackTrace();
                                                                      Toast.makeText(SmsAccept.this, "Ошибка ответа от сервера", Toast.LENGTH_LONG).show();
                                                                  }
                                                              });
                                                      }
                                                  }

                                              });





        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edtOTP.getText().toString())){
                    Toast.makeText(SmsAccept.this, "OTP box must not be empty!", Toast.LENGTH_SHORT).show();
                }
                else {


                    SaveUser();


                    final String SAVED_TEXT = "saved_text";
                    startActivity(new Intent(SmsAccept.this, Web.class));
                    Toast.makeText(SmsAccept.this, "Код корректен", Toast.LENGTH_LONG).show();

                }
            }

            private void SaveUser() {
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_TEXT, "User");
                ed.apply();
            }
        });
    }


    private int generateCode() {
        return (int) (Math.random() * 10000) + 1000;
    }

}