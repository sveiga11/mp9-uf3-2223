import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class ParaulesMulticastServer {

        MulticastSocket socket;
        InetAddress multicastIP;

        int port;
        boolean continueRunning = true;

        static String[] paraules;

        public int numeroAleatori(){
            int paraulaAleatoria = (int)(Math.random() * 11);
            return paraulaAleatoria;
        }

        public ParaulesMulticastServer(int portValue, String strIp) throws IOException {
            socket = new MulticastSocket(portValue);
            multicastIP = InetAddress.getByName(strIp);
            port = portValue;
        }

        public void runServer() throws IOException{
            DatagramPacket packet;
            byte [] sendingData;

            while(continueRunning){
                sendingData = paraules[numeroAleatori()].getBytes(StandardCharsets.UTF_8);
                packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
                socket.send(packet);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.getMessage();
                }
            }
            socket.close();
        }

        public static void main(String[] args) throws IOException {
            //Canvieu la X.X per un número per formar un IP.
            //Que no sigui la mateixa que la d'un altre company
            paraules = new String[]{
                    "Paracaigudisme",
                    "Bungee jumping",
                    "Salt Base",
                    "Escalada",
                    "Parkour",
                    "Parapent",
                    "Alpinisme",
                    "Rapel",
                    "Heli-Ski",
                    "Busseig",
                    "Motocros"
            };

            ParaulesMulticastServer paraulesMulticastServer = new ParaulesMulticastServer(5557, "224.0.11.111");
            paraulesMulticastServer.runServer();
            System.out.println("Parat!");

        }
   }

