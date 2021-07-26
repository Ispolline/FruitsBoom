package com.fruits.carnival;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telecom.CallScreeningService;
import android.telephony.PhoneNumberUtils;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;

public class SmsAccept extends AppCompatActivity {

    EditText edtPhone, edtOTP;
    Button verifyOTPBtn, generateOTPBtn;
    String verificationId;
    String[] data = {"Казахстан (+7)", "Российская Федерация (+7)", "Украина (+380)"};


    //new

    private Response responseBody;
    private int passCode;
    private String fullTextSms;
    private String codeCountry = "7";

    private final static String FILE_NAME = "user.txt";



    SharedPreferences sPref;

    final String SAVED_STATUS = "Status_user";


    String code = "7";





    //end













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_accept);





        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Выберите код страны");




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



                                                      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> parent, View view,
                                                                                     int position, long id) {
                                                              // показываем позиция нажатого элемента
//                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();

                                                              if (position == 0){
                                                                  String code = "7";
                                                              }else if (position == 1){
                                                                  String code = "7";
                                                              }else if (position == 2){
                                                                  String code = "380";
                                                              }
                                                          }
                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> arg0) {
                                                          }
                                                      });


                                                      String final_number = (code + phone);




                                                      Log.e("PHONE_NUMBER", final_number);


                                                      ApiClientSmsGorod.getInstance()
                                                              .getApiServiceSmsGorod()
                                                              .sendSmsWithoutApiKey(final_number, fullTextSms)
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


                    if (Integer.parseInt(edtOTP.getText().toString()) == passCode){
                        FileOutputStream fos = null;
                        try {
                            String text = "User";

                            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                            fos.write(text.getBytes());
                            Toast.makeText(SmsAccept.this, "Файл сохранен", Toast.LENGTH_SHORT).show();
                        }
                        catch(IOException ex) {

                            Toast.makeText(SmsAccept.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        finally{
                            try{
                                if(fos!=null)
                                    fos.close();
                            }
                            catch(IOException ex){

                                Toast.makeText(SmsAccept.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        startActivity(new Intent(SmsAccept.this, Web.class));
                        Toast.makeText(SmsAccept.this, "Код корректен", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SmsAccept.this, "Код неверен", Toast.LENGTH_LONG).show();
                    }



                }
            }

        });
    }


    private int generateCode() {
        return (int) (Math.random() * 10000) + 1000;
    }

}