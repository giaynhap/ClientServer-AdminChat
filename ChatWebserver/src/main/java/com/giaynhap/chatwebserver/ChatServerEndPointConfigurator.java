 
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 *
 * @author GiayNhap
 */
public class ChatServerEndPointConfigurator extends Configurator {

	private ChatServerEndPoint chatServer = new ChatServerEndPoint();

	@SuppressWarnings("unchecked")
	public <T> T getEndpointInstance(Class<T> endpointClass)
			throws InstantiationException {
		return (T)chatServer;
	}
}