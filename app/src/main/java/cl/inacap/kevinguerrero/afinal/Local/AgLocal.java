package cl.inacap.kevinguerrero.afinal.Local;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cl.inacap.kevinguerrero.afinal.BD.BD;
import cl.inacap.kevinguerrero.afinal.R;

public class AgLocal extends Fragment {
    BD db;

    Context ctx;

    public AgLocal() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mivista= inflater.inflate(R.layout.fragment_ag_local, container, false);

        ctx=this.getActivity();

        db= new BD(ctx);

        final EditText txtnombre=(EditText)mivista.findViewById(R.id.txtnombre);
        final EditText txtemail=(EditText)mivista.findViewById(R.id.txtemail);
        final EditText txtpassword=(EditText)mivista.findViewById(R.id.txtpassword);
        Button btnagregar1=(Button)mivista.findViewById(R.id.btnagregar1);

        btnagregar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.abrir();
                db.insertarReg(txtnombre.getText().toString(),txtemail.getText().toString(),txtpassword.getText().toString());
                db.cerrar();
                txtnombre.setText("");
                txtemail.setText("");
                txtpassword.setText("");
                txtnombre.findFocus();
                Toast.makeText(ctx, "Registro Creado", Toast.LENGTH_SHORT).show();
            }
        });
        return mivista;
    }

}
