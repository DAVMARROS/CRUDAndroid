package com.example.crudservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerEmpleadoActivity extends AppCompatActivity {

    public static String idEmpleado="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_empleado);


        FloatingActionButton fabregresar = findViewById(R.id.fabRegresar);
        FloatingActionButton fabeditar = findViewById(R.id.fabEditar);
        FloatingActionButton fabborrar = findViewById(R.id.fabBorrar);

        fabregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VerEmpleadoActivity.this, ListaEmpleadosActivity.class));
            }
        });
        fabeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarEmpleadoActivity.idEmpleado= idEmpleado;

                startActivity(new Intent(VerEmpleadoActivity.this, EditarEmpleadoActivity.class));
            }
        });
        fabborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarEmpleado();
            }
        });

        getEmpleado();
    }


    private void getEmpleado() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empleados.jl.serv.net.mx/CRUD/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<ListaEmpleados>> call = postService.getEmpleado(idEmpleado);

        call.enqueue(new Callback<List<ListaEmpleados>>() {
            @Override
            public void onResponse(Call<List<ListaEmpleados>> call, Response<List<ListaEmpleados>> response) {

                TextView txtID = (TextView)findViewById(R.id.txtID);
                TextView txtNombre = (TextView)findViewById(R.id.txtNombreE);
                TextView txtSueldo = (TextView)findViewById(R.id.txtSueldoE);

                for(ListaEmpleados empleado : response.body()) {

                    txtID.setText(empleado.getclave()+"");
                    txtNombre.setText(empleado.getNombre());
                    txtSueldo.setText(empleado.getSueldo());

                }

            }

            @Override
            public void onFailure(Call<List<ListaEmpleados>> call, Throwable t) {
            }
        });
    }

    private void borrarEmpleado() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empleados.jl.serv.net.mx/CRUD/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<ListaEmpleados> call = postService.borrarEmpleado(Integer.parseInt(idEmpleado));

        call.enqueue(new Callback<ListaEmpleados>() {
            @Override
            public void onResponse(Call<ListaEmpleados> call, Response<ListaEmpleados> response) {
                if(response.isSuccessful()) {
                    Toast  toast = Toast.makeText(getApplicationContext(),"Borrado correctamente", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(new Intent(VerEmpleadoActivity.this, ListaEmpleadosActivity.class));
                }
            }
            @Override
            public void onFailure(Call<ListaEmpleados> call, Throwable t) {

            }
        });
    }

}
