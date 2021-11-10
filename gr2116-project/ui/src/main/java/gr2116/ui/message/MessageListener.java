package gr2116.ui.message;

/**
 * Interface for lsiteners that receive notifications containing data from the Message enum.
 */
public interface MessageListener {
  /**
   * Register that something has been sent to the listener.
   *
   * @param from The object that the notification is from
   * @param message The message contained in the notification, chosen from Message Enum
   * @param data An object sent with the notification. Varies depending on message.
   */
  void receiveMessage(Object from, Message message, Object data);

}
