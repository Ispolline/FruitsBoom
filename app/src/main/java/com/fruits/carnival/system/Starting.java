package com.fruits.carnival.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.fruits.carnival.MainActivity;
import com.fruits.carnival.R;
import com.fruits.carnival.Web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Starting extends AppCompatActivity {


    @Override
    protected void onStart() {

        new NewThread().execute();



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