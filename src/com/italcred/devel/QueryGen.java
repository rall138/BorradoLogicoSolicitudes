package com.italcred.devel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class QueryGen extends SQLHelper{

	private static HashMap<String, Object> map = new HashMap<>();
	private static final String UseSentence = "USE FinancialPro;";
	
	public static HashMap<String, Object> obtencionCataClil(String documento){
		generarConexion();
		String consulta = UseSentence 
		+ " SELECT * FROM CATACLIL WHERE CliCod='"+documento+"' ORDER BY stnrosol;";
		map.clear();		
		try{
			ResultSet result =  executeQuery(consulta);
			boolean condition = false;
			String movCod = ""; 
			int solNro = 0, stNroSoc = 0;
			while(result != null && result.next() && !condition){
				solNro = result.getInt(4);
				movCod = result.getString(5).trim();
				stNroSoc = result.getInt(6);
				if(movCod.compareTo("143") == 0 || movCod.compareTo("145") == 0){
					map.put("SolNro", new Integer(solNro));
					map.put("STNroSoc", new Integer(stNroSoc));
					map.put("MovCod", movCod);
					condition = true;
				}
			}
			result.getStatement().close();
		}catch(SQLException ex){
			ex.printStackTrace(System.err);
		}
		return map;		
	}
	
	private static ResultSet executeQuery(String consulta){
		generarConexion();
		ResultSet result = null;
		try{
			Statement query = mConnection.createStatement();
			result = query.executeQuery(consulta);
		}catch(SQLException ex){
			cerrarConexion();
			ex.printStackTrace();
		}
		return result;
	}
	
	private static void executeUpdate(String sentencia){
		generarConexion();
		try{
			Statement query = mConnection.createStatement();
			query.executeUpdate(sentencia);
			query.close();
		}catch(SQLException ex){
			cerrarConexion();			
			ex.printStackTrace();
		}
	}
	
	public static void modificaCATACLIL(int nroSol, int nroSoc, String documento, boolean isEliminacion){
		String ordinal = "9" + documento;
		String update = "";
		if (isEliminacion){ 
			update = UseSentence +
			" UPDATE CATACLIL SET CliCod="+ordinal+" WHERE CliCod="+documento+" AND STNroSoc="+nroSoc+";";
		}else{
			update = UseSentence +
			" UPDATE CATACLIL SET CliCod="+documento+" WHERE CliCod="+ordinal+" AND STNroSoc="+nroSoc+";";
		}
		executeUpdate(update);
	}
	
	public static void modificaSOLICITU(int nroSol, int nroSoc, String documento, boolean isEliminacion){
		String ordinal = "9" + documento;
		String update = "";
		if (isEliminacion){
			update = UseSentence +
			" UPDATE SOLICITU SET CliCod="+ordinal+" WHERE CliCod="+documento+" AND SolNro="+nroSol+";";
		}else{
			update = UseSentence +
			" UPDATE SOLICITU SET CliCod="+documento+" WHERE CliCod="+ordinal+" AND SolNro="+nroSol+";";			
		}
		executeUpdate(update);
	}
	
	public static void modificaQSCAM(String documento, boolean isEliminacion){
		String ordinal = "9" + documento;
		String update = "";
		if (isEliminacion){
			update = UseSentence +
			" DELETE BENTITRN WHERE EntICliCod = "+documento+";" +
			" UPDATE Qscam SET QCliCod="+ordinal+" WHERE QCliCod="+documento+";";
		}else{
			update = UseSentence +
			" UPDATE Qscam SET QCliCod="+documento+" WHERE QCliCod="+ordinal+";";			
		}
		executeUpdate(update);
		cerrarConexion();
	}
	
	//Usada para verificar QSCAM, mas que nada para los casos donde solo posee registros de este tipo
	public static int verificarRegistrosQSCAM(String documento){
		String consulta = "SELECT COUNT(*) FROM QSCAM WHERE QCliCod="+documento+";";
		ResultSet result = executeQuery(consulta);
		int cantidad_qscam = 0;
		try{
			while (result.next()){
				cantidad_qscam = result.getInt(1);
			}
			result.getStatement().close();
			cerrarConexion();
		}catch(SQLException ex){
			System.err.println("VRQSCAM");
			ex.printStackTrace();
		}
		return cantidad_qscam;
	}
}
