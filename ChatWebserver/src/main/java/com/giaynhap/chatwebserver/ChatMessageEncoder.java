 /**
 *
 * @author GiayNhap
 */
import com.giaynhap.chatwebserver.object.ChatMessage;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
 
public class ChatMessageEncoder implements Encoder.Text<String> {
 
    public void init(EndpointConfig config) {
        
    }
 
    public void destroy() {
    }
 
    public String encode(String chatMessage) {
        
        return chatMessage;
    }
}