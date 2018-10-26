package com.example.jhordanvega.app3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhordanvega.app3.Adapters.MyAdapter;
import com.example.jhordanvega.app3.POJO.Usuario;
import com.example.jhordanvega.app3.Parsers.UsuarioJSONParser;
import com.example.jhordanvega.app3.Parsers.UsuarioXMLParser;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;
import java.util.List;

import static org.scribe.model.OAuthConstants.CONSUMER_KEY;
import static org.scribe.model.OAuthConstants.CONSUMER_SECRET;

public class MainActivity extends AppCompatActivity {

    TextView txtCargarDatos;
    Button btnCargarDatos;
    ProgressBar barraProgreso;
    // List<TareaAsincronica> listaTareas;
    List<Usuario> listaUsuarios;

    ListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCargarDatos = findViewById(R.id.idTxtCargarDatos);
        txtCargarDatos.setMovementMethod(new ScrollingMovementMethod());

        // btnCargarDatos = findViewById(R.id.idBtnCargarDatos);

        // barraProgreso = findViewById(R.id.idBarraProgreso);
        // Hacemos que antes de ejecutar la acción sea invisible el progressbar
        // barraProgreso.setVisibility(View.INVISIBLE);

        listView = findViewById(R.id.listview);

        if (isOnline()){
            // pedirDatos();
            // XML:
            // pedirDatos("http://maloschistes.com/maloschistes.com/jose/usuarios.xml");

            // JSON:
            pedirDatos("http://maloschistes.com/maloschistes.com/jose/webserviceI.php");

        }else{
            Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
        }


        // listaTareas = new ArrayList<>();


        /*
        // Evento de click desde Java
        btnCargarDatos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                // for (int i=0; i<=10; i++){
                    cargarDatos("Número " + i);
                }



                if (isOnline()){
                    // pedirDatos();
                    // XML:
                    // pedirDatos("http://maloschistes.com/maloschistes.com/jose/usuarios.xml");

                    // JSON:
                    pedirDatos("http://maloschistes.com/maloschistes.com/jose/webservice.php");

                }else{
                    Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                }


                //TareaAsincronica tareaAsin = new TareaAsincronica();
                // tareaAsin.execute();
                // tareaAsin.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });
        */

    }

    // public void cargarDatos(String dato){
    public void cargarDatos(){
        // txtCargarDatos.append(dato + "\n");

        /*
        if (listaUsuarios != null){

            for (Usuario usuario: listaUsuarios){
                txtCargarDatos.append(usuario.getNombre() + "\n");
            }
        }
        */

        adapter = new MyAdapter(getApplicationContext(), listaUsuarios);
        listView.setAdapter(adapter);

    }

    // Verficar la conectividad:
    public boolean isOnline(){
        ConnectivityManager administradorConeccion = (ConnectivityManager)getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = administradorConeccion.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }

    }

    // public void pedirDatos(){
    public void pedirDatos(String uri){
        TareaAsincronica tareaAsin = new TareaAsincronica();

        // EJECUTA LOS HILOS EN FORMA SERIAL
        // tareaAsin.execute();

        // EJECUTA LOS HILOS EN FORMA PARALELA
        // tareaAsin.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        tareaAsin.execute(uri);
    }

    // Creamos una tarea asíncorna para que se ejecute en 2do plano:
    private class TareaAsincronica extends AsyncTask<String, String, String>{

        // Siempre se ejecuta al inicio:
        @Override
        protected void onPreExecute() {
            super.onPreExecute(); // constructor.

            // cargarDatos("Inicio de carga");
            // barraProgreso.setVisibility(View.VISIBLE);

            /*
            if(listaTareas.size() == 0){
                // Cuando empiece a ejecutar la tarea asíncrona mostramos el progressbar
                barraProgreso.setVisibility(View.VISIBLE);
            }
            */



            // listaTareas.add(this); // le agregamos esta tarea (clase TareaAsincronica).

        }

        // Esto se ejecuta en 2do plano:
        @Override
        protected String doInBackground(String... params) {

            /*
            for (int i=0; i<=3; i++){
                publishProgress("Número:" + i);

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
            */

            String content = HttpManager.getData(params[0]);
            return content;


            // return "Terminamos";
        }

        // Se ejecuta después de la ejecución de la tarea asincrónica:
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result == null){
                Toast.makeText(MainActivity.this,"No se pudo conectar",Toast.LENGTH_SHORT).show();
                // barraProgreso.setVisibility(View.INVISIBLE);
                return;
            }else{
                // XML:
                // listaUsuarios = UsuarioXMLParser.parser(result);

                // JSON:
                listaUsuarios = UsuarioJSONParser.parser(result);

                // cargarDatos(result);
                cargarDatos();
                // barraProgreso.setVisibility(View.INVISIBLE);
            }



            // Cuando terminamos el hilo lo eliminamos de la lista.
            // listaTareas.remove(this);

            /*
            if(listaTareas.size() == 0){
                // Después de terminar de ejecutar la tarea Asíncrona ocultamos el progressbar
                barraProgreso.setVisibility(View.INVISIBLE);
            }
            */


        }



        @Override
        protected void onProgressUpdate(String... values) {
            // super.onProgressUpdate(values);
            // cargarDatos(values[0]);
        }


        // OAuth:
/*
        String restURL = "https://demo4-manga.herokuapp.com/api/v1/manga";
        String CONSUMER_KEY = "ck_f42c4951de6acf79cfa67cf47afcf33f376641da";
        String CONSUMER_SECRET = "cs_5c71761154acdc1f1cdca94b69dc311b0c79d64c";

        OAuthService service = new ServiceBuilder()
                .provider(WoocommerceApi.WooCommerceApi.class)
                .apiKey(CONSUMER_KEY) //Your Consumer key
                .apiSecret(CONSUMER_SECRET) //Your Consumer secret
                .scope("API.Public") //fixed
                .signatureType(SignatureType.QueryString)
                .build();
        OAuthRequest request = new OAuthRequest(Verb.GET, restURL);
        Token accessToken = new Token("", ""); //not required for context.io
        service.signRequest(accessToken, request);
        Response response = request.send();
        Log.d("OAuthTask",response.getBody());
*/




    }


}
