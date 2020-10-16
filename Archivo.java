import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Archivo{

    public static ArrayList<String> leer() {
        ArrayList<String> lineas = new ArrayList<String>();
        BufferedReader b = null;
        String linea;
        try{
                    FileReader f = new FileReader("datos.txt");
                    b = new BufferedReader(f);
                    while((linea=b.readLine())!=null)
                        lineas.add(linea);
                    b.close();
                    f.close();
        }catch(Exception e){
                System.out.println("No se encontro el archivo");
        }
        return lineas;
    }

    public static void escribir(ArrayList<String> registros) {
        PrintWriter p = null;
        try{
                    FileWriter f = new FileWriter("datos.hex");
                    p = new PrintWriter(f);

                    for(String registo: registros)
                        p.println(registo);
                    p.close();
                    f.close();
        }catch(Exception e){
                System.out.println("No se encontro el archivo");
        }
    }

}