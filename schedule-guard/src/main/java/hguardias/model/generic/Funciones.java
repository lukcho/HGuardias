package hguardias.model.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class Funciones {
	
	private static  final int num_provincias = 24;
	/**
	 * Atributos para el manejo de estados
	 */
	public static String estadoActivo = "A";
	public static String valorEstadoActivo = "Activo";
	public static String estadoInactivo = "I";
	public static String valorEstadoInactivo = "Inactivo";
	public static String estadoEnviado = "E";
	public static String valorEstadoEnviado = "Enviado";
	public static String estadoNegado = "N";
	public static String valorEstadoNegado = "Negado";
	public static String estadoFinalizado = "F";
	public static String valorEstadoFinalizado = "Finalizado";
	
	public static String estadoAprobado = "A";
	public static String valorEstadoAprobado = "Aprobado";
	
	public static String estadoRechazado = "R";
	public static String valorEstadoRechazado = "Rechazado";
	
	public static String estadoAnulado = "N";
	public static String valorEstadoAnulado= "Anulado";

	//Atributos de Genero
	public static String estadoFemenino = "Femenino";
	public static String valorEstadoFemenino = "F";
	public static String estadoMasculino = "Masculino";
	public static String valorEstadoMasculino = "M";

	//Atributos de Genero
	public static String valorEstadoA = "A";
	public static String valorEstadoA1= "A1";
	public static String valorEstadoB= "B";
	public static String valorEstadoC = "C";
	public static String valorEstadoC1 = "C1";
	public static String valorEstadoD = "D";
	public static String valorEstadoD1 = "D1";
	public static String valorEstadoE = "E";
	public static String valorEstadoE1 = "E1";
	public static String valorEstadoF = "F";
	public static String valorEstadoG = "G";

	public static Integer mayoriaDeEdad = 18;
		
//	public static String hostWS1 = "http://10.1.0.115:8080/app-permisos/";
	public static String hostWS = "http://app-permisos.yachay.gob.ec/";
	
	public static Boolean validacionCedula(String cedula){
        //verifica que los dos primeros d�gitos correspondan a un valor entre 1 y NUMERO_DE_PROVINCIAS
        int prov = Integer.parseInt(cedula.substring(0, 2));
        if (!((prov > 0) && (prov <= num_provincias) || prov==30)) {
        	//addError("La c�dula ingresada no es v�lida");
        	System.out.println("Error: cedula ingresada mal");
            return false;
        }
        //verifica que el �ltimo d�gito de la c�dula sea v�lido
        int[] d = new int[10];
        //Asignamos el string a un array
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }
        int imp = 0;
        int par = 0;
        //sumamos los duplos de posici�n impar
        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }
        //sumamos los digitos de posici�n par
        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }
        //Sumamos los dos resultados
        int suma = imp + par;
        //Restamos de la decena superior
        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) + "0") - suma;
        //Si es diez el d�cimo d�gito es cero        
        d10 = (d10 == 10) ? 0 : d10;
        //si el d�cimo d�gito calculado es igual al digitado la c�dula es correcta
        if (d10 == d[9]) {
        	return true;
        }else {
        	//addError("La c�dula ingresada no es v�lida");
        	return false;
        }
	}
	
	/**
	 * Convierte un cadena en codigo MD5
	 * @param input entrada de cadena para convertirla en MD5
	 * @return String MD5
	 */
	public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	 public static boolean validarEmail(String email) {
		 
	        // Definicion de pattern con la expresion regular
	        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	 
	        // COmprobacion del correo ingresado
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	 
	    }
	
	/**
	 * Convierte un cadena en codigo SHA2
	 * @param input  entrada de cadena para convertirla en SHA2
	 * @return String MD5
	 */
	public String getSHA2(String input) {
	    try {
	        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
	        String salt = "some_random_salt";
	        String passWithSalt = input + salt;
	        byte[] passBytes = passWithSalt.getBytes();
	        byte[] passHash = sha256.digest(passBytes);             
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< passHash.length ;i++) {
	            sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));         
	        }
	        String generatedPassword = sb.toString();
	        return generatedPassword;
	    } catch (NoSuchAlgorithmException e) { 
	    	e.printStackTrace(); 
	    	return null;
	    }       
	}
	
	/**
	 * Transforma una fecha a String
	 * @param fecha
	 * @return String
	 */
	public static String dateToString(Date fecha){
		DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		if(fecha==null)
			return "";
		else
			return formato.format(fecha).toString();
	}
	
	/**
	 * Transforma un string de fecha en Date
	 * @param fecha
	 * @return Date
	 * @throws ParseException
	 */
	public static Date stringToDate(String fecha) throws ParseException{
		DateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(fecha.isEmpty())
			return null;
		else
			return formato.parse(fecha);
	}
	
	/**
	 * Transforma una fecha a letters
	 * @param fecha
	 * @return String
	 */
	public static String dateToLetters(Date fecha){
		DateFormat formato = new SimpleDateFormat("dd-MMMM-yyyy");
		if(fecha==null)
			return "";
		else
			return formato.format(fecha).toString().replace("-", " de ");
	}
	
	/**
	 * Transforma un string de fecha en Date
	 * @param fecha
	 * @return Date
	 * @throws ParseException
	 */
	public static Date stringToDateF(String fecha) throws ParseException{
		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		if(fecha.isEmpty())
			return null;
		else
			return formato.parse(fecha);
	}
	
	/**
	 * Evalua si una cadena es numerica
	 * @param cadena
	 * @return
	 */
	public static boolean isNumeric(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	/**
	 * Evalua un dato que viene desde un JSON
	 * @param dato
	 * @return String
	 */
	public static String evaluarDatoWS(Object dato){
		if(dato!=null)
			return dato.toString();
		else
			return "";
	}
	
	
	public static Date evaluarDatoWSDate(Object dato){
		Date date = null;
		try {
			if(dato!=null)
			{
			System.out.println(dato);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.parse((String) dato);
			}
			else
			date = new Date();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Evalua un String para convertirlo a Entero
	 * @param dato
	 * @return Integer
	 */
	public static Integer evaluarString(String dato){
		if(dato.isEmpty())
			return 0;
		else
			return Integer.parseInt(dato);
	}
	
	/**
	 * Genera un pass unico
	 * @return pass
	 */
	public static String genPass(){
		String pass = ""; 
		for (int i = 0; i < 4; i++) {
			pass+=(char)(Math.random()*25+97)+""+(int)(Math.random()*9+1);
		}
		return pass;
	}
	
	/**
	 * Convierte una cadena en otra con codificaci�n utf-8
	 * @param cadena
	 * @return String
	 */
	public static String utf8Sting(String cadena){
		try {
			return new String(cadena.getBytes("UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "Error utf8Sting";
		}
	}
	
	/**
	 * Transforma un timestamp a String
	 * @param estFechaIni
	 * @return String
	 */
	public static String timestampToString(Timestamp estFechaIni) {
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(estFechaIni);
	}
	
	/**
	 * Calcula la edad de una persona
	 * (Date fechaNac = new SimpleDateFormat("dd-MM-yyyy").parse("10-12-1980");)
	 * @param fecha
	 * @return Integer
	 */
	public static Integer calcularEdad(Date fecha) {
		Calendar fechaNacimiento = Calendar.getInstance();
		// Se crea un objeto con la fecha actual
		Calendar fechaActual = Calendar.getInstance();
		// Se asigna la fecha recibida a la fecha de nacimiento.
		fechaNacimiento.setTime(fecha);
		// Se restan la fecha actual y la fecha de nacimiento
		int anio = fechaActual.get(Calendar.YEAR)
				- fechaNacimiento.get(Calendar.YEAR);
		int mes = fechaActual.get(Calendar.MONTH)
				- fechaNacimiento.get(Calendar.MONTH);
		int dia = fechaActual.get(Calendar.DATE)
				- fechaNacimiento.get(Calendar.DATE);
		// Se ajusta el a�o dependiendo el mes y el d�a
		if (mes < 0 || (mes == 0 && dia < 0)) {
			anio--;
		}
		// Regresa la edad en base a la fecha de nacimiento
		return anio;
	}
	
	/**
	 * Determina si es mayor de edad
	 * @param fecha
	 * @return boolean
	 */
	public static boolean mayorDeEdad(Date fecha){
		if(calcularEdad(fecha)>=mayoriaDeEdad)
			return true;
		else
			return false;
	}
	
	public static String hora_1 = "01:00:00";
	public static String hora_2 = "02:00:00";
	public static String hora_3 = "03:00:00";
	public static String hora_4 = "04:00:00";
	public static String hora_5 = "05:00:00";
	public static String hora_6 = "06:00:00";
	public static String hora_7 = "07:00:00";
	public static String hora_8 = "08:00:00";
	public static String hora_9 = "09:00:00";
	public static String hora_10 = "10:00:00";
	public static String hora_11 = "11:00:00";
	public static String hora_12 = "12:00:00";
	public static String hora_13 = "13:00:00";
	public static String hora_14 = "14:00:00";
	public static String hora_15 = "15:00:00";
	public static String hora_16 = "16:00:00";
	public static String hora_17 = "17:00:00";
	public static String hora_18 = "18:00:00";
	public static String hora_19 = "19:00:00";
	public static String hora_20 = "20:00:00";
	public static String hora_21 = "21:00:00";
	public static String hora_22 = "22:00:00";
	public static String hora_23 = "23:00:00";
	public static String hora_24 = "24:00:00";
	
	public static String automovil = "Autom�vil";
	public static String camioneta = "Camioneta";
	
	/**
	 * M�todo para descargar archivos excel
	 * 
	 * @param url
	 */
	public static void descargarExcel(String url) {
		System.out.println(url);
		File ficheroXLS = new File(url);
		FacesContext ctx = FacesContext.getCurrentInstance();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(ficheroXLS);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] bytes = new byte[1000];
		int read = 0;

		if (!ctx.getResponseComplete()) {
			String fileName = ficheroXLS.getName();
			String contentType = "application/vnd.ms-excel";
			// String contentType = "application/pdf";
			HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			ServletOutputStream out = null;
			try {
				out = response.getOutputStream();
				while ((read = fis.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Descargado....!!!");
			ctx.responseComplete();
		}
	}
}
