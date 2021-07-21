package com.fruits.carnival;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Starting extends AppCompatActivity {

    SharedPreferences sPref;

    final String SAVED_TEXT = "none";


    @Override
    protected void onStart() {

        sPref = getPreferences(MODE_PRIVATE);
        String Status_user = sPref.getString(SAVED_TEXT, "");

        if (Status_user == "none"){
            new NewThread().execute();
        }else{
            finish();
            startActivity(new Intent(Starting.this, Web.class));
        }




        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);



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
                    Intent intent = new Intent(Starting.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }if (text_check.equals("show")){
                    Intent intent2 = new Intent(Starting.this, Web.class);
                    startActivity(intent2);
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }






            return null;

        }

    }
}