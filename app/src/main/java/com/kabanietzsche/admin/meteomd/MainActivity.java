package com.kabanietzsche.admin.meteomd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView gradesTextView;
    String temperature = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gradesTextView = (TextView) findViewById(R.id.grades_text_view);

        connectToMeteo();


    }

    private void connectToMeteo() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://meteo.md";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String responseParts[] = response.split("font color=\"#CC");

                Pattern pattern = Pattern.compile("3333\"><br>(.*?)</b>");
                Matcher matcher = pattern.matcher(responseParts[9]);


                while (matcher.find()) {
                    temperature = matcher.group(1);
                }


                gradesTextView.setText(temperature);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Шняга", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
