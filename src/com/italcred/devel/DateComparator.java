package com.italcred.devel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.italcred.devel.JSONHelper.Documento;

public class DateComparator implements Comparator<Documento>{

	@Override
	public int compare(Documento firstDocument, Documento secondDocument) {
		SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
		Date date_firstDate = null;
		Date date_secondDate = null;
		try {
			date_firstDate = df.parse(firstDocument.getFecha());			
			date_secondDate = df.parse(secondDocument.getFecha());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date_secondDate.compareTo(date_firstDate);
	}
}
