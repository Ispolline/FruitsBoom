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


    final String SAVED_STATUS = "none";


    @Override
    protected void onStart() {

        sPref = getPreferences(MODE_PRIVATE);
        String Status_user = sPref.getString(SAVED_STATUS, "");

        if (Status_user.equals("User")){

            finish();
            startActivity(new Intent(MainActivity.this, Web.class));
        }else{
            new NewThread().execute();

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
                doc = Jsoup.connect("https://wheelboom.site/check.html").get();
                String text_check = ((org.jsoup.nodes.Document) doc).text();
                text_check.toString();
                System.out.println(text_check);

                if (text_check.equals("Deny")){

                    Intent intent = new Intent(MainActivity.this, HomePage.class);

                    intent.putExtra("Choose", "Deny");



                    startActivity(intent);
                }else {
                    Intent intent2 = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent2);
                }
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }






            return null;

        }

    }
}