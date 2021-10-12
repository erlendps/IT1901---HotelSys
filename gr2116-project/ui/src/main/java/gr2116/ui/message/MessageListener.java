package gr2116.ui.message;

public interface MessageListener {

  void receiveNotification(Object from, Message message, Object data);

}
