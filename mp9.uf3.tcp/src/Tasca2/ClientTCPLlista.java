package Tasca2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCPLlista extends Thread{
    /* CLient TCP que ha endevinar un número pensat per SrvTcpAdivina_Obj.java */

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private boolean continueConnected;
    private Llista llista;

    private ClientTCPLlista(String hostname, int port, Llista llista) {
        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        continueConnected = true;
        this.llista = llista;
    }

    public void run() {
        while(continueConnected){
            try {
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(llista);
                oos.flush();
                this.llista = getRequest();
                break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        close(socket);
        }
    private Llista getRequest() {
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            llista = (Llista) ois.readObject();
            System.out.println("NUM. " + llista.getNom() + " --> " + llista.getNumberList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return llista;
    }

    private void close(Socket socket){
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(ClientTCPLlista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        String jugador, ipSrv;

        //Demanem la ip del servidor i nom del jugador
        Scanner sip = new Scanner(System.in);

        System.out.print("Ip del servidor: ");
        ipSrv = sip.next();
        System.out.print("Nom jugador: ");
        jugador = sip.next();

        System.out.println();

        List<Integer> integerArrayList = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            integerArrayList.add(((int) (Math.random() * 50))+1);
        }

        Llista llista = new Llista(jugador,integerArrayList);

        ClientTCPLlista clientTCPLlista = new ClientTCPLlista(ipSrv,2828, llista);
        clientTCPLlista.llista.setNom(jugador);
        clientTCPLlista.start();
    }
}
