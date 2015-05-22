/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manga4Read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author tenshi
 */
public class generador {
    private static String codigoPag;
    public generador(){
    }
    public void generarManga(int numeroPag, String nombreManga, String enlace,int ser){
        /**
         * Modelo de enlace:
         * http://submanga.com/c/242184
         */
        String temp2;
        database.insertar("delete from temporal");
        for (int i=1;(numeroPag+1)>i;i++){
            String tempNombre=nombreManga;
            tempNombre=tempNombre.replaceAll(" ","-");
            tempNombre=tempNombre.replaceFirst("---.*","");
            String file;
            if(i<10){
                file ="Descargas/"+nombreManga+"/"+tempNombre+"-Pg00"+(i)+".jpg";
            }else {
                if(i<100){
                    file ="Descargas/"+nombreManga+"/"+tempNombre+"-Pg0"+(i)+".jpg";
                }else{
                     file ="Descargas/"+nombreManga+"/"+tempNombre+"-Pg"+(i)+".jpg";  
                }
            }
//            System.out.println(ser);
            if (ser==0){
                temp2=enlace+i+".jpg";
            }else{
                if(i<100){
                    if(i<10){
                        temp2=enlace+"00"+i+".jpg";
                    }else{
                        temp2=enlace+"0"+i+".jpg";
                    }
                }else {
                    temp2=enlace+i+".jpg";
                } 
            }
            
            database.insertar("insert into temporal values ("+i+", '"+temp2+"', '"+file+"')");
        }
    }
    
    public int infoSubmanga(String enlacePag){
        /**
         *Entrada: String modo http://submanga.com/Fairy_Tail/428/242298 
         *                     http://submanga.com/c/242298
         *Salida: String [3]
         * Nombre Manga
         * http://submanga.com/c/242298
         * Numero de paginas
         * http://omg.submanga.com/pages/3/242/242298d70/
         * .jpg
         */
        String urlCap = enlacePag;
        urlCap=urlCap.replaceFirst(".*/submanga.*/.*/.*/", "http://submanga.com/c/");
        System.out.println(urlCap);
        codigoPag=OCodigo(urlCap);
        String imagenes=codigoPag;
        if (codigoPag.equals("")){
            return 1;
        }
        imagenes=imagenes.replaceFirst(".*http://omg","http://omg");
        imagenes=imagenes.replaceFirst(".*http://img","http://img");
        imagenes=imagenes.replaceFirst("/[0-9]+.jpg\"/.*","/");
        //System.out.println(generarNombre()+" "+urlCap+" "+contarPAginas(32, 16, imagenes, ".jpg"));
        String numero = Integer.toString(contarPAginas(32, 16, imagenes, ".jpg",0));
        String nombre=generarNombre().replaceFirst(" -.*", "");
        database.insertar("insert into cola values ('"+nombre+"','"+urlCap+"',"+numero+",'Disponible', '"+imagenes+"','.jpg')");
        return 0;
    }
    
    public int infoFakku(String enlacePag){
        /**
         * Modelo de enlace
         * https://www.fakku.net/manga/case-file-of-the-arata-osteopathic-clinic-english
         */
        String urlCap = enlacePag;
        urlCap=enlacePag+"/read";
        System.out.println(urlCap);
        codigoPag=OCodigo(urlCap);
        String imagenes=codigoPag;
        if (codigoPag.equals("")){
            return 1;
        }
        imagenes=imagenes.replaceFirst(".*return '","https:");
        imagenes=imagenes.replaceFirst("/images/'.*","/images/");
        //System.out.println(generarNombre()+" "+imagenes+" "+urlCap+" "+contarPAginas(32, 16, imagenes, ".jpg",0));
        String numero = Integer.toString(contarPAginas(32, 16, imagenes, ".jpg",1));
        String nombre=generarNombre().replaceFirst("Read ", "");
        nombre=nombre.replaceFirst(" Hentai.*", "");
        nombre=nombre.replaceAll("'", "");
        database.insertar("insert into cola values ('"+nombre+"','"+urlCap+"',"+numero+",'Disponible', '"+imagenes+"','.jpg')");
        return 0;
        
    }
    
    public int infoHaki(String enlacePag){
        /**
         * Modelo de enlace
         * http://hakihome.com/Original-work/Sensei-umm.html
         * http://hakihome.com/Original-work/Sensei-umm.html/Read.html
         */
        String urlCap = enlacePag;
        urlCap=enlacePag+"/Read.html";
        System.out.println(urlCap);
        codigoPag=OCodigo(urlCap);
        String imagenes=codigoPag;
        if (codigoPag.equals("")){
            return 1;
        }
        imagenes=imagenes.replaceFirst(".*<img src=\"","");
        imagenes=imagenes.replaceFirst("[0-9]+1\\..*","");
        imagenes=imagenes.replaceAll(" ","%20");
        String nombre=generarNombre().replaceFirst("-Read.*", "");
        nombre=nombre.replaceFirst("-Hentai.*", "");
        //System.out.println(generarNombre()+" "+imagenes+" "+urlCap+" "+contarPAginas(32, 16, imagenes, ".jpg",0));
        String numero = Integer.toString(contarPAginas(32, 16, imagenes, ".jpg",1));
        database.insertar("insert into cola values ('"+nombre+"','"+urlCap+"',"+numero+",'Disponible', '"+imagenes+"','.jpg')");
        return 0;
        
    }
    
    public String generarNombre(){
        /**
         * Devuelve el titulo del manga que se encuentra en el titulo de la pagina
         * Se elimina los caracteres que no son admintidos para nombres en windows \ / ? : * " > < |
         * Se eliminan espacios fianles 
         */
        String nombreManga=codigoPag;
        nombreManga=nombreManga.replaceFirst(".*<title>","");
        nombreManga=nombreManga.replaceFirst("</title>.*","");
        nombreManga=nombreManga.replaceAll("[^A-Za-z0-9 -]"," ");
        nombreManga=nombreManga.replaceAll(" +"," ");
        nombreManga=nombreManga.replaceFirst(" $","");
        System.out.println(nombreManga);       
        return nombreManga;
    }
    
    public String OCodigo (String enlace){
        String cod="";
        try {
            java.net.URL url2 = new java.net.URL(enlace);
            java.net.URLConnection con = url2.openConnection();
            InputStream s = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s));
            String line;
            while((line = bufferedReader.readLine())!=null){
                    //cod=String.format("%s \n%s",cod, line);
                    cod=String.format("%s %s",cod, line);
            }
        }catch (java.net.MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion: No existe conexion a internet o es muy inestable");
            return "";
        } 
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexion: No existe conexion a internet o es muy inestable");
            return "";
        }
        //System.out.println(cod);
        return cod;
    }
    
    public boolean comprobar (String imagen){
        try {
            System.out.println(imagen);
            URL u = new URL (imagen);
            HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
            huc.setRequestMethod ("HEAD");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect () ;
            return (huc.getResponseCode()==HttpURLConnection.HTTP_OK);
        } catch (MalformedURLException ex) {
            //JOptionPane.showMessageDialog(null, "No existe conexion a internet");
            Logger.getLogger(generador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            //JOptionPane.showMessageDialog(null, "No existe conexion a internet");
            Logger.getLogger(generador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public int contarPAginas(int entrada, int salto, String url1,String url2,int ser){
        //System.out.println(entrada+" "+salto);
        /**
         * Manejo de servidores
         * 0 -> Submanga 
         * 1 -> Fakku, Hakihome 
         */
//        System.out.println(entrada+" "+salto);//Save to check numbers
        String urlFinal;
        int numero=entrada;
        if (ser==0){
            urlFinal=url1+entrada+url2;
        }else{
            if(entrada<100){
                if(entrada<10){
                urlFinal=url1+"00"+entrada+url2;
                }else{
                    urlFinal=url1+"0"+entrada+url2;
                }
            }else {
                urlFinal=url1+entrada+url2;
            } 
        }
        if ((salto > 16)&&(comprobar(urlFinal))){
            if(salto>500){
                return 0;
            }else{
                numero = contarPAginas(entrada*2, salto*2, url1, url2, ser);
            }
        }else{
            if (ser==0){
                urlFinal=url1+(entrada-salto)+url2;
            }else{
                if(entrada-salto<100){
                    if(entrada-salto<10){
                    urlFinal=url1+"00"+(entrada-salto)+url2;
                    }else{
                        urlFinal=url1+"0"+(entrada-salto)+url2;
                    }
                }else {
                    urlFinal=url1+(entrada-salto)+url2;
                } 
            }
            if (comprobar(urlFinal)){
                //System.out.println("Buscar arriba");
                if(salto==1){return (entrada-1);}
                numero=contarPAginas(entrada, salto/2, url1, url2,ser);
                
            }else{
                //System.out.println("Buscar abajo");
                if(salto==1){return (entrada-2);}
                numero=contarPAginas((entrada-salto), salto/2, url1, url2,ser);
            }
        }
        return numero;
    }

}
