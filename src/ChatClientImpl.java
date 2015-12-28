import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by timol on 28.12.2015.
 */
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {

    private String name;

    public ChatClientImpl(String n) throws RemoteException{
        name = n;
    }
    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void print(String msg) throws RemoteException {
        System.out.println(name + " got a message: " +msg);

    }

}
