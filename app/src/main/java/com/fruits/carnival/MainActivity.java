package com.fruits.carnival;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adefruandta.spinningwheel.SpinningWheelView;
import com.fruits.carnival.system.Starting;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.OkHttpClient;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity {

    SpinningWheelView wheelView;

    ImageView spin;

    TextView balance;

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onStart() {

        new MaterialTapTargetPrompt.Builder(MainActivity.this)
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
                            new MaterialTapTargetPrompt.Builder(MainActivity.this)
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


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor((okhttp3.Interceptor) new UserAgentInterceptor(System.getProperty("http.agent")));

        OkHttpClient client = httpClient.build();




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
        setContentView(R.layout.activity_main);

        spin = findViewById(R.id.spin);

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelView.rotate(43, 3000, 50);

                balance = findViewById(R.id.balance);
                // сделать время перед переходом



                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        balance.setText("Balance: 10 000");
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Вы выйграли!")
                                .setMessage("Теперь авторизируйтесь!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                        new MainActivity.NewThread().execute();


                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        });
    }


    public class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg){
            Document doc = null;

            try {
                doc = Jsoup.connect("https://wheelboom.site/check.html").get();
                String text_check = ((org.jsoup.nodes.Document) doc).text();
                text_check.toString();
                System.out.println(text_check);

                if(text_check.equals("Allow")){
                    startActivity(new Intent(MainActivity.this, SmsAccept.class));
                    finish();
                }if (text_check.equals("Deny")){

                    //null
                }
            } catch (IOException e) {
                e.printStackTrace();
            }






            return null;

        }

    }
}