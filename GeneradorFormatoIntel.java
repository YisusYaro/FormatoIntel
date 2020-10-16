import java.util.StringTokenizer;
import java.util.Formatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class GeneradorFormatoIntel{

    private int numeroDatos;
    private final int tamanoDatosBytes = 16;
    private ArrayList<String> registros = new ArrayList<String>();

    public void generar(ArrayList<String> lineas){

        this.numeroDatos=lineas.size();
        String linea;
        String registro;
        String dirHexa;
        String dirInicialHexa;
        int dir;
        int dirInicial=0;
        int dirFinal=0;
        int dirActual=0;


        for (int i=0; i<lineas.size(); i+=this.tamanoDatosBytes){
            registro=":"+formateoTamano(Integer.toHexString(this.tamanoDatosBytes));
    
            for(int j=i; j<(i+this.tamanoDatosBytes); j++){
                if(j<this.numeroDatos){
                    StringTokenizer tokens=new StringTokenizer(lineas.get(j)," ");
                    
                    if(j==i){
                            dirInicialHexa=formateoDireccion(tokens.nextToken());
                            dirInicial=Integer.parseInt(dirInicialHexa,16);
                            dirActual = dirInicial;
                            registro+=dirInicialHexa+"00";
                    }
                    else{

                        dirActual++;
                        dirHexa = tokens.nextToken();
                        dir = Integer.parseInt(dirHexa,16);
                        if(dir!=dirActual){
                            JOptionPane.showMessageDialog(null, "Revisar: No existe continuidad de direcciones: "+dirHexa);
                        }
                        dirFinal=dir;
                    }

                    registro+=tokens.nextToken();
                }
                else{
                    registro+="00";
                }
            }
            registro+=checksum(registro.substring(1,registro.length()));
            registros.add(registro);
        }
        registros.add(":00000001FF");
        Archivo.escribir(registros);
        JOptionPane.showMessageDialog(null, "Archivo.hex ha sido creado.");
    }

    private int finDeDatos(int fin){
        if(fin+this.tamanoDatosBytes<this.numeroDatos)
            return fin+this.tamanoDatosBytes;
        else
            return this.numeroDatos;
    }

    private String formateoDireccion(String direccion){
        switch(direccion.length()){
            case  1:
                direccion="000"+direccion;
                break;
            case  2:
                direccion="00"+direccion;
                break;
            case  3:
                direccion="0"+direccion;
                break;
            case 4:
                direccion=direccion;
                break;
            default:
                JOptionPane.showMessageDialog(null, direccion.length()+"Error: Numero de digitos de direccion erroneo");
                break;
                
        }
        return direccion;
    }

    private String formateoTamano(String tamano){
        switch(tamano.length()){
            case  1:
                tamano="0"+tamano;
                break;
            case 2:
                tamano=tamano;
                break;
            default:
                JOptionPane.showMessageDialog(null,"Error: Numero de digitos de tamanio de bytes erroneo: "+tamano.length());
                break;

        }
        return tamano;
    }

    private String checksum(String registro){
        int suma=0;
        for(int i = 0; i < registro.length() ; i+=2){
            suma+=Integer.valueOf(registro.substring(i, i+2),16);
        }
        suma = ((~suma) +1);
        String checksum = Integer.toHexString(suma);
        return checksum.substring(checksum.length()-2, checksum.length()).toUpperCase();
            
    }





}