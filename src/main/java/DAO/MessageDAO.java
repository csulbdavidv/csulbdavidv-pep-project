package DAO;
import Model.Account;
import Util.ConnectionUtil;
import Model.Message;

import java.sql.*;
import java.util.*;

public class MessageDAO {
    public Message createMessage(Message m){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, m.getPosted_by());
            preparedStatement.setString(2, m.getMessage_text());
            preparedStatement.setLong(3, m.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, m.getPosted_by(), m.getMessage_text(), m.getTime_posted_epoch());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from Message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM Message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            Message m = getMessageById(message_id);
            preparedStatement.executeUpdate();
            return m;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int message_id, Message m){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, m.getPosted_by());
            preparedStatement.setString(2, m.getMessage_text());
            preparedStatement.setLong(3, m.getTime_posted_epoch());
            preparedStatement.setInt(4, message_id);
            preparedStatement.executeUpdate();
            return new Message(message_id, m.getPosted_by(), m.getMessage_text(), m.getTime_posted_epoch());
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
