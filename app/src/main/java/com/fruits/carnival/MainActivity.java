package com.fruits.carnival;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;


    final String SAVED_STATUS = "none";

    private final static String FILE_NAME = "user.txt";


    @Override
    protected void onStart() {

        FileInputStream fin = null;
        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);

            finish();
            startActivity(new Intent(MainActivity.this, Web.class));
        }
        catch(IOException ex) {

            new NewThread().execute();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();

                new NewThread().execute();
            }
            catch(IOException ex){


                new NewThread().execute();
            }
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