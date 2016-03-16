package com.italcred.devel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
	
	protected static Connection mConnection;
	
	protected static void generarConexion(){
		try{
			if (mConnection == null || mConnection.isClosed()){
				String userName, userPassword, connectionURL;

				userName 	  = "FRANQICURU\\rlomez";
				userPassword  = "Febrero2016";
				//connectionURL = "jdbc:odbc:SQLUAT";
				connectionURL = "jdbc:odbc:SQLFP01";
				
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				mConnection = DriverManager.getConnection(connectionURL, userName, userPassword);
			}
		}catch(SQLException | ClassNotFoundException ex){
			ex.printStackTrace(System.err);
		}
	}
	
	protected static void cerrarConexion(){
		try{
			mConnection.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
//	Statement query = mConnection.createStatement();
//	ResultSet result =  query.executeQuery(consulta);
//	ResultSetMetaData metadata = result.getMetaData();
//	int index;
//	while(result.next()){
//		index = 1;
//		while(index <= metadata.getColumnCount()){
//			System.out.print(result.getString(index) + ", ");
//			index++;
//		}
//		System.out.println("");
//	}
	
}
