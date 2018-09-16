
import java.util.Map;
import java.util.Scanner;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.glassfish.tyrus.server.Server;
 


/**
 *
 * @author GiayNsshap
 */
public class Main {
     
    public static void main(String[] args) {
       ProcessServer ProcessServer = new ProcessServer();
       ProcessServer.Start();
    }
}
