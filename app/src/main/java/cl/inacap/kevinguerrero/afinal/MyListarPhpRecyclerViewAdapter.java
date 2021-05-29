package cl.inacap.kevinguerrero.afinal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import cl.inacap.kevinguerrero.afinal.Modelo.Usuarios;

import java.util.List;


public class MyListarPhpRecyclerViewAdapter extends RecyclerView.Adapter<MyListarPhpRecyclerViewAdapter.UsuariosViewHolder> {

    private final List<Usuarios> listausuarios;
    Context ctx;

    public MyListarPhpRecyclerViewAdapter(Context ctx, List<Usuarios> listausuarios) {
        this.ctx=ctx;
        this.listausuarios=listausuarios;
    }


    @Override
    public UsuariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_listarphp, parent, false);
        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UsuariosViewHolder holder, int position) {
        Usuarios usuarios=listausuarios.get(position);

        if(ctx!=null) {
            //Cargar imagen por url
            Glide.with(ctx).load(usuarios.getImagen()).into(holder.mi_imagen);
        }
        holder.mi_nombre.setText(usuarios.getNombre());
        holder.mi_email.setText(usuarios.getEmail());

    }

    @Override
    public int getItemCount() {
        return listausuarios.size();
    }

    public class UsuariosViewHolder extends RecyclerView.ViewHolder {
        TextView mi_nombre, mi_email;
        ImageView mi_imagen;

        public UsuariosViewHolder(View view) {
            super(view);
            mi_nombre=view.findViewById(R.id.mi_nombre);
            mi_email=view.findViewById(R.id.mi_email);
            mi_imagen=view.findViewById(R.id.mi_imagen);
        }

    }
}
