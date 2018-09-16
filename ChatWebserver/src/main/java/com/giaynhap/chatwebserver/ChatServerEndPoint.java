 

import com.giaynhap.chatwebserver.core.sSystem;
import com.giaynhap.chatwebserver.object.ChatMessage;
import com.sun.media.jfxmedia.logging.Logger;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
 
/**
 *
 * @author GiayNhap
 */
 
@ServerEndpoint(value="/chat", 
    configurator=ChatServerEndPointConfigurator.class,
    encoders={ChatMessageEncoder.class},
    decoders={ChatMessageDecoder.class}
)
public class ChatServerEndPoint {
   
  static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    @OnMessage
    public void onMessage(ChatMessage message, Session userSession) {
           message.setSession(userSession);
        
           
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
           
            message.setStrDate(dateFormat.format(date));
            message.setTimeSpam(date.getTime() );
            sSystem.instance().add(message);
            
         
    }
    @OnOpen
    public void onOpen(Session session) {
       System.out.println(" ========== User Connect =============" );
       peers.add(session);
    }
     @OnClose
    public void onClose(Session session) throws    EncodeException {
        System.out.print("exot");
        peers.remove(session);
       
        
    }
}