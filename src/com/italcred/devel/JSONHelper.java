package com.italcred.devel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
	
	public static void writeJSON(List<Documento> coleccionDocumentos){
		JSONArray jarray = new JSONArray();
		JSONObject jparentObject = new JSONObject();
		JSONObject jobject;
		for(int index = 0; index < coleccionDocumentos.size(); index++){
			jobject = new JSONObject();
			jobject.put("Documento", coleccionDocumentos.get(index).getDocumento());
			jobject.put("Fecha", coleccionDocumentos.get(index).getFecha());
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
	
	public static List<Documento> readJSON(){
		JSONParser parser = new JSONParser();
		Documento documento;
		JSONHelper helper = new JSONHelper();
		List<Documento> documentos = new ArrayList<>();
		try{
			JSONObject jparentObject = (JSONObject)parser.parse(new FileReader(doc_path));
			JSONArray jarray = (JSONArray)jparentObject.get("Registros");
			Iterator<JSONObject> it = jarray.iterator();
			while(it.hasNext()){
				JSONObject jobject = it.next();
				documento = helper.new Documento((String)jobject.get("Documento"),
						(String)jobject.get("Fecha"));
				documentos.add(documento);
			}
		}catch(org.json.simple.parser.ParseException | IOException ex){
			ex.printStackTrace();
		}
		return documentos;
	}
	
	public class Documento{
		private String documento;
		private String fecha;
		
		public Documento(String nro_documento, String fecha){
			this.documento = nro_documento;
			this.fecha = fecha;
		}
		
		public String getDocumento() {
			return documento;
		}
		public void setDocumento(String documento) {
			this.documento = documento;
		}
		public String getFecha() {
			return fecha;
		}
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}
		
		
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
