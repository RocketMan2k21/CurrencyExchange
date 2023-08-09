package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;


public class MainActivity extends AppCompatActivity {

    private EditText myGiveText;
    private TextView myGetText;
    private Spinner give_sp;
    private Spinner take_sp;

    CurrencyModel[] currensies;

    MyAdapter adapter;


    private RequestQueue requestQueue;

    int give_cur;
    int take_cur;
    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGetText = findViewById(R.id.editTextTextPersonName3);
        myGiveText = findViewById(R.id.editTextTextPersonName);
        Button exchangeBtn = findViewById(R.id.button4);

        requestQueue = Volley.newRequestQueue(this);

        take_sp = findViewById(R.id.spinner_take);
        give_sp = findViewById(R.id.spinner_give);


        currensies =  new CurrencyModel[]{
                new CurrencyModel("UAH", R.drawable.ukraine),
                new CurrencyModel("USD", R.drawable.usa),
                new CurrencyModel("EUR", R.drawable.european),
                new CurrencyModel("GBP", R.drawable.uk),
                new CurrencyModel("PLN", R.drawable.poland),
        };

        CurrencyModel[] currencies1 = new CurrencyModel[]{
                new CurrencyModel("USD", R.drawable.usa),
                new CurrencyModel("UAH", R.drawable.ukraine),
                new CurrencyModel("EUR", R.drawable.european),
                new CurrencyModel("GBP", R.drawable.uk),
                new CurrencyModel("PLN", R.drawable.poland),
        };

        adapter = new MyAdapter(this,
                R.layout.custom_spinner,
                currensies
                );

        MyAdapter adapter1 = new MyAdapter(this,
                R.layout.custom_spinner,
                currencies1
        );


        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        take_sp.setAdapter(adapter);
        give_sp.setAdapter(adapter1);



        take_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                take_cur = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        give_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                give_cur = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myGiveText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double doubleValue = 0;
                if (s != null) {
                    try {
                        doubleValue = Double.parseDouble(s.toString().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        //Error
                    }
                }
                amount = String.valueOf(doubleValue);
            }
        });


        exchangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = String.valueOf(myGiveText.getText());
                if(amount.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();;
                    amount = "0";
                    myGiveText.setText(amount);
                }
                jsonParse(amount, currencies1[give_cur].getCurrency(), currensies[take_cur].getCurrency());
            }
        });

    }
    private void jsonParse(String amount, String give, String take){

        String url = "https://v6.exchangerate-api.com/v6/5288a6e8f5171e3f971c1a97/pair/" + give + "/" + take +"/" + amount;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String conversionRate = response.getString("conversion_result");
                            myGetText.setText(conversionRate);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}

