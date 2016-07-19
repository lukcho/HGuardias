package hguardias.controller.access;

import hguardias.model.manager.ManagerCarga;
import hguardias.model.dao.entities.Persona;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ManagerCarga mg = new ManagerCarga();
		try {
			String a = "aquina";
			Persona per = mg.funcionarioByDNI(a);
			System.out.println(per.getPerNombres()+" "+per.getPerApellidos());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
