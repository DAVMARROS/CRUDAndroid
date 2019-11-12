package com.example.crudservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaEmpleadosActivity extends AppCompatActivity {

    ListView list;
    ArrayList<String> empleados = new ArrayList<>();
    ArrayList<String> empleadosClaves = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_empleados);

        FloatingActionButton fab = findViewById(R.id.fabGuardar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaEmpleadosActivity.this, NuevoEmpleadoActivity.class));
            }
        });

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,empleados);
        list = findViewById(R.id.list);

        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                String selectionID = empleadosClaves.get(position);

                VerEmpleadoActivity.idEmpleado=selectionID;

                startActivity(new Intent(ListaEmpleadosActivity.this, VerEmpleadoActivity.class));
            }
        });
        getEmpleados();
    }


    private void getEmpleados() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empleados.jl.serv.net.mx/CRUD/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<ListaEmpleados>> call = postService.getListaEmpleados();

        call.enqueue(new Callback<List<ListaEmpleados>>() {
            @Override
            public void onResponse(Call<List<ListaEmpleados>> call, Response<List<ListaEmpleados>> response) {
//                titles.add("Clave       Nombre");

                for(ListaEmpleados empleado : response.body()) {
//                  titles.add("    "+empleado.getclave()+"           "+empleado.getNombre());

                    empleadosClaves.add(empleado.getclave()+"");
                    empleados.add(empleado.getNombre());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListaEmpleados>> call, Throwable t) {
            }
        });
    }

}
