/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Network;

/**
 *
 * @author Simon
 */
public class MessageFactory<T> {

    public Message<T> createMessage(eMessageType type, T dataContainer) {
        Message<T> message  = new Message<>();
        message.setMessageType(type);
        message.setDataContainer(dataContainer);
        return message;
    }
   
}
