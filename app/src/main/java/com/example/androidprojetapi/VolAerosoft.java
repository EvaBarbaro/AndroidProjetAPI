package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VolAerosoft extends AppCompatActivity {

    ListView listView;

    ArrayList<HashMap<String, String>> volsList;

    private static String API_URL="http://10.75.25.182:8080/apache/AerosoftAPI/vol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_aerosoft);

        volsList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewVol);
        Button b1;
        b1 = findViewById(R.id.titleAerosoft);

        extractVols();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),HomeAerosoft.class);
                startActivity(i);
            }
        });
    }

    private void extractVols() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray volArray = obj.getJSONArray("Vols");
                            for (int i = 0; i < volArray.length(); i++) {
                                JSONObject volObject = volArray.getJSONObject(i);

                                String NumVol = volObject.getString("NumVol");
                                String AeroportDept = volObject.getString("AeroportDept");
                                String HDepart = volObject.getString("HDepart");
                                String AeroportArr = volObject.getString("AeroportArr");
                                String HArrivee = volObject.getString("HArrivee");

                                HashMap<String, String> vols = new HashMap<>();

                                vols.put("NumVol", NumVol);
                                vols.put("AeroportDept", AeroportDept);
                                vols.put("HDepart", HDepart);
                                vols.put("AeroportArr", AeroportArr);
                                vols.put("HArrivee", HArrivee);

                                volsList.add(vols);

                            }
                            ListAdapter adapter = new SimpleAdapter(
                                    VolAerosoft.this, volsList,
                                    R.layout.list_item_vol, new String[]{"NumVol", "AeroportDept",
                                    "HDepart", "AeroportArr", "HArrivee"}, new int[]{R.id.NumVol,
                                    R.id.AeroportDept, R.id.HDepart, R.id.AeroportArr, R.id.HArrivee});

                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}