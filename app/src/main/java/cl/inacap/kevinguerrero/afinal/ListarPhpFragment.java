package cl.inacap.kevinguerrero.afinal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.kevinguerrero.afinal.Modelo.Usuarios;
import cz.msebera.android.httpclient.Header;


public class ListarPhpFragment extends Fragment {

    List<Usuarios> listausuarios;
    RecyclerView recyclerView;
    Context ctx=getActivity();
    MyListarPhpRecyclerViewAdapter miAdaptador;

    public ListarPhpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargardatos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listarphp_list, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.listarecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        listausuarios=new ArrayList<>();
        return view;
    }

    public void cargardatos(){

        AsyncHttpClient cliente=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        String uri= "http://kethasoft.cl/Control/listardatos.php";
        cliente.post(uri, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray array = new JSONArray(new String(responseBody));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject usuarios = array.getJSONObject(i);


                        listausuarios.add(new Usuarios(usuarios.getInt("id"),
                                usuarios.getString("nombre"),
                                usuarios.getString("email"),
                                usuarios.getString("imagen")));
                    }
                    miAdaptador = new MyListarPhpRecyclerViewAdapter(getActivity(), listausuarios);
                    recyclerView.setAdapter(miAdaptador);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listausuarios = null;
    }
}
