package com.example.jhordanvega.app3.Parsers;

import com.example.jhordanvega.app3.POJO.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuarioJSONParser {

    public static List<Usuario> parser(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Usuario> listaUsuarios = new ArrayList<>();

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Usuario usuario = new Usuario();

                usuario.setUsuarioId(jsonObject.getInt("usuarioid"));
                usuario.setNombre(jsonObject.getString("nombre"));
                usuario.setTwiter(jsonObject.getString("twitter"));

                listaUsuarios.add(usuario);
            }

            return listaUsuarios;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}
