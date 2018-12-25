package hw3.muammersahin.com.muammersahinhw3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    JSONObject currency;
    JSONObject currencies;
    Spinner s1, s2;
    EditText miktar;
    Button button;
    private String[] kurlar;
    private RequestQueue mRequestQueue;



    private ArrayAdapter<String> dataAdapter;


    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);


        db = new DatabaseHelper(this);


        s1 = findViewById(R.id.spinner1);
        s2 = findViewById(R.id.spinner2);
        miktar = findViewById(R.id.miktar);
        button = findViewById(R.id.convertBtn);


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest (
                Request.Method.GET, "https://api.exchangeratesapi.io/latest", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        currency = response;

                        Log.d("TAH", "Deneme");
                        // Call to AsyncTask
                        new getBooks().execute();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result",
                        "ERROR JSONObject request" + error.toString());

            }
        });

        mRequestQueue.add(mJsonObjectRequest);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Bundle b = new Bundle();
                b.putString("miktar", miktar.getText().toString());
                b.putString("from", s1.getSelectedItem().toString());
                b.putString("to", s2.getSelectedItem().toString());
                try {

                    Double x = Double.parseDouble(miktar.getText().toString()) / Double.parseDouble(currencies.getString(s1.getSelectedItem().toString()+"")) ;
                    Double to = x * Double.parseDouble(currencies.getString(s2.getSelectedItem().toString()+""));
                    b.putDouble("result", to);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }

    public String loadJSONFromAsset(Context context) {
        String json = null;

        try {
            URL url = new URL("http://data.fixer.io/api/latest?access_key=c39ce5d17ab899cd11b880cc84db423a&format=1");
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    private class getBooks extends AsyncTask<Void, Void, Void> {

        // Main job should be done here
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Getting JSON Array
                currencies = currency.getJSONObject("rates");
                Iterator keysToCopyIterator = currencies.keys();
                List<String> keysList = new ArrayList<String>();
                while(keysToCopyIterator.hasNext()) {
                    String key = (String) keysToCopyIterator.next();
                    keysList.add(key);
                }
                kurlar = keysList.toArray(new String[keysList.size()]);
                dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, kurlar);


            }
            catch (JSONException ee) {
                ee.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // What do you want to do after doInBackground() finishes
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Dismiss the progress dialog


            s1.setAdapter(dataAdapter);
            s2.setAdapter(dataAdapter);
        }
    }
}
