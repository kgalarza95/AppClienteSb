package vera.galarza.appclientesb.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import vera.galarza.appclientesb.R;
import vera.galarza.appclientesb.dto.Respuesta;
import vera.galarza.appclientesb.repository.RepositoryApiRest;

public class ActivityBuscarEliminar extends AppCompatActivity
        implements RepositoryApiRest.DAOCallbackServicio {

    ProgressDialog progressDialog;
    private Gson gson = new Gson();

    private EditText editTextUserId;
    private Button btnBuscar;
    private TextView textViewNombres;
    private EditText editTextNombres;
    private EditText editTextApellidos;
    private EditText editTextUsuario;
    private EditText editTextPass;
    private Button btnActualizar;
    private String accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_eliminar);
        editTextUserId = findViewById(R.id.editTextUserId);
        btnBuscar = findViewById(R.id.btnBuscar);
        textViewNombres = findViewById(R.id.textViewNombres);
        editTextNombres = findViewById(R.id.editTextNombres);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPass = findViewById(R.id.editTextPass);
        btnActualizar = findViewById(R.id.btnActualizar);
        progressDialog = new ProgressDialog(this);
        // Llamar al método para guardar el usuario en la base de datos

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accion = "BUSCAR";
                progressDialog.setMessage("Cargando...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Log.i("se envia: ", editTextUserId.getText().toString());
                RepositoryApiRest dao = new RepositoryApiRest(ActivityBuscarEliminar.this);
                dao.getUsuario(editTextUserId.getText().toString(), ActivityBuscarEliminar.this, Request.Method.GET);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accion = "actualizar";
                progressDialog.setMessage("Cargando...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                Log.i("se envia: ", params.toString());
                RepositoryApiRest dao = new RepositoryApiRest(ActivityBuscarEliminar.this);
                dao.getUsuario(editTextUserId.getText().toString(), ActivityBuscarEliminar.this,
                        Request.Method.DELETE);
            }
        });
    }

    Long id;

    @Override
    public void onSuccess(String response) {
        progressDialog.dismiss();
        try {
            Log.d("Response==========>  ", response);
            Respuesta data = gson.fromJson(response, Respuesta.class);
            Log.d("before.. Ok...Ok ==========> cod es: ", data.getCodRespuesta());
            if (data.getCodRespuesta().equals("00")) {
                if (accion.equals("BUSCAR")) {
                    Log.d("Ok...Ok ==========>  ", response);
                    Map<String, Object> listFilas = (Map<String, Object>) data.getDatos();
                    //idUsuarioBusq =  listFilas.get("ID").toString();

                    //Usuario usu = (Usuario) data.getDatos();
                    Log.d("Respuesta:  ", String.valueOf(listFilas));

                    id = Long.valueOf(listFilas.get("id").toString().replace(".0",""));
                    String nombres = listFilas.get("nombres").toString();;
                    String apellidos = listFilas.get("apellidos").toString();
                    String usuario =listFilas.get("usuario").toString();
                    String pass = listFilas.get("pass").toString();

                    // Establecer los valores de respuesta en los elementos correspondientes
                    textViewNombres.setText(nombres);
                    editTextNombres.setText(nombres);
                    editTextApellidos.setText(apellidos);
                    editTextUsuario.setText(usuario);
                    editTextPass.setText(pass);

                } else {
                    id = Long.valueOf(0);
                    // Limpiar los campos de texto
                    textViewNombres.setText("");
                    editTextNombres.setText("");
                    editTextApellidos.setText("");
                    editTextUsuario.setText("");
                    editTextPass.setText("");

                }

            } else {
                Toast.makeText(this, "" + data.getSms(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("error: ", String.valueOf(e));
            Toast.makeText(this, "ERROR: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(VolleyError error) {
        progressDialog.dismiss();
        Log.d("Error:  ", error.toString());
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}