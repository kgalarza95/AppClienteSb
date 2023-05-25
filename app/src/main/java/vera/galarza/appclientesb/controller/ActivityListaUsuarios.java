package vera.galarza.appclientesb.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vera.galarza.appclientesb.R;
import vera.galarza.appclientesb.dto.Respuesta;
import vera.galarza.appclientesb.dto.Usuario;
import vera.galarza.appclientesb.repository.RepositoryApiRest;
import vera.galarza.appclientesb.util.UserAdapter;

public class ActivityListaUsuarios extends AppCompatActivity
        implements RepositoryApiRest.DAOCallbackServicio {

    ProgressDialog progressDialog;
    private Gson gson = new Gson();

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Usuario> userList;

    public ActivityListaUsuarios() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        // Obtén la lista de usuarios de tu fuente de datos (por ejemplo, una base de datos o una API)
        userList = getUserList();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
        progressDialog = new ProgressDialog(this);
        // Llamar al método para guardar el usuario en la base de datos
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //vista = view;
        Map<String, String> params = new HashMap<>();
        params.put("nombres", "");

        Log.i("se envia: ", params.toString());
        RepositoryApiRest dao = new RepositoryApiRest(ActivityListaUsuarios.this);
        dao.crudUsuario(params, ActivityListaUsuarios.this, Request.Method.GET);
    }

    // Método para obtener la lista de usuarios (puedes reemplazarlo con tus datos reales)
    private List<Usuario> getUserList() {
        List<Usuario> userList = new ArrayList<>();

        // Agrega usuarios a la lista
        userList.add(new Usuario("John Doe", "johndoe","",""));
        userList.add(new Usuario("John Doe", "johndoe","",""));
        userList.add(new Usuario("John Doe", "johndoe","",""));
        userList.add(new Usuario("John Doe", "johndoe","",""));

        // ...

        return userList;
    }


    @Override
    public void onSuccess(String response) {
        progressDialog.dismiss();
        try {
            Log.d("Response==========>  ", response);
            Respuesta data = gson.fromJson(response, Respuesta.class);
            Log.d("before.. Ok...Ok ==========>  ", response);
            if (data.getCodRespuesta().equals("00")) {
                Log.d("Ok...Ok ==========>  ", response);
                String json = gson.toJson(data.getDatos());
                Type listType = new TypeToken<List<Usuario>>() {
                }.getType();
                List<Usuario> listaCursos = gson.fromJson(json, listType);
                Log.d("Respuesta:  ", String.valueOf(listaCursos));
                Log.d("Respuesta:  ", listaCursos.get(0).getNombres());
                init( listaCursos);
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

    public void init(List<Usuario> listaCursos) {
       // recyclerView.setHasFixedSize(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(listaCursos);
        recyclerView.setAdapter(userAdapter);
    }
}