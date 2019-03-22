package com.example.shiva.json_data;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText e;
    String main,description;

void report(View v){
    e=findViewById(R.id.name);
    String city=e.getText().toString();
    Log.i("City Name",city);
    DownloadTask task=new DownloadTask();
    task.execute("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=4833b59654344d64dc6d0c656e8e53c8&units=Imperial");
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            }
            catch(Exception e) {

                e.printStackTrace();

                return "Failed";

            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject json=new JSONObject(result);

                String w1= json.getString("weather");
                JSONObject w2= json.getJSONObject("main");

                JSONArray arr1=new JSONArray(w1);


                for(int i=0;i<arr1.length();i++) {
                    JSONObject j = arr1.getJSONObject(i);
                    main=j.getString("main");
                    description=j.getString("description");
                    }
                String a;

                a=w2.getString("temp");
                float b=Float.parseFloat(a);
                Log.i("Temp", String.valueOf(b));
                float temp1= (float) (b-32.0);
                float temp2= (float) (temp1*(5.0/9.0));
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                float temp3 = Float.valueOf(decimalFormat.format(temp2));
                String temp4=String.valueOf(temp3);

                TextView t=(TextView)findViewById(R.id.temp);
                TextView t1=(TextView)findViewById(R.id.des);
                TextView t2=(TextView)findViewById(R.id.textView4);
                t.setText(temp4);
                t1.setText(description);
                t.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
