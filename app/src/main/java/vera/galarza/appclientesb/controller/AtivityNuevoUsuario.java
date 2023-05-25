package vera.galarza.appclientesb.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import vera.galarza.appclientesb.R;
import vera.galarza.appclientesb.dto.Respuesta;
import vera.galarza.appclientesb.dto.Usuario;
import vera.galarza.appclientesb.repository.RepositoryApiRest;

public class AtivityNuevoUsuario extends AppCompatActivity
        implements RepositoryApiRest.DAOCallbackServicio {

    ProgressDialog progressDialog;
    private Gson gson = new Gson();
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativity_nuevo_usuario);

        // Obtener referencias de los elementos en el XML
        editTextNombre = findViewById(R.id.editTextNombres);
        editTextApellido = findViewById(R.id.editTextApellidos);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextContraseña);
        btnGuardar = findViewById(R.id.buttonGuardar);
        progressDialog = new ProgressDialog(this);

        // Agregar listener al botón de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String usuario = editTextUsuario.getText().toString();
                String password = editTextPassword.getText().toString();

                // Crear un nuevo objeto Usuario con los valores ingresados
                Usuario nuevoUsuario = new Usuario(nombre, apellido, usuario, password);

                // Llamar al método para guardar el usuario en la base de datos
                progressDialog.setMessage("Cargando...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                //vista = view;
                Map<String, String> params = new HashMap<>();
                params.put("nombres", nombre);
                params.put("apellidos", apellido);
                params.put("usuario", usuario);
                params.put("pass", password);

                Log.i("se envia: ", params.toString());
                RepositoryApiRest dao = new RepositoryApiRest(AtivityNuevoUsuario.this);
                dao.crudUsuario(params, AtivityNuevoUsuario.this, Request.Method.POST);
            }
        });
    }

    @Override
    public void onSuccess(String response) {
        progressDialog.dismiss();
        try {
            Log.d("Response==========>  ", response);
            Respuesta data = gson.fromJson(response, Respuesta.class);
            if (data.getCodRespuesta().equals("00")) {
                editTextNombre.setText("");
                editTextPassword.setText("");
                editTextUsuario.setText("");
                editTextApellido.setText("");
            } else {
                Toast.makeText(this, ""+data.getSms(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.e("error: ", String.valueOf(e));
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