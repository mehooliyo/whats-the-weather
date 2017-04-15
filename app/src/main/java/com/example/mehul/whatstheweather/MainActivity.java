package com.example.mehul.whatstheweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

    private TextView header;
    private ImageView image;
    private TextView temperature;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = (TextView) findViewById(R.id.header);
        image = (ImageView) findViewById(R.id.imageView);
        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);

    }

    public void onClick(View v){
        EditText editText = (EditText) findViewById(R.id.editText);
        DownloadTask task = new DownloadTask();

        String api_key = getResources().getString(R.string.api_key);

        if(api_key.isEmpty()){
            Toast.makeText(getApplicationContext(), "No API Key Found", Toast.LENGTH_LONG).show();
            return;
        } else if (editText == null || editText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "No input", Toast.LENGTH_LONG).show();
            return;
        }

        String query = String.format(QUERY_ZIP, editText.getText(), api_key);
        Log.i("myapp", "query = " + query);
        task.execute(query);
    }

    private void updateView(CityWeather city){
        header.setText(city.getName() + ", " + city.getCountry());
        image.setImageResource(getWeatherImage(city.getWeather()));
        temperature.setText(city.getTemperature(true));
        description.setText(city.getDescription());
    }

    private int getWeatherImage(String weather){
        int drawableId = R.drawable.dunno;

        switch (weather){
            case "Clouds": drawableId = R.drawable.cloudy1;
                break;
        }

        return drawableId;

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
                Log.i("myapp", "Got weather " + weather);
                updateView(weather);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
