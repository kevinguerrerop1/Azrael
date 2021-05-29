package cl.inacap.kevinguerrero.afinal.Remoto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cl.inacap.kevinguerrero.afinal.R;
import cz.msebera.android.httpclient.Header;

//import cz.msebera.android.httpclient.Header;

public class AgRem extends Fragment {
    public AgRem() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_ag_rem, container, false);
        final EditText rnombre=(EditText)vista.findViewById(R.id.rnombre);
        final EditText remail=(EditText)vista.findViewById(R.id.remail);
        final EditText rpassword=(EditText)vista.findViewById(R.id.rpassword);
        Button btnRemotoG=(Button)vista.findViewById(R.id.btnRemotoG);

        btnRemotoG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client= new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.add("nombre",rnombre.getText().toString());
                parametros.add("email",remail.getText().toString());
                parametros.add("password",rpassword.getText().toString());
                String url="http://kethasoft.cl/Control/agregar.php";
                final Context ctx=getActivity();


                client.post(url, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {

                            JSONObject obj = new JSONObject(new String(responseBody));

                            String respuesta = obj.getString("status");

                            if (statusCode==200 && respuesta.equals("")){

                                Toast.makeText(ctx, "Registro Creado", Toast.LENGTH_SHORT).show();

                            }else{

                                Toast.makeText(ctx, "Error al Crear el Registro", Toast.LENGTH_SHORT).show();

                            }

                            rnombre.setText("");
                            rnombre.findFocus();
                            remail.setText("");
                            rpassword.setText("");

                        }catch (JSONException e){
                            Toast.makeText(ctx, "Registro Creado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(ctx, "Registro Creado", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return vista;
    }
}
