package com.example.espirituhielo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Pedidos extends AppCompatActivity {

    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        spinner1 = (Spinner)findViewById(R.id.spinner);

        String [] opciones = {"5 kg","3 kg"};

        ArrayAdapter <String>   adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter);
    }

        public void showMessage(String message) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        public void onBtnLoginClicked(View view){

            EditText txtCantidad = (EditText)findViewById(R.id.editText6);
            EditText txtFecha = (EditText)findViewById(R.id.editText8);
            String cantidad =  txtCantidad.getText().toString();
            String fecha =  txtFecha.getText().toString();

            //2.  Creating a message using user input
            Map<String, String> message = new HashMap<>();
            message.put("cantidad", cantidad);
            message.put("fecha", fecha);

            //3. Convertir el objeto mensaje a JSON string
            JSONObject jsonMessage = new JSONObject(message);


            //4. Mandar mensaje

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    "http://10.0.2.2:80/pedidos",
                    jsonMessage,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            showMessage("Pedido realizado!");
                            try {
                                String username = response.getString("username");
                                int user_id = response.getInt("user_id");
                                goToRegistroActivity();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //cuando el server responda
                            showMessage("Error");
                        }
                    }
            );
            //5. Mandar request al server
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }

    private void goToRegistroActivity(){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
}
