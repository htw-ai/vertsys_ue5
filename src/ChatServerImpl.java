import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by timol on 28.12.2015.
 */
public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

    private static Logger log = Logger.getInstance();
    private ArrayList<ChatClient> allClients;

    public ChatServerImpl() throws RemoteException{
        allClients = new ArrayList<ChatClient>();
        log.info("Server - Initialized");
    }

    @Override
    public synchronized boolean addClient(ChatClient objRef) throws RemoteException {
        String name = objRef.getName();
        for(Iterator<ChatClient> iter = allClients.iterator(); iter.hasNext();){
            ChatClient cc = iter.next();
            try {
                if (cc.getName().equals(name)) {
                    log.error("Server - Client already exists");
                    return false;
                }
            }
            catch(RemoteException exc){
                    log.error("Server - failed to add Client" + exc.getLocalizedMessage());
                    iter.remove();
                }
            }

        allClients.add(objRef);
        log.info("Server - Added Client: " + objRef.getName());
        return true;
    }

    @Override
    public synchronized void removeClient(ChatClient objRef) throws RemoteException {

        allClients.remove(objRef);
        log.info("Server - Removed Client: " + objRef.getName());
    }

    @Override
    public synchronized void sendMessage(String name, String msg) throws RemoteException {

        for(Iterator<ChatClient> iter = allClients.iterator(); iter.hasNext();){
            ChatClient cc = iter.next();
            try{
                cc.print(name + ": " + msg);
            }
            catch(RemoteException exc){
                log.error("Server - Failed to send Message, Name: " + name + " Message: " + msg );
                iter.remove();
            }
        }
    }
}
