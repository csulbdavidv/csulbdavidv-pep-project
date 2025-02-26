package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {

    public Account getUserByAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "Select * from Account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account register(Account a){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, a.getUsername());
            preparedStatement.setString(2, a.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, a.getUsername(), a.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(Account a){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * from Account where username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, a.getUsername());
            preparedStatement.setString(2, a.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
