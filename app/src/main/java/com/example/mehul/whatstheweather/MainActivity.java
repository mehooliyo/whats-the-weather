package com.example.mehul.whatstheweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String QUERY_ZIP = "http://api.openweathermap.org/data/2.5/weather?zip=%s,us&appid=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        EditText e = (EditText) findViewById(R.id.editText);
        DownloadTask task = new DownloadTask();

        String api_key = getResources().getString(R.string.api_key);
        if(api_key == null || api_key.isEmpty()){
            Toast.makeText(getApplicationContext(), "No API Key Found", Toast.LENGTH_LONG).show();
            Log.e("onClick", "No API Key Found");
            return;
        }

        String query = String.format(QUERY_ZIP, "07306", api_key);
        Log.i("onClick", "query = " + query);
        task.execute(query);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (MalformedURLException e){
                Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_LONG);
            } catch (IOException e){
                Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_LONG);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject jsonResult = new JSONObject(result);
                CityWeather weather = new CityWeather(jsonResult);
                Log.i("onPostExecute", "Got weather for " + weather.getName() + ", " +  weather.getCountry());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
