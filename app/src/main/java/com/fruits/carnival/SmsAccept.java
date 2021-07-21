package com.fruits.carnival;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fruits.carnival.system.ApiClientSmsGorod;
import com.squareup.okhttp.Response;

import java.util.Objects;

import retrofit.Call;
import retrofit.Callback;

import static android.content.ContentValues.TAG;

public class SmsAccept extends AppCompatActivity {

    EditText edtPhone, edtOTP;
    Button verifyOTPBtn, generateOTPBtn;
    String verificationId;

    //new

    private Response responseBody;
    private int passCode;
    private String fullTextSms;
    private String codeCountry = "7";



    //end













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_accept);

        //neew

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        passCode = generateCode();
        fullTextSms = "Ваш код подтверждения: " + passCode;






        //end





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
                                                              .sendSms(PrecomputedText.Params.keyApi, phone, fullTextSms)
                                                              .enqueue(new Callback<Response>() {
                                                                  @Override
                                                                  public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {

                                                                      responseBody = response.body();

                                                                      if (Objects.requireNonNull(responseBody).getStatus().equalsIgnoreCase("success")) {
                                                                          Toast.makeText(SmsAccept.this, "СМС успешно отправленно", Toast.LENGTH_LONG).show();
                                                                      } else {
                                                                          Toast.makeText(SmsAccept.this, "Ошибка ответа от сервера", Toast.LENGTH_LONG).show();
                                                                      }
                                                                  }

                                                                  @Override
                                                                  public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
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

                    startActivity(new Intent(SmsAccept.this, Web.class));
                    Toast.makeText(SmsAccept.this, "Код корректен", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    // Generate #### number
    private int generateCode() {
        return (int) (Math.random() * 10000) + 1000;
    }

}