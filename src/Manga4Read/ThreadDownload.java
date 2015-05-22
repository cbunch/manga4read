package Manga4Read;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class ThreadDownload implements Runnable{
String enlace;
String nombre;

public ThreadDownload(String url,String archivo){
    enlace = url;
    nombre = archivo;
}

@Override
public void run(){
    try {
             URLConnection conn = new URL(enlace).openConnection();
             conn.connect();
             System.out.println("Descargando: "+nombre+" Tamaño: " + conn.getContentLength()+" bytes");
            // Abre los streams
             InputStream in = conn.getInputStream();
             OutputStream out = new FileOutputStream(nombre);
             int b = 0;
             // este ciclo lee de a un byte por vez y los escribe en un archivo
             // el -1 significa que se llego al final
             while (b != -1) {
                 b = in.read();
                 if (b != -1)
                     out.write(b);
             }
             // Cierra los streams
             out.close();
             in.close();
         } catch (MalformedURLException ex) {
//             Logger.getLogger(descargar.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("Error al descargar la imagen: 100");
         } catch (IOException ex) {
//             Logger.getLogger(descargar.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("Error al descargar la imagen: 101");
             
         }
}

}