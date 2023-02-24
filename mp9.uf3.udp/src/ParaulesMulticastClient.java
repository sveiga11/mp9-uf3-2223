import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class ParaulesMulticastClient {

    /* Client afegit al grup multicast ParaulesMulticastServer.java que representa unes paraules */

    HashMap<String, Integer> map;

    private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    NetworkInterface netIf;
    InetSocketAddress group;


    public ParaulesMulticastClient(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        //netIf = NetworkInterface.getByName("enp1s0");
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
    }

    public void runClient() throws IOException{
        DatagramPacket packet;
        byte [] receivedData = new byte[15];
        map = new HashMap<>();

        socket.joinGroup(group,netIf);
        System.out.printf("Connectat a %s:%d%n",group.getAddress(),group.getPort());

        while(continueRunning){
            packet = new DatagramPacket(receivedData, receivedData.length);
            socket.setSoTimeout(5000);
            try{
                socket.receive(packet);
                continueRunning = getData(packet.getData(), packet.getLength());
            }catch(SocketTimeoutException e){
                System.out.println("S'ha perdut la connexió amb el servidor.");
                continueRunning = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socket.leaveGroup(group,netIf);
        socket.close();
    }

    protected  boolean getData(byte[] data, int lenght) {

        String paraulaAleatoria = new String(data,0,lenght);

        if (!map.containsKey(paraulaAleatoria)) {
            map.put(paraulaAleatoria, 1);
            System.out.println(paraulaAleatoria + ": " + map.get(paraulaAleatoria));

        } else {
            map.put(paraulaAleatoria, map.get(paraulaAleatoria) + 1 );
            System.out.println(paraulaAleatoria + ": " + map.get(paraulaAleatoria));
        }
        return  true;
    }

    public static void main(String[] args) throws IOException {
        ParaulesMulticastClient paraulesMulticastClient = new ParaulesMulticastClient(5557, "224.0.11.111");
        paraulesMulticastClient.runClient();
        System.out.println("Parat!");
    }

}
