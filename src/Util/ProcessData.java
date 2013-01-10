package Util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcessData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String inputFile = "D:/workspace/segaDentalSist/Data/DATA MEDICALONE C.A.xls";
		File inputWorkbook = new File(inputFile);
	    Workbook w;
	    
	    try {
	    	w = Workbook.getWorkbook(inputWorkbook);
		  	//Sheet[] sheets = w.getSheets();
		   	// Get the first sheet
		 	Sheet sheet = w.getSheet(0);
		   	// Loop over first 10 column and lines
	      
		 	for (int i = 1; i < sheet.getRows(); i++) {
		 		
		 		//Cédula
		 		Cell cell = sheet.getCell(0, i);
		 		Long identityNumber = Long.valueOf(cell.getContents());
		 		String identityCard; 
		 		
		 		if(identityNumber>=80000000){
		 			identityCard = "E-"+identityNumber;
		 		} else {
		 			identityCard = "V-"+identityNumber;
		 		}
		 		
		 		//Apellidos
		 		cell = sheet.getCell(1, i);
		 		String lastNames = cell.getContents().toLowerCase();
		 		lastNames = lastNames.substring(0, 1).toUpperCase() + lastNames.substring(1, lastNames.length());
		 		
		 		//Nombres
		 		cell = sheet.getCell(2, i);
		 		String firstNames = cell.getContents().toLowerCase();
		 		firstNames = firstNames.substring(0, 1).toUpperCase() + firstNames.substring(1, firstNames.length());
		 		
		 		//Contrato
		 		cell = sheet.getCell(3, i);
		 		String contract = cell.getContents();
		 		
		 		if(contract.equalsIgnoreCase("RENOVACION")){
		 			//TODO: Qué hacer en este caso?
		 		}
		 		
		 		//Fecha Venta
		 		cell = sheet.getCell(4, i);
		 		String affiliationDateStr = cell.getContents();
		 		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		 		Date affiliationDate = dateFormat.parse(affiliationDateStr);
		 		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		 		affiliationDateStr = dateFormat2.format(affiliationDate);
		 		
		 		//Nombre Producto
		 		cell = sheet.getCell(5, i);
		 		String productName = cell.getContents();
		 		Integer productId;
		 				 		
		 		if(productName.equalsIgnoreCase("NO RENOVAR")){
		 			//TODO: Qué hacer en este caso?
		 			productId = null;
		 		} else {
		 			productId = 24;
		 		}
		 		
		 		//Cantidad
		 		cell = sheet.getCell(6, i);
		 		String quantity = cell.getContents();
		 		boolean isHolder = false;
		 		String contractHolder = null;
		 		
		 		if(quantity.equalsIgnoreCase("T")){
		 			isHolder = true;
		 		} else {
		 			quantity = quantity.replaceFirst("B-", "");
		 			int contractNumber = Integer.valueOf(quantity);
		 			Formatter fmt = new Formatter();
				 	fmt.format("%07d",contractNumber);
				 	contractHolder = "ML-"+fmt;
				 	//TODO: Sumar precio del beneficiario al titular?
				 	//TODO: Ojo con cedulas repetidas beneficiario-titular! Chequear!
				 	//TODO: Si contrato de titular no existe ERROR no se pudo incluir beneficiario
		 		}
		 		
		 		//Precio
		 		cell = sheet.getCell(7, i);
		 		String priceStr = cell.getContents();
		 		priceStr = priceStr.replaceAll("\\.", "");
		 		priceStr = priceStr.replaceAll(",", "\\.");
		 		Double price = Double.valueOf(priceStr);
		 		
		 		//Vendedor
		 		cell = sheet.getCell(8, i);
		 		String sellerName = cell.getContents().toUpperCase();
		 		String[] names = sellerName.split(" ");
		 		String sellerFirstNames, sellerLastNames, sellerUserName, sellerPassword;
		 		int roleId = 2;
		 		
		 		if(names.length==2){
		 			sellerFirstNames = names[0].toLowerCase();
		 			sellerFirstNames = sellerFirstNames.substring(0, 1).toUpperCase() + sellerFirstNames.substring(1, sellerFirstNames.length());
		 			sellerLastNames = names[1];
		 		} else {
		 			sellerFirstNames = names[0].toLowerCase();
		 			sellerLastNames = null;
		 		}
		 		
		 		sellerUserName = sellerName.replaceAll(" ", "").toLowerCase();
		 		//TODO: Encriptar Password
		 		sellerPassword = sellerUserName;
		 		
		 		//Turno
		 		cell = sheet.getCell(9, i);
		 		String turn = cell.getContents();
		 		
		 		if(turn.equalsIgnoreCase("AM")){
		 			turn = "A.M.";
		 		} else if(turn.equalsIgnoreCase("PM")){
		 			turn = "P.M.";
		 		} else {
		 			turn = null;
		 		}
		 		
		 		//Ciudad
		 		cell = sheet.getCell(10, i);
		 		String city = cell.getContents().toLowerCase();
		 		String[] cityWords = city.split(" ");
		 		String cityWord;
		 		
		 		for (int k=0; k<cityWords.length; k++){
		 			cityWord = cityWords[k];
		 			cityWord = cityWord.substring(0,1).toUpperCase() + cityWord.substring(1,cityWord.length());
		 			
		 			if(k==0){
		 				city = cityWord;
		 			}else{
		 				city += " " + cityWord;
		 			}
	 			}
		 		
		 		//Estado
		 		cell = sheet.getCell(11, i);
		 		String state = cell.getContents().toLowerCase();
		 		String[] stateWords = state.split(" ");
		 		String stateWord;
		 		
		 		for (int k=0; k<stateWords.length; k++){
		 			stateWord = stateWords[k];
		 			stateWord = stateWord.substring(0,1).toUpperCase() + stateWord.substring(1,stateWord.length());
		 			
		 			if(k==0){
		 				state = stateWord;
		 			}else{
		 				state += " " + stateWord;
		 			}
	 			}
		 		
		 		//Tel Res
		 		cell = sheet.getCell(12, i);
		 		String homePhone = cell.getContents();
		 		homePhone = homePhone.replaceAll("-", "");
		 		int homePhoneTypeId = 1;
		 		
		 		if(homePhone.length()==0){
		 			homePhone = null;
		 		} else if(homePhone.length()!=0 && homePhone.length()!=11){
		 			//TODO: Error de telefono invalido
		 			homePhone = "ERROR";
		 		} else {
		 			//TODO: Insertar telefono
		 		}
		 		
		 		//Tel Ofi
		 		cell = sheet.getCell(13, i);
		 		String officePhone = cell.getContents();
		 		officePhone = officePhone.replaceAll("-", "");
		 		int officePhoneTypeId = 2;
		 		
		 		if(officePhone.length()==0){
		 			officePhone = null;
		 		} else if(officePhone.length()!=0 && officePhone.length()!=11){
		 			//TODO: Error de telefono invalido
		 			officePhone = "ERROR";
		 		} else {
		 			//TODO: Insertar telefono
		 		}
		 		
		 		//Tel Cel
		 		cell = sheet.getCell(14, i);
		 		String mobilePhone = cell.getContents();
		 		mobilePhone = mobilePhone.replaceAll("-", "");
		 		int mobilePhoneTypeId = 3;
		 		
		 		if(mobilePhone.length()==0){
		 			mobilePhone = null;
		 		} else if(mobilePhone.length()!=0 && mobilePhone.length()!=11){
		 			//TODO: Error de telefono invalido
		 			mobilePhone = "ERROR";
		 		} else {
		 			//TODO: Insertar telefono
		 		}
		 		
		 		//Tel Fax
		 		cell = sheet.getCell(15, i);
		 		String otherPhone = cell.getContents();
		 		otherPhone = otherPhone.replaceAll("-", "");
		 		int otherPhoneTypeId = 4;
		 		
		 		if(otherPhone.length()==0){
		 			otherPhone = null;
		 		} else if(otherPhone.length()!=0 && otherPhone.length()!=11){
		 			//TODO: Error de telefono invalido
		 			otherPhone = "ERROR";
		 		} else {
		 			//TODO: Insertar telefono
		 		}
		 		
		 		//Email
		 		cell = sheet.getCell(16, i);
		 		String email = cell.getContents();
		 		
		 		if(email.equalsIgnoreCase("")){
		 			email = null;
		 		}
		 		
		 		//TODO: Fila 17 anotaciones! Debo hacer algo con eso?
		 				 		
		 		System.out.println((i+1)+".- identityCard: "
 						+ identityCard + ", lastNames: " + lastNames + ", firstNames: "+firstNames+", contract: "+contract+
 						", affiliationDateStr: "+affiliationDateStr+", productId: "+productId);
		 		System.out.println("isHolder: "+isHolder+
 						", contractHolder: "+contractHolder+", price: "+price+", sellerFirstNames: "+sellerFirstNames+
 						", sellerLastNames: "+sellerLastNames);
		 		System.out.println("sellerUserName: "+sellerUserName+", sellerPassword:"+
 						sellerPassword+", roleId: "+roleId+", turn: "+turn+", city: "+city+", state: "+state+", homePhone: "+homePhone);
		 		System.out.println("officePhone: "+officePhone+", mobilePhone: "+mobilePhone+", otherPhone: "+otherPhone+", email: "+email);
		 		System.out.println();
		 	}
	    } catch (BiffException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
