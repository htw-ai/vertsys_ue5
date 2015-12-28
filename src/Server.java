/**
 * Created by timol on 28.12.2015.
 */

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    private static Logger log = Logger.getInstance();

    public static void main (String args[] ){

        try{
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            ChatServerImpl cs = new ChatServerImpl();
            Naming.rebind("Chatserver", cs);
            System.out.println("ChatServer gestartet");

        } catch (RemoteException e) {
            log.error("Server - failed to initialize" + e.getLocalizedMessage());
        } catch (MalformedURLException e) {
            log.error("Server - failed to initialize" + e.getLocalizedMessage());
        }
    }



}
