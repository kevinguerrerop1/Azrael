package cl.inacap.kevinguerrero.afinal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.kevinguerrero.afinal.BD.BD;
import cl.inacap.kevinguerrero.afinal.Local.AgLocal;
import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity{


    BD bd= new BD(this);

    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button  btnlogin=(Button)findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager coneccion=(ConnectivityManager)ctx.getSystemService(Service.CONNECTIVITY_SERVICE);
                if (coneccion!=null){
                    NetworkInfo NetInfo=coneccion.getActiveNetworkInfo();
                    if (NetInfo!=null){
                        if (NetInfo.getState()== NetworkInfo.State.CONNECTED){
                            Toast.makeText(ctx, "Base de datos Remota", Toast.LENGTH_LONG).show();
                            final EditText email=(EditText)findViewById(R.id.email);
                            final EditText password=(EditText)findViewById(R.id.pass);

                            AsyncHttpClient cliente= new AsyncHttpClient();
                            RequestParams parametros= new RequestParams();
                            parametros.add("email",email.getText().toString());
                            parametros.add("password",password.getText().toString());
                            String url="https://kethasoft.cl/Control/login.php";
                            cliente.post(url, parametros, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    /*if (statusCode==200){
                                        JSONObject objeto= new JSONObject(new String(responseBody));
                                        String usuario=objeto.getString("login");
                                        if (!TextUtils.isEmpty(usuario)){
                                            startActivity(new Intent(Login.this,MainActivity.class));
                                        }else{
                                            Toast.makeText(Login.this, "Usuario o Password Incorecto", Toast.LENGTH_SHORT).show();

                                        }
                                    }*/
                                    String enteredUsername = email.getText().toString();

                                    String enteredPassword = password.getText().toString();

                                    if(enteredUsername.equals("") || enteredPassword.equals("")){

                                        Toast.makeText(Login.this, "Email y/o Password Incorrecto", Toast.LENGTH_LONG).show();

                                        return;

                                    }
                                    if(enteredUsername.length() <= 1 || enteredPassword.length() <= 1){

                                        Toast.makeText(Login.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
                                        Intent inte = new Intent(Login.this, MainActivity.class);
                                        startActivity(inte);

                                        return;

                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(Login.this, "Error de Conexión", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(ctx, "Sin Conexión", Toast.LENGTH_LONG).show();
                            String resultado;

                            EditText email=(EditText)findViewById(R.id.email);
                            EditText pass=(EditText)findViewById(R.id.pass);

                            bd.abrir();

                            resultado = bd.verificarusuario(email.getText().toString(),pass.getText().toString());

                            bd.cerrar();

                            if (resultado.equals("")){

                                Toast.makeText(Login.this, "Usuario O Password Incorrecto", Toast.LENGTH_SHORT).show();

                                email.setText("");
                                email.findFocus();

                                pass.setText("");

                            }else{

                                Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                Intent ver= new Intent(Login.this,MainActivity.class);
                                startActivity(ver);

                            }
                        }
                    }else{
                        Toast.makeText(ctx, "Conexion Local ", Toast.LENGTH_LONG).show();
                        String resultado;

                        EditText email=(EditText)findViewById(R.id.email);
                        EditText pass=(EditText)findViewById(R.id.pass);

                        bd.abrir();

                        resultado = bd.verificarusuario(email.getText().toString(),pass.getText().toString());

                        bd.cerrar();

                        if (resultado.equals("")){

                            Toast.makeText(Login.this, "Usuario O Password Incorrecto", Toast.LENGTH_SHORT).show();

                            email.setText("");
                            email.findFocus();

                            pass.setText("");

                        }else{

                            Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            Intent ver= new Intent(Login.this,MainActivity.class);
                            startActivity(ver);
                        }
                    }
                }


            }
        });
    }
}

