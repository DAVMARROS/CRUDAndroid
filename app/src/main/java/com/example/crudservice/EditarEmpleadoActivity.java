package com.example.crudservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.reactivestreams.Subscriber;

import java.sql.SQLOutput;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class EditarEmpleadoActivity extends AppCompatActivity {

    public static String idEmpleado="1";
    private PostService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        FloatingActionButton fab = findViewById(R.id.fabEditar);
        FloatingActionButton fabRegresar = findViewById(R.id.fabRegresarE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editEmpleado();
            }
        });

        fabRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtID = (TextView)findViewById(R.id.txtID);
                VerEmpleadoActivity.idEmpleado=txtID.getText().toString();
                startActivity(new Intent(EditarEmpleadoActivity.this, VerEmpleadoActivity.class));
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
                EditText txtNombre = (EditText )findViewById(R.id.txtNombreE);
                EditText  txtSueldo = (EditText )findViewById(R.id.txtSueldoE);

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

    private void editEmpleado() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empleados.jl.serv.net.mx/CRUD/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);

        TextView txtID = (TextView)findViewById(R.id.txtID);
        EditText txtNombre = (EditText )findViewById(R.id.txtNombreE);
        EditText txtSueldo = (EditText )findViewById(R.id.txtSueldoE);


        Call<ListaEmpleados> call = postService.editarEmpleado(Integer.parseInt(txtID.getText().toString()),txtNombre.getText().toString(),txtSueldo.getText().toString());

        call.enqueue(new Callback<ListaEmpleados>() {
            @Override
            public void onResponse(Call<ListaEmpleados> call, Response<ListaEmpleados> response) {

                if(response.isSuccessful()) {
                    Toast  toast = Toast.makeText(getApplicationContext(),"Actualizado correctamente", Toast.LENGTH_SHORT);
                    toast.show();

                    TextView txtID = (TextView)findViewById(R.id.txtID);
                    VerEmpleadoActivity.idEmpleado=txtID.getText().toString();
                    startActivity(new Intent(EditarEmpleadoActivity.this, VerEmpleadoActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ListaEmpleados> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }



}
