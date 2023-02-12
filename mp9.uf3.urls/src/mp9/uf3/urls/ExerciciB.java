package mp9.uf3.urls;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ExerciciB {

    public static void respondreFormulari(URL url, String string, boolean resposta) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());

        if(resposta){
            writer.write("entry.835030737="+string+"&entry.1616686619=Si");
        }else {
            writer.write("entry.835030737="+string+"&entry.1616686619=No");
        }

        writer.flush();
        writer.close();
        httpURLConnection.getInputStream();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introdueix l'enllaç al formulari: ");
        String enllaç = scanner.nextLine();
        System.out.println();

        System.out.println("Introdueix el teu nom: ");
        String nom = scanner.nextLine();
        System.out.println();

        System.out.println("Introdueix si o no: ");
        String resposta = scanner.nextLine();
        System.out.println();

        boolean afirmatiu = resposta.equals("si") || resposta.equals("Si") || resposta.equals("sI") || resposta.equals("SI");
        ExerciciB.respondreFormulari(new URL(enllaç),nom,afirmatiu);
    }
}