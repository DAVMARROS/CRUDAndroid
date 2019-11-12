package com.example.crudservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NuevoEmpleadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_empleado);

        FloatingActionButton fab = findViewById(R.id.fabGuardar);
        FloatingActionButton fabRegresar = findViewById(R.id.fabRegresarAdd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmpleado();
            }
        });
        fabRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NuevoEmpleadoActivity.this, ListaEmpleadosActivity.class));
            }
        });

    }

    private void saveEmpleado() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empleados.jl.serv.net.mx/CRUD/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);

        EditText txtNombre = (EditText )findViewById(R.id.txtNombreE);
        EditText txtSueldo = (EditText )findViewById(R.id.txtSueldoE);


        Call<ListaEmpleados> call = postService.guardarEmpleado(txtNombre.getText().toString(),txtSueldo.getText().toString());

        call.enqueue(new Callback<ListaEmpleados>() {
            @Override
            public void onResponse(Call<ListaEmpleados> call, Response<ListaEmpleados> response) {

                if(response.isSuccessful()) {
                    Toast  toast = Toast.makeText(getApplicationContext(),"Guardado correctamente", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(new Intent(NuevoEmpleadoActivity.this, ListaEmpleadosActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ListaEmpleados> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
