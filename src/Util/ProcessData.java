package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import servlet.UserLoginServlet;


import domain.Client;
import domain.ClientAddress;
import domain.ClientBeneficiary;
import domain.ClientProduct;
import domain.PhoneType;
import domain.User;
import domain.UserProduct;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcessData {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		

		String reportFile = "C:/Users/Yomi/Projects/Call Center/Data/ResultadosDataMedicalone.txt";
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(reportFile));
		
		String inputFile = "C:/Users/Yomi/Projects/Call Center/Data/DATA MEDICALONE C.A.xls";
		File inputWorkbook = new File(inputFile);
	    Workbook w;
	   	    	    
	    try {
	    	
	    	// Se registra el Driver de MySQL
	        DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
	        
	        // Se obtiene una conexión con la base de datos. Hay que
	        // cambiar el usuario "root" y la clave "la_clave" por las
	        // adecuadas a la base de datos que estemos usando.
	        Connection conexion = 
	        	DriverManager.getConnection ("jdbc:mysql:loadbalance://localhost:3306,localhost:3310/segadental?loadBalanceConnectionGroup=first","root", "administrator");
	    	
	    	w = Workbook.getWorkbook(inputWorkbook);
		  	//Sheet[] sheets = w.getSheets();
		   	// Get the first sheet
		 	Sheet sheet = w.getSheet(0);
		   	// Loop over first 10 column and lines
		 	
		 	writer.write("Reporte con datos que fallaron en la carga");
	 		writer.newLine();
	 		writer.write("Archivo: DATA MEDICALONE C.A.xls");
	 		writer.newLine();
	 		writer.write("Hoja: "+sheet.getName());
	 		writer.newLine();
	      
		 	for (int i = 1; i < sheet.getRows(); i++) {
		 		
		 		//Cantidad
		 		Cell cell = sheet.getCell(6, i);
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
		 		}
		 		
		 		//Cédula
		 		cell = sheet.getCell(0, i);
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
		 		
		 		//Precio
		 		cell = sheet.getCell(7, i);
		 		String priceStr = cell.getContents();
		 		priceStr = priceStr.replaceAll(",", "");
		 		priceStr = priceStr.replaceAll(".00", "");
		 		
		 		if(isHolder){
		 			Integer clientId = (Integer)new command.SelectClientId(identityCard).executeDatabaseOperation(conexion);
		 			
		 			if(clientId==null){
		 				//Email
				 		cell = sheet.getCell(16, i);
				 		String email = cell.getContents();
				 		
				 		if(email.equalsIgnoreCase("")){
				 			email = null;
				 		}
				 		
		 				Client client = new Client();
				 		client.setFirstName(firstNames);
				 		client.setLastName(lastNames);
				 		client.setIdentityCard(identityCard);
				 		client.setEmail(email);
				 		client.setSex("M");
				 		client.setIsHolder(1);
				 		
				 		clientId = (Integer)new command.CreateClient(client).executeDatabaseOperation(conexion);
				 		
				 		if(clientId == -1){
				 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el cliente. Saltando fila...");
					 		writer.newLine();
				 			continue;
				 		}
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
		 			
			 		int addressType = 1;
		 			Integer addressId = (Integer)new command.SelectClientAddressId(clientId, state, city, addressType).executeDatabaseOperation(conexion);
		 			
		 			if(addressId==null){
		 				ClientAddress address = new ClientAddress();
				 		address.setCity(city);
				 		address.setState(state);
				 		address.setClientId(clientId);
				 		address.setAddressTypeId(addressType);
				 		address.setPropertyTypeId(3);

				 		addressId = (Integer)new command.CreateClientAddress(address).executeDatabaseOperation(conexion);
				 		
				 		if(addressId == -1){
				 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar la dirección del cliente. Continuando con el resto de los datos...");
					 		writer.newLine();
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
			 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono de habitación del cliente (formato inválido: '"+homePhone+"'). Continuando con el resto de los datos...");
				 		writer.newLine();
			 		} else {
			 			Integer phoneId = (Integer)new command.SelectClientPhoneId(clientId, homePhone, homePhoneTypeId).executeDatabaseOperation(conexion);
			 			
			 			if(phoneId==null){
			 				PhoneType phone = new PhoneType(homePhoneTypeId, homePhone, clientId);
			 				
			 				phoneId = (Integer) new command.CreateClientPhone(phone).executeDatabaseOperation(conexion);
					 		
					 		if(phoneId == -1){
					 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono de habitación del cliente. Continuando con el resto de los datos...");
						 		writer.newLine();
					 		}
			 			}
			 		}
			 		
			 		//Tel Ofi
			 		cell = sheet.getCell(13, i);
			 		String officePhone = cell.getContents();
			 		officePhone = officePhone.replaceAll("-", "");
			 		int officePhoneTypeId = 2;
			 		
			 		if(officePhone.length()==0){
			 			officePhone = null;
			 		} else if(officePhone.length()!=0 && officePhone.length()!=11){
			 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono de oficina del cliente (formato inválido: '"+homePhone+"'). Continuando con el resto de los datos...");
				 		writer.newLine();
			 		} else {
			 			Integer phoneId = (Integer)new command.SelectClientPhoneId(clientId, officePhone, officePhoneTypeId).executeDatabaseOperation(conexion);
			 			
			 			if(phoneId==null){
			 				PhoneType phone = new PhoneType(officePhoneTypeId, officePhone, clientId);
			 				
			 				phoneId = (Integer) new command.CreateClientPhone(phone).executeDatabaseOperation(conexion);
					 		
					 		if(phoneId == -1){
					 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono de oficina del cliente. Continuando con el resto de los datos...");
						 		writer.newLine();
					 		}
			 			}
			 		}
			 		
			 		//Tel Cel
			 		cell = sheet.getCell(14, i);
			 		String mobilePhone = cell.getContents();
			 		mobilePhone = mobilePhone.replaceAll("-", "");
			 		int mobilePhoneTypeId = 3;
			 		
			 		if(mobilePhone.length()==0){
			 			mobilePhone = null;
			 		} else if(mobilePhone.length()!=0 && mobilePhone.length()!=11){
			 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono celular del cliente (formato inválido: '"+homePhone+"'). Continuando con el resto de los datos...");
				 		writer.newLine();
			 		} else {
			 			Integer phoneId = (Integer) new command.SelectClientPhoneId(clientId, mobilePhone, mobilePhoneTypeId).executeDatabaseOperation(conexion);
			 			
			 			if(phoneId==null){
			 				PhoneType phone = new PhoneType(mobilePhoneTypeId, mobilePhone, clientId);
			 				
			 				phoneId = (Integer) new command.CreateClientPhone(phone).executeDatabaseOperation(conexion);
					 		
					 		if(phoneId == -1){
					 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono celular del cliente. Continuando con el resto de los datos...");
						 		writer.newLine();
					 		}
			 			}
			 		}
			 		
			 		//Tel Fax
			 		cell = sheet.getCell(15, i);
			 		String otherPhone = cell.getContents();
			 		otherPhone = otherPhone.replaceAll("-", "");
			 		int otherPhoneTypeId = 4;
			 		
			 		if(otherPhone.length()==0){
			 			otherPhone = null;
			 		} else if(otherPhone.length()!=0 && otherPhone.length()!=11){
			 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono 'fax' del cliente (formato inválido: '"+homePhone+"'). Continuando con el resto de los datos...");
				 		writer.newLine();
			 		} else {
			 			Integer phoneId = (Integer)new command.SelectClientPhoneId(clientId, otherPhone, otherPhoneTypeId).executeDatabaseOperation(conexion);
			 			
			 			if(phoneId==null){
			 				PhoneType phone = new PhoneType(otherPhoneTypeId, otherPhone, clientId);
			 				
			 				phoneId = (Integer)new command.CreateClientPhone(phone).executeDatabaseOperation(conexion);
					 		
					 		if(phoneId == -1){
					 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el teléfono 'fax' del cliente. Continuando con el resto de los datos...");
						 		writer.newLine();
					 		}
			 			}
			 		}
			 		
			 		Integer productId = 4;		
		 			
		 			//Vendedor
			 		cell = sheet.getCell(8, i);
			 		String sellerName = cell.getContents().toUpperCase();			 		
			 		String sellerUserName = sellerName.replaceAll(" ", "").toLowerCase();
			 		int roleId = 2;
			 		
		 			Integer sellerId = (Integer)new command.SelectUserId(sellerUserName, roleId).executeDatabaseOperation(conexion);
		 			
		 			if(sellerId==null){
		 				String[] names = sellerName.split(" ");
				 		String sellerFirstNames, sellerLastNames, sellerPassword;
				 		
				 		if(names.length==2){
				 			sellerFirstNames = names[0].toLowerCase();
				 			sellerFirstNames = sellerFirstNames.substring(0, 1).toUpperCase() + sellerFirstNames.substring(1, sellerFirstNames.length());
				 			sellerLastNames = names[1];
				 		} else {
				 			sellerFirstNames = names[0].toLowerCase();
				 			sellerLastNames = null;
				 		}

				 		sellerPassword = sellerUserName;
				 		String encryptPassword = UserLoginServlet.getEncryptPassword(sellerPassword);
				 		
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
				 		
				 		User user = new User();
				 		user.setFirstName(sellerFirstNames);
				 		user.setLastName(sellerLastNames);
				 		user.setIdentityCard(null);
				 		user.setUserName(sellerUserName);
				 		user.setPassword(encryptPassword);
				 		user.setRoleId(roleId);
				 		user.setRoomId(null);
				 		user.setTurn(turn);
				 		
				 		sellerId = (Integer) new command.CreateUser(user).executeDatabaseOperation(conexion);

				 		if(sellerId == -1){
				 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el vendedor. Saltando fila...");
					 		writer.newLine();
				 			continue;
				 		} else {
				 			UserProduct userProduct = new UserProduct();
				 			userProduct.setUserId(sellerId);
				 			userProduct.setProductId(productId);
				 			Integer result = (Integer)new command.CreateUserProduct(userProduct).executeDatabaseOperation(conexion);
				 		}
		 			}
		 			
		 			//Nombre Producto
			 		cell = sheet.getCell(5, i);
			 		String productName = cell.getContents();	 		
		 			
		 			Integer clientProductId = (Integer)new command.SelectClientProductId(clientId, productId).executeDatabaseOperation(conexion);
		 		
		 			if(clientProductId==null){
		 				//Fecha Venta
				 		cell = sheet.getCell(4, i);
				 		String affiliationDateStr = cell.getContents();
				 		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				 		Date affiliationDate = dateFormat.parse(affiliationDateStr);
				 		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
				 		affiliationDateStr = dateFormat2.format(affiliationDate);
				 		
				 		//Contrato
				 		cell = sheet.getCell(3, i);
				 		String contract = cell.getContents();
				 		
				 		if(contract.equalsIgnoreCase("RENOVACION")){
				 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar la relación cliente-producto: contrato faltante. Saltando fila...");
					 		writer.newLine();
				 			continue;
				 		}
				 		
				 		int statusId = 1;
				 		
				 		if(productName.equalsIgnoreCase("NO RENOVAR")){
				 			statusId = 4;
				 		}
				 		
		 				ClientProduct product = new ClientProduct();
				 		product.setClientId(clientId);
				 		product.setProductId(productId);
				 		product.setAffiliationDate(affiliationDateStr);
				 		product.setAmount(priceStr);
				 		product.setSellerId(sellerId);
				 		product.setStatusId(statusId);
				 		product.setContract(contract);
				 		
				 		clientProductId = (Integer) new command.CreateClientProduct(product).executeDatabaseOperation(conexion);
				 		
				 		if(clientProductId == -1){
				 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar la relación cliente-producto. Saltando fila...");
					 		writer.newLine();
				 			continue;
				 		}
		 			}
		 		} else {
		 			Integer clientBeneficiaryId = (Integer)new command.SelectClientBeneficiaryId(contractHolder, firstNames, lastNames).executeDatabaseOperation(conexion);
		 			
		 			if(clientBeneficiaryId==null){
		 				ClientProduct clientProduct = (ClientProduct)new command.SelectClientProduct(contractHolder).executeDatabaseOperation(conexion);
			 			
		 				if(clientProduct == null){
				 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar el beneficiario: el titular asociado no existe. Saltando fila...");
					 		writer.newLine();
		 					continue;
		 				} else {
		 					ClientBeneficiary beneficiary = new ClientBeneficiary();
				 			beneficiary.setClientProduct(clientProduct.getId());
				 			beneficiary.setFirstName(firstNames);
				 			beneficiary.setLastName(lastNames);
				 			beneficiary.setIdentityCard(identityCard);
				 			
				 			clientBeneficiaryId = (Integer) new command.CreateClientBeneficiary(beneficiary).executeDatabaseOperation(conexion);
					 		
					 		if(clientBeneficiaryId == -1){
					 			writer.write("Fila N° "+(i+1)+".- No se pudo insertar la relación cliente-producto. Saltando fila...");
						 		writer.newLine();
					 			continue;
					 		} else {
					 			String amountStr = clientProduct.getAmount();
					 			Integer amountTit = Integer.valueOf(amountStr);
					 			Integer amountBen = Integer.valueOf(priceStr);
					 			Integer amountTot = amountTit + amountBen;
					 			
					 			Integer rowsUpdated = (Integer) new command.EditClientProduct(clientProduct.getId(), amountTot.toString()).executeDatabaseOperation(conexion);
					 		
					 			if(rowsUpdated != 1){
					 				writer.write("Fila N° "+(i+1)+".- No se pudo actualizar el precio de cliente-producto. Saltando fila...");
							 		writer.newLine();
						 			continue;
					 			}
					 		}
		 				}		 				
		 			}	 			
		 		}		 		
		 					 		
		 		/*System.out.println((i+1)+".- identityCard: "
 						+ identityCard + ", lastNames: " + lastNames + ", firstNames: "+firstNames+", contract: "+contract+
 						", affiliationDateStr: "+affiliationDateStr+", productId: "+productId);
		 		System.out.println("isHolder: "+isHolder+
 						", contractHolder: "+contractHolder+", price: "+price+", sellerFirstNames: "+sellerFirstNames+
 						", sellerLastNames: "+sellerLastNames);
		 		System.out.println("sellerUserName: "+sellerUserName+", sellerPassword:"+
 						sellerPassword+", roleId: "+roleId+", turn: "+turn+", city: "+city+", state: "+state+", homePhone: "+homePhone);
		 		System.out.println("officePhone: "+officePhone+", mobilePhone: "+mobilePhone+", otherPhone: "+otherPhone+", email: "+email);
		 		System.out.println();*/
		 		System.out.println("Fila N° "+(i+1)+".- Almacenada Exitosamente. Siguiente fila...");
		 	}
		 	// Se cierra la conexión con la base de datos.
            conexion.close();
	 		writer.close();
	 		System.out.println("Reporte creado");
	    } catch (BiffException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}