
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GiayNhap
 */
public class ProcessServer implements Runnable {
    Thread thread;
    boolean isRunnig = false;
     Server server;
    public ProcessServer()
    {
        thread = new Thread(this,"ProcessChatServer");
          
    }
    public void Start()
    {
        isRunnig = true;
        server =  new   Server("localhost", 24024, "/", ChatServerEndPoint.class);
        
        try {
            server.start();
        } catch ( DeploymentException e) {
            throw new RuntimeException(e);
        }
        thread.start();
        
    }
    @Override
    public void run()
    {
        while(isRunnig)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcessServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void stop()
    {
        server.stop();
        isRunnig = false;
    }
            
}
