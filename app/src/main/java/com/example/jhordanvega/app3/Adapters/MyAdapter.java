package com.example.jhordanvega.app3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jhordanvega.app3.POJO.Usuario;
import com.example.jhordanvega.app3.R;
import com.example.jhordanvega.app3.VisorImagen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    List<Usuario> usuarioList;
    // Sirve para ir colocando los elementos de la vista.
    LayoutInflater layoutInflater;
    Context context;

    // Constructor
    public MyAdapter(Context context, List<Usuario> usuarioList){
        this.context = context;
        this.usuarioList = usuarioList;
        layoutInflater = LayoutInflater.from(this.context);
    }

    // Nos regresa la cantidad de contenido que tenemos:
    @Override
    public int getCount() {
        return usuarioList.size();
    }

    // Regresa un item
    @Override
    public Usuario getItem(int position) {
        return usuarioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Acá se forma la vista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Usuario usuario = getItem(position);
        viewHolder.title.setText(usuario.getNombre());
        Picasso.get().load(usuarioList.get(position).getTwiter()).into(viewHolder.imageView);

        // Imagen detalle
        viewHolder.imageView.setTag(position);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent visorImagen = new Intent(context, VisorImagen.class);
                visorImagen.putExtra("IMG", usuarioList.get((Integer)v.getTag()).getTwiter());
                context.startActivity(visorImagen);
            }
        });


        return convertView;
    }

    public class ViewHolder{
        TextView title;
        ImageView imageView;

        public ViewHolder(View item){
            title = item.findViewById(R.id.title);
            imageView = item.findViewById(R.id.imageview);
        }

    }
}
