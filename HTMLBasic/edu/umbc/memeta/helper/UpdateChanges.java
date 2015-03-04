/*
 * Created on May 25, 2005
 *
 */

package edu.umbc.memeta.helper;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;

import edu.umbc.util.DBHelper;

/**
 * @author Sandeep Balijepalli
 *
 */

public class UpdateChanges {

	/**
	 * 
	 */
	
	DBHelper connection;
	PreparedStatement statement1;
	PreparedStatement statement2;
	PreparedStatement statement3;
	PreparedStatement statement4;
	PreparedStatement statement5;
	PreparedStatement statement6;
	
	
	public UpdateChanges() throws Exception{
	  	try {
		  	//lQ =  new LogQueries();
			//lQ.openLogs("triloki.cs.umbc.edu", "blogrss","kolari", "yahoo123" );
			connection = new DBHelper();
	  		connection.openConnection("devagni.cs.umbc.edu", "myspace", "semdis", "semdis2004agents");
	  		
	  		
	  		statement3 = connection.createPreparedStatement(
	  				"INSERT into weblogs_myspace(url, name, created) values(?,?,?);");
	  		
	  	}catch(Exception e){
	  		System.out.println(e);
	  		throw e;
	  	}
	}

	public static void main(String[] args) {
		
	}
	
	public int insertWeblog(String blogname, String blogurl, String formattedDateTime){
		int status = 0;
      	try {
	        //blogname = blogname.replaceAll("'", "''");
	        if(blogname.length() > 255) blogname = blogname.substring(0, 254);
	        //String query1 = "Select id, count from changes_weblogs where url='"+blogurl+"';";
	        statement3.setString(1, blogurl);
	        statement3.setString(2, blogname);
	        statement3.setString(3, formattedDateTime);
	        statement3.executeUpdate();
      	}
      	catch (Exception e){
      		System.out.println(e);
      	}
      	return status;
	
}
}