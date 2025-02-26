package Service;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;
import java.util.*;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = new AccountDAO();
    }

    public Message createMessage(Message m){
        if(m.getMessage_text().length() > 0 && m.getMessage_text().length() < 255 && accountDAO.getUserByAccountId(m.getPosted_by()) != null){
            return messageDAO.createMessage(m);
        }
        return null;  
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id){
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessage(int message_id, Message m){
        if(m.getMessage_text().length() > 0 && m.getMessage_text().length() < 255 && messageDAO.getMessageById(message_id) != null){
            messageDAO.updateMessage(message_id, m);
            return messageDAO.getMessageById(message_id);
        }
        return null;
    }

    public List<Message> getAllMessagesForUser(int account_id){
        return messageDAO.getAllMessagesForUser(account_id);
    }
}
