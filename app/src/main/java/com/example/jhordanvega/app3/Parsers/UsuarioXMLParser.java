package com.example.jhordanvega.app3.Parsers;

import com.example.jhordanvega.app3.POJO.Usuario;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class UsuarioXMLParser {

    public static List<Usuario> parser(String content){
        boolean inDataItemTag = false;
        String currentTagName = "";
        Usuario usuario = null;
        List<Usuario> listaUsuarios = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){ // Mientras haya algo que leer
                switch (eventType){
                    case XmlPullParser.START_TAG: // inicia la etiqueta
                        currentTagName = parser.getName();
                        if (currentTagName.equals(("usuario"))){
                            inDataItemTag = true;
                            usuario = new Usuario();
                            listaUsuarios.add(usuario);
                        }
                    break;
                    case XmlPullParser.END_TAG: // finaliza la etiqueta.
                        if (parser.getName().equals("usuario")){
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                    break;
                    case XmlPullParser.TEXT: // Contenido de la etiqueta.
                        if (inDataItemTag && usuario != null){
                            switch (currentTagName){
                                case "usuarioId":
                                    usuario.setUsuarioId(Integer.parseInt(parser.getText()));
                                    break;
                                case "nombre":
                                    usuario.setNombre(parser.getText());
                                    break;
                                case "twitter":
                                    usuario.setTwiter(parser.getText());
                                    break;

                            }
                        }
                    break;



                }
                eventType = parser.next();

            }
            return listaUsuarios;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
