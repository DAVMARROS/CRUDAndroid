package com.example.crudservice;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostService {

    String API_EMPLEADOS_ROUTE = "getEmpleados.htm";
    String API_EMPLEADO_ROUTE = "getEmpleado/{id}.htm";
    String API_PUT_EMPLEADO_ROUTE = "putEmpleado.htm";
    String API_POST_EMPLEADO_ROUTE = "postEmpleado.htm";
    String API_DELETE_EMPLEADO_ROUTE = "deleteEmpleado.htm";

    @GET(API_EMPLEADOS_ROUTE)
    Call<List<ListaEmpleados> > getListaEmpleados();

    @GET(API_EMPLEADO_ROUTE)
    Call<List<ListaEmpleados>> getEmpleado(@Path("id") String var);

    @POST(API_PUT_EMPLEADO_ROUTE)
    @FormUrlEncoded
    Call<ListaEmpleados> editarEmpleado(@Field("clave") long clave,
                                        @Field("nombre") String nombre,
                                        @Field("sueldo") String sueldo);

    @POST(API_POST_EMPLEADO_ROUTE)
    @FormUrlEncoded
    Call<ListaEmpleados> guardarEmpleado(@Field("nombre") String nombre,
                                         @Field("sueldo") String sueldo);


    @POST(API_DELETE_EMPLEADO_ROUTE)
    @FormUrlEncoded
    Call<ListaEmpleados> borrarEmpleado(@Field("clave") long clave);



}