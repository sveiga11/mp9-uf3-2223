package mp9.uf3.urls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ExerciciA {

    private static void printContent(URL url, String etiqueta){
        InputStream in;
        BufferedReader br;


        try {
            in = url.openStream();
            InputStreamReader inr = new InputStreamReader(in);

            br = new BufferedReader(inr);

            String linea = br.readLine();

            while (linea != null){
                if (linea.contains(etiqueta)){
                    System.out.println(linea);
                }
                linea = br.readLine();
            }
            System.out.println();
        } catch (IOException ex) {
            Logger.getLogger(ExerciciA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        String enllaç = args[0];
        String etiqueta = args[1];

        try {
            ExerciciA.printContent(new URL(enllaç), etiqueta);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
