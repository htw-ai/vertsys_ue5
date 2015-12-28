import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by timol on 28.12.2015.
 */
public class Client {

    private static Logger log = Logger.getInstance();

    private static String[] getMessage() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        String[] message = new String[2];

        System.out.println("Enter Exit if you want to quit");
        System.out.println("Enter Name of the Message: ");
        try {
            message[0] = br.readLine();
            if (!message[0].equals("Exit")) {
                System.out.println("Enter message: ");
                message[1] = br.readLine();
            }
        } catch (IOException e) {
            log.error("Client - Failed to Read Input " + e.getLocalizedMessage());
        }
        return message;

    }

    public static void main (String args[] ){

        if(args.length == 0){
            log.error("Client - No Arguments found - Expected: Name of the Server and Name of the Client");
        }
        else {
            try {
                ChatClientImpl cc = new ChatClientImpl(args[1]);
                ChatServer cs = (ChatServer) Naming.lookup("rmi://" + args[0] + "/Chatserver");
                cs.addClient(cc);
                log.info("Client: " + cc.getName() + " Initialized");
                String[] message = new String[2];
                do {
                    message = getMessage();
                    if (!message[0].equals("Exit")) {
                        cs.sendMessage(message[0], message[1]);
                    }

                } while (!message[0].equals("Exit"));
                cs.removeClient(cc);


            } catch (RemoteException e) {
                log.error("Client - failed to initialize" + e.getLocalizedMessage());
            } catch (MalformedURLException e) {
                log.error("Client - failed to initialize" + e.getLocalizedMessage());
            } catch (NotBoundException e) {
                log.error("Client - failed to initialize" + e.getLocalizedMessage());
            }

        }
    }






}
