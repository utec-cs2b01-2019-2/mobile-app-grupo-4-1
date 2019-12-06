package com.example.espirituhielo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Activity getActivity(){
        return this;
    }

    public void onBtnLoginClicked(View view){
        //1. Obtener username y password
        EditText txtUsername = (EditText)findViewById(R.id.editText);
        EditText txtPassword = (EditText)findViewById(R.id.editText3);
        String username =  txtUsername.getText().toString();
        String password =  txtPassword.getText().toString();

        //2.  Creating a message using user input
        Map<String, String> message = new HashMap<>();
        message.put("username", username);
        message.put("password", password);

        //3. Convertir el objeto mensaje a JSON string
        JSONObject jsonMessage = new JSONObject(message);


        //4. Mandar mensaje
        //4.1 Instalar volley
        //4.2 Crear objeto de request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/authenticate",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showMessage("Autorizado!");
                        try {
                            String username = response.getString("username");
                            int user_id = response.getInt("user_id");
                            goToPedidosActivity(user_id, username);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //cuando el server responda
                        showMessage("Unauthorized!");
                    }
                }
        );
        //5. Mandar request al server
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void onBtnLoginClicked2(View view){
        goToRegistroActivity();
    }

    private void goToPedidosActivity(int user_id, String username) {
        Intent intent = new Intent(this, Pedidos.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void goToRegistroActivity(){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }


}
