package vera.galarza.appclientesb.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import vera.galarza.appclientesb.R;
import vera.galarza.appclientesb.util.UtilGlobal;
import vera.galarza.appclientesb.dto.Respuesta;
import vera.galarza.appclientesb.repository.RepositoryApiRest;

public class MainActivity extends AppCompatActivity
        implements RepositoryApiRest.DAOCallbackServicio {

    ProgressDialog progressDialog;
    private Gson gson = new Gson();

    EditText txtUsuario;
    EditText txtPass;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = findViewById(R.id.editTextUsername);
        txtPass = findViewById(R.id.editTextPassword);
        btnIngresar = findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(view.getContext(), PrincipalProfesor.class));
                //solo prueba de recyclerview
                //  startActivity(new Intent(view.getContext(), RecyclerViewLista.class));

                progressDialog.setMessage("Cargando...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                //vista = view;
                Map<String, String> params = new HashMap<>();
                params.put("usuario", txtUsuario.getText().toString().trim());
                params.put("contrase", txtPass.getText().toString().trim());

                Log.i("se envia: ", params.toString());
                RepositoryApiRest dao = new RepositoryApiRest(MainActivity.this);
                dao.apiLogin(params, MainActivity.this);

            }
        });
    }

    @Override
    public void onSuccess(String response) {
        progressDialog.dismiss();
        try {
            Log.d("Response==========>  ", response);
            Respuesta data = gson.fromJson(response, Respuesta.class);
            if (data.getCod().equals("00")) {
                UtilGlobal.JWT = data.getToken();
                Intent intent = new Intent(this, ActivityMenu.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, ""+data.getSms(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "ERROR: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(VolleyError error) {
        progressDialog.dismiss();
        Log.d("Error:  ", error.toString());
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}