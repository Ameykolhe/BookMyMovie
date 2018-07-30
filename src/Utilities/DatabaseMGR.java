package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("deprecation")
public class DatabaseMGR {
	static final String juser = "root";
	static final String jpass = "1379";
	static final String url = "jdbc:mysql://127.0.0.1:3306/moviereservationdb?autoReconnect=true&use=false";
	static final String driver = "com.mysql.cj.jdbc.Driver";
	
	static Connection conn;
	static Statement stmt;
	
	static{
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection( url , juser , jpass );
			stmt = conn.createStatement();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery( String query ) throws SQLException
	{
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}
	
	public static Integer executeUpdate( String query ) throws SQLException
	{
		Integer i = new Integer(stmt.executeUpdate(query));
		return i;
	}
}