package hguardias.controller.access;

import org.json.simple.JSONObject;

import hguardias.model.dao.entities.Persona;
import hguardias.model.generic.ConsumeREST;
import hguardias.model.generic.Funciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class test {

	public static void main(String[] args) {
		try {
			 BufferedReader br = null;

		        br = new BufferedReader(new InputStreamReader(System.in));
		        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		        System.out.println("Insert first date : ");
		        Date dt1 = sdf.parse(br.readLine().trim());

		        System.out.println("Insert second date : ");
		        Date dt2 = sdf.parse(br.readLine().trim());

		        long diff = dt2.getTime() - dt1.getTime();

		        System.out.println("Days: " + diff / 1000L / 60L / 60L / 24L);

		        if (br != null) {
		            br.close();
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
