import com.giaynhap.chatwebserver.object.ChatMessage;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

 
/**
 *
 * @author GiayNhap
 */

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {


    public void init(EndpointConfig config) {
    }

    public void destroy() {
    }

    public ChatMessage decode(String s) throws DecodeException {
       
        JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
        ChatMessage chatMessage = new ChatMessage();   
        chatMessage.setMessage(jsonObject.getString("message"));
        chatMessage.setType(jsonObject.getInt("type"));
        chatMessage.setUser(jsonObject.getInt("user"));
        chatMessage.setToUser(jsonObject.getString("session"));
        
        chatMessage.setMessage(chatMessage.getMessage().replace("&", "&amp;")                     
                                                        .replace("<", "&lt;")
                                                        .replace(">", "&gt;")
                                                        .replace("\"", "&quot;")
                                                        .replace("'", "&#x27;")
                                                        .replace("/", "&#x2F;")
        );
         chatMessage.setToUser(chatMessage.getToUser().replace("&", "&amp;")                     
                                                        .replace("<", "&lt;")
                                                        .replace(">", "&gt;")
                                                        .replace("\"", "&quot;")
                                                        .replace("'", "&#x27;")
                                                        .replace("/", "&#x2F;")
        );
     
        return chatMessage;
    }
 
    public boolean willDecode(String s) {
        return true;
    }
}