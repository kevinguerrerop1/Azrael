package cl.inacap.kevinguerrero.afinal.Local;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.inacap.kevinguerrero.afinal.BD.BD;
import cl.inacap.kevinguerrero.afinal.R;

public class ListLocal extends Fragment {
    BD db;

    Context ctx;

    public ListLocal() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mivista= inflater.inflate(R.layout.fragment_list_local, container, false);

        TextView txtmostrar=(TextView)mivista.findViewById(R.id.txtmostrar);

        ctx=this.getActivity();

        db= new BD(ctx);

        db.abrir();
        txtmostrar.setText(db.leer());
        db.cerrar();

        return mivista;
    }

}
