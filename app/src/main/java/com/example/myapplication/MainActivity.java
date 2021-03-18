package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();

        Button buttonClick = (Button) findViewById(R.id.login);
        EditText usernameTView = (EditText) findViewById(R.id.username);
        EditText passwordTView = (EditText) findViewById(R.id.password);

        TextView messageTView = (TextView) findViewById(R.id.message);


        buttonClick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Signning in", Toast.LENGTH_SHORT).show();
                String url = "https://sharedproject852.000webhostapp.com/login.php";
                String username = usernameTView.getText().toString();
                String password = passwordTView.getText().toString();
                Toast.makeText(context, "Sending request", Toast.LENGTH_SHORT).show();
                StringRequest response = logIn(username, password, url, messageTView);
                //Toast.makeText(context, "Request Received", Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public StringRequest logIn(String username, String password, String url, TextView messageTV){
        List<String> jsonResponse = new ArrayList<>();
        //Binding data to request (Send to Server)
        /*
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);
            Log.i("Connection", postData.toString());
        } catch (JSONException e) {
            Log.e("Connection", e.toString());
        }

         */
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Sending Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Context context = getApplicationContext();
                CharSequence text = "Response Received!";

                try {
                    Log.i("Connection", response);
                    JSONObject jsonObject = new JSONObject(response.toString());
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    messageTV.setText(message);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.d("Connection", e.getMessage());
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Error Response", Toast.LENGTH_SHORT).show();
                Log.e("Connection", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", username);
                parameters.put("password", password);
                return parameters;
            }
        };
        Log.i("Connection", "Initializaing Request");
        requestQueue.add(stringRequest);
        return stringRequest;

    }
}