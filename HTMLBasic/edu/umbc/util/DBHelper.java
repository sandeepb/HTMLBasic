/*
 * Created on Apr 17, 2005
 *
 */
package edu.umbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 * @author Sandeep Balijepalli
 *
 */

public class DBHelper {
	
	public Connection connection = null;
	public Statement statement = null;
	public PreparedStatement preparedStatement = null;
	
	public static void main(String[] args) {
	}
	
	public DBHelper(){
		
	}
	
	
	public ResultSet executeQuery(String query) throws SQLException{	
		ResultSet rs = null;
		try{
			rs = statement.executeQuery(query);
			
		}catch(SQLException e){
			System.out.println(e);
			throw e;      	
        }		
		return rs;
	}

	public void executeUpdate(String query) throws SQLException{	
		ResultSet rs = null;
		try{
			statement.executeUpdate(query);
			
		}catch(SQLException e){
			System.out.println(e);
			throw e;      	
        }		
	}
		
	
	public int logQuery(String query) throws SQLException{
		int key =-1;
		try{
			System.out.println(query);
			statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt(1);
				System.out.println(key);
				rs.close();
			}
		    return key;
		}catch(SQLException e){
			System.out.println("In Log Query "+e);
			throw e;      	
        }		
	}
	
	public int logAutoUpdate(String query) throws SQLException{
		int key =-1;
		try{
			//System.out.println(query);
			statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt(1);
				//System.out.println(key);
				rs.close();
			}
		    return key;
		}catch(SQLException e){
			System.out.println("In Log Query "+e);
			throw e;      	
        }		
	}
	
	public int logQueryTerm(String query) throws SQLException{
		int key=-1;
		try{
			System.out.println(query);
			statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt(1);
				System.out.println(key);
				rs.close();
			}
		    return key;			
		}catch(SQLException e){
			System.out.println("In DBInterface"+e);
			throw e;      	
        }		
	       
	}
	
	public void openConnection
		(String serverName, String mydatabase, String username, String password) 
			throws SQLException, ClassNotFoundException{
		try{
			Class.forName("com.mysql.jdbc.Driver");
	        //serverName = "apple.cs.umbc.edu";
	        //mydatabase = "semdis_iswc";
	        //username = "semdis";
	        //password = "semdis_iswc";
	        String url = "jdbc:mysql://" + serverName +  "/" + mydatabase; // a JDBC url
	        connection = DriverManager.getConnection(url, username, password);
	        statement = connection.createStatement();	        
		}catch(ClassNotFoundException e){
			System.out.println(e);
			throw e;
		}
        catch(SQLException e){
			System.out.println(e);
			throw e;      	
        }
	}
	
	public void openPreparedConnection
	(String serverName, String mydatabase, String username, String password) 
		throws SQLException, ClassNotFoundException{
	try{
		Class.forName("com.mysql.jdbc.Driver");
        //serverName = "apple.cs.umbc.edu";
        //mydatabase = "semdis_iswc";
        //username = "semdis";
        //password = "semdis_iswc";
        String url = "jdbc:mysql://" + serverName +  "/" + mydatabase; // a JDBC url
        connection = DriverManager.getConnection(url, username, password);
        //preparedStatement = connection.prepareStatement(template);
	}catch(ClassNotFoundException e){
		System.out.println(e);
		throw e;
	}
    catch(SQLException e){
		System.out.println(e);
		throw e;      	
    }
    
}
	
	public void closeConnection() throws SQLException{
		try{
			connection.close();
		}catch(SQLException e){
			System.out.println(e);
			throw e;      	
        }		
	}	
	
	public PreparedStatement createPreparedStatement(String template) throws SQLException{
		PreparedStatement stat= null;
		try {
			stat = connection.prepareStatement(template);
		}catch(SQLException e){
			System.out.println(e);
			throw e;
		}
		return stat;
	}

	public PreparedStatement createPreparedStatement(String template, int parameter) throws SQLException{
		PreparedStatement stat= null;
		try {
			stat = connection.prepareStatement(template, parameter);
		}catch(SQLException e){
			System.out.println(e);
			throw e;
		}
		return stat;
	}
	
}
