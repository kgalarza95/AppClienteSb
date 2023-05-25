package vera.galarza.appclientesb.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vera.galarza.appclientesb.R;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener {

    private Button btnFindAll;
    private Button btnSaveUser;
    private Button btnUpdateUser;
    private Button btnDeleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtener referencias a los botones
        btnFindAll = findViewById(R.id.btnFindAll);
        btnSaveUser = findViewById(R.id.btnSaveUser);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);

        // Establecer el listener de clic para cada botón
        btnFindAll.setOnClickListener(this);
        btnSaveUser.setOnClickListener(this);
        btnUpdateUser.setOnClickListener(this);
        btnDeleteUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnFindAll:
                // Acción para el botón "Mostrar todos los Usuarios"
                findAll();
                break;
            case R.id.btnSaveUser:
                // Acción para el botón "Guardar Usuario"
                saveUser();
                break;
            case R.id.btnUpdateUser:
                // Acción para el botón "Actualizar Usuario"
                updateUser();
                break;
            case R.id.btnDeleteUser:
                // Acción para el botón "Eliminar Usuario"
                deleteUser();
                break;
        }
    }


    private void findAll() {
        // Lógica para mostrar todos los usuarios
        Intent intent = new Intent(this, ActivityListaUsuarios.class);
        startActivity(intent);
    }

    private void saveUser() {
        // Lógica para guardar un usuario
        Intent intent = new Intent(this, AtivityNuevoUsuario.class);
        startActivity(intent);
    }

    private void updateUser() {
        // Lógica para actualizar un usuario
        Intent intent = new Intent(this, ActivityBuscarActualizar.class);
        startActivity(intent);
    }

    private void deleteUser() {
        // Lógica para eliminar un usuario
        Intent intent = new Intent(this, ActivityBuscarEliminar.class);
        startActivity(intent);
    }
}