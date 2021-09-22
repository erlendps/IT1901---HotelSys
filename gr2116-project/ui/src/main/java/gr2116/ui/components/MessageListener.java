package gr2116.ui.components;

import gr2116.ui.message.Message;

public interface MessageListener {
	
	public void receiveNotification(Object from, Message message, Object data);

}
