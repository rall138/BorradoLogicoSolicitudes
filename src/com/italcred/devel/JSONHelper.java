package com.italcred.devel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class JSONHelper {

	private static final String doc_path = System.getProperty("user.home") + "\\his_docs.txt";
	
	public static void writeJSON(HashMap<String, String> coleccionDocumentos){
		JSONArray jarray = new JSONArray();
		JSONObject jparentObject = new JSONObject();
		JSONObject jobject;
		for(Map.Entry<String, String> documento : coleccionDocumentos.entrySet()){
			jobject = new JSONObject();
			jobject.put("Documento", documento.getKey());
			jobject.put("Fecha", documento.getValue());
			jarray.add(jobject);
		}
		jparentObject.put("Registros", jarray);
		
		try{
			FileWriter writer = new FileWriter(doc_path);
			writer.write(jparentObject.toJSONString());
			writer.flush();
			writer.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public static HashMap<String, String> readJSON(){
		JSONParser parser = new JSONParser();
		HashMap<String, String> documentos = new HashMap<>();
		try{
			JSONObject jparentObject = (JSONObject)parser.parse(new FileReader(doc_path));
			JSONArray jarray = (JSONArray)jparentObject.get("Registros");
			Iterator<JSONObject> it = jarray.iterator();
			while(it.hasNext()){
				JSONObject jobject = it.next();
				documentos.put((String)jobject.get("Documento"), (String)jobject.get("Fecha"));
			}
		}catch(org.json.simple.parser.ParseException | IOException ex){
			ex.printStackTrace();
		}
		return documentos;
	}
	
//	public static HashMap<String, String> readSortedJSON(){
//		JSONParser parser = new JSONParser();
//		HashMap<String, String> documentos = new HashMap<>();
//		try{
//			JSONObject jparentObject = (JSONObject)parser.parse(new FileReader(doc_path));
//			JSONArray jarray = (JSONArray)jparentObject.get("Registros");
//			JSONObject jobject;
//			List<JSONObject> coleccionObjetos = new ArrayList<>();
//			for(int index = 0; index < jarray.size(); index++){
//				jobject = (JSONObject)jarray.get(index);
//				
//			}
//			
//
//				documentos.put((String)jobject.get("Documento"), (String)jobject.get("Fecha"));
//			}
//		}catch(org.json.simple.parser.ParseException | IOException ex){
//			ex.printStackTrace();
//		}
//		return documentos;
//	}	
}
